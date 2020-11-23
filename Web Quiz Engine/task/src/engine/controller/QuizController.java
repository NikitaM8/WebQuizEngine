package engine.controller;

import engine.entity.Answer;
import engine.entity.Completion;
import engine.exception.ForbiddenException;
import engine.exception.QuizNotFoundException;
import engine.entity.Quiz;
import engine.entity.User;
import engine.repository.QuizRepository;
import engine.service.CompletionService;
import engine.service.QuizService;
import engine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@RestController
public class QuizController {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    UserService userService;

    @Autowired
    QuizService quizService;

    @Autowired
    CompletionService completionService;

    /**
     * Add a quiz to db via Post request in JSON format
     * @param quiz - serialized quiz object
     * @param principal - current authenticated user
     * @return - quiz object for deserialization and response
     */
    @PostMapping(value = "/api/quizzes", consumes = "application/json")
    @ResponseBody
    public Quiz addQuizz(@Valid @RequestBody Quiz quiz, Principal principal) {

        User user = userService.findUserByUsername(principal.getName());
        quiz.setUser(user);

        return quizService.saveQuiz(quiz);
    }

    /**
     * Get quiz by id via GET request
     * @param id - path parameter in GET request to get a certain quiz
     * @return - quiz object for deserialization and response
     * @throws QuizNotFoundException - if quiz not found
     */
    @GetMapping("/api/quizzes/{id}")
    public Quiz getQuizzById(@PathVariable(value = "id") Long id) throws QuizNotFoundException {

        return quizRepository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException("Quiz for ID " + id + " not found"));
    }

    /**
     * get all available quizzes in pageble format via GET request
     * @param page - page number, started from 0
     * @param pageSize - number of quizzes at page
     * @param sortBy - chosen column for sorting
     * @return - page object for deserialization
     */
    @GetMapping("/api/quizzes")
    @ResponseBody
    public Page<Quiz> getAllQuizzes(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy)
    {
        return quizService.findAllQuizzesPagable(page, pageSize, sortBy);
    }

    /**
     * solve a certain available quiz via POST request and if it was success, add completion to db
     * @param id - number of quiz to solve
     * @param answer - supposed answer
     * @param principal - current authenticated user
     * @return - message about success or fail of solving
     * @throws QuizNotFoundException
     */
    @PostMapping(path = "/api/quizzes/{id}/solve")
    @ResponseBody
    public String solveQuizz(@PathVariable Long id, @RequestBody(required = false) Answer answer, Principal principal) throws QuizNotFoundException {
        //try to get quiz
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException("Quiz for ID " + id + " not found"));

        //if user not sent answer, it is ok if quiz have no answer, otherwise fail
        if (answer.getAnswer() == null) {
            if (quiz.getAnswer() == null) {

                User user = userService.findUserByUsername(principal.getName());

                Completion completion = new Completion(quiz.getId(), user);
                completionService.saveCompletion(completion);

                return "{\"success\":true,\"feedback\":\"Congratulations, you're right!\"}";
            } else {
                return "{\"success\":false,\"feedback\":\"Wrong answer! Please, try again.\"}";
            }
        }

        int[] correctAnswers = quiz.getAnswer(); //correct answers
        int[] answers = answer.getAnswer(); //answers from user

        if (correctAnswers == null) {
            correctAnswers = new int[0];
        }

        //sort both arrays
        Arrays.sort(correctAnswers);
        Arrays.sort(answers);

        if (Arrays.equals(correctAnswers, answers)) {

            User user = userService.findUserByUsername(principal.getName());

            Completion completion = new Completion(quiz.getId(), user);
            completionService.saveCompletion(completion);

            return "{\"success\":true,\"feedback\":\"Congratulations, you're right!\"}";
        }

        return "{\"success\":false,\"feedback\":\"Wrong answer! Please, try again.\"}";
    }

    /**
     * delete a certain quiz via DELETE request
     * @param id - number of quiz to delete
     * @param principal - current authenticated user
     * @throws QuizNotFoundException - if quiz not found
     * @throws ForbiddenException - if current user not author of certain quiz
     */
    @DeleteMapping(path = "/api/quizzes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuizz(@PathVariable Long id, Principal principal) throws QuizNotFoundException, ForbiddenException {

        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException("Quiz for ID " + id + " not found"));

        User quizUser = quiz.getUser();
        User curUser = userService.findUserByUsername(principal.getName());

        if (quizUser.getId() != curUser.getId()) {
            throw new ForbiddenException("Forbidden");
        }

        quizRepository.delete(quiz);
    }

    /**
     * get all completions of current auth user via GET request in pageble format
     * @param page - page number, started from 0
     * @param pageSize - number of completions at page
     * @param sortBy - chosen column for sorting
     * @param principal - current authenticated user
     * @return - page object for deserialization
     */
    @GetMapping(path = "/api/quizzes/completed")
    @ResponseBody
    public Page<Completion> getAllUsersCompletions(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = "completed_At") String sortBy,
            Principal principal) {

        User user = userService.findUserByUsername(principal.getName());

        return completionService.findAllUsersCompletions(user.getId(), page, pageSize, sortBy);
    }

    //--------METHONDS FOR REGISTRATION AND AUTH--------

    /**
     * register new user via POST request
     * @param user - serialized user object
     * @return - message about success or fail to register user
     */
    @PostMapping(path = "/api/register")
    @ResponseStatus(HttpStatus.OK)
    public String registerUser(@Valid @RequestBody User user) {

        if (!userService.saveUser(user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is already exist");
        }

        return "User successfully registered";
    }

}
