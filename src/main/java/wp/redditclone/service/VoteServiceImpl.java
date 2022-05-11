package wp.redditclone.service;

import org.springframework.stereotype.Service;
import wp.redditclone.model.Vote;
import wp.redditclone.repository.VoteRepository;

@Service
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;

    public VoteServiceImpl(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Override
    public Vote save(Vote vote) {
        return voteRepository.save(vote);
    }
}
