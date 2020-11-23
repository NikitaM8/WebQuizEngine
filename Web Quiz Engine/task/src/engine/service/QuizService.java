package engine.service;

import engine.entity.Quiz;
import engine.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {

    @Autowired
    QuizRepository quizRepository;

    public Page<Quiz> findAllQuizzesPagable(Integer page, Integer pageSize, String id) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(id));

        return quizRepository.findAll(pageable);
    }

    public Quiz saveQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }
}
