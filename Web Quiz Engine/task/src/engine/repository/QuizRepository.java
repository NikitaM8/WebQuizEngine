package engine.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import engine.entity.Quiz;

@Repository
public interface QuizRepository extends PagingAndSortingRepository<Quiz, Long> {

}
