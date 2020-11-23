package engine.repository;

import engine.entity.Completion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CompletionRepository extends PagingAndSortingRepository<Completion, Long> {
    @Query(value = "SELECT * FROM Completion c WHERE c.UserID = :UserID", nativeQuery = true)
    Page<Completion> findAllUsersCompletions(@Param("UserID") Long userId, Pageable pageable);

    @Query(value = "SELECT * FROM Completion c WHERE c.UserID = :UserID", nativeQuery = true)
    Collection<Completion> findAllCompletions(@Param("UserID") Long userId);
}
