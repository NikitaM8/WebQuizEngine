package engine.service;

import engine.entity.Completion;
import engine.entity.User;
import engine.repository.CompletionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CompletionService {

    @Autowired
    CompletionRepository completionRepository;

    public boolean saveCompletion(Completion completion) {

        completionRepository.save(completion);

        return true;
    }

    public Page<Completion> findAllUsersCompletions(Long userId, Integer page, Integer pageSize, String sortBy) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sortBy).descending());

        return completionRepository.findAllUsersCompletions(userId, pageable);
    }

    public Collection<Completion> findAllComp(Long userId) {
        return completionRepository.findAllCompletions(userId);
    }

}
