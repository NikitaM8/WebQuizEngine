# WebQuizEngine
A web quiz engine platfrom wich provides basic operations, such as add new quiz, delete your quiz, solve random quiz, get certain quiz and more.
Platform uses basic http authhentication with bCrypt. All information persists in H2 database. Service developed using Spring Boot.

## Main functionality of API (requests)
POST request /api/register - register a new user
Example
{
    "email": "well@google.com",
    "password": "12345"
}

Field email must be in correct email format, password lenght must be not less then 5

---------------------------------------------------------------

POST request /api/quizzes - add your personal quiz in JSON format
Example
{
  "title": "The Java Logo second quizz",
  "text": "What is depicted on the Java logo?",
  "options": ["Robot","Tea leaf","Cup of coffee","Bug"],
  "answer": [2]
}

Field answer can be empty or may contain more than one option
Response woud be the same as request, instead answer field 

---------------------------------------------------------------

GET request /api/quizzes/2 - get certain quiz by its id (in this line quiz with id 2)
Response with JSON similar to above.

---------------------------------------------------------------

GET request /api/quizzes - get all quizes (in page format)
Parameters: page, size, sortBy
By default page = 0, size = 10, sortBy = id

For example /api/quizzes/?size=5&page=3 return you 5 quizzes at 3 page if service have enough

---------------------------------------------------------------

POST request /api/quizzes/2/solve - solve certain quiz (in this line quiz with id 2)
Example
{
    "answer": [2]
}

Serivce return message with success or fail in solving quiz

---------------------------------------------------------------

DELETE request /api/quizzes/2 - delete certain quiz (in this line quiz with id 2)
Response with status HTTP codes or message means than quiz not found
If user not author of this quiz, he can't perform delete operation

---------------------------------------------------------------

GET request /api/quizzes/completed - get all completions of any quizzes for current user
Parameters: page, size, sortBy
By default page = 0, size = 10, sortBy = completed_At

For example /api/quizzes/completed/?size=10&page=1 return you 10 completions at 1 page if service have enough

## How to use in your envirorment (MacOs)
Download project to your local directory and run it with gradle
```bash
gradle bootRun
```

Project has embeded tomcat, so you can use API via localhost:8889/(request) 
Don't forget to change direction to database file in application.properties


----------------
It was educational project for mastering Spring Boot, ORM, JPA, Hibernate, REST, H2 database, SQL
Thanks JetBrains for this project!
