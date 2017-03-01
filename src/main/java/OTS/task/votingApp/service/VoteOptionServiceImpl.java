package OTS.task.votingApp.service;

import OTS.task.votingApp.exceptions.VoteOptionNotFoundException;
import OTS.task.votingApp.repository.VoteOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteOptionServiceImpl implements VoteOptionService {

    @Autowired
    VoteOptionRepository voteOptionRepository;

    @Override
    public void registerVote(Long voteOptionId) throws VoteOptionNotFoundException {
        if (!voteOptionRepository.exists(voteOptionId))
            throw new VoteOptionNotFoundException(voteOptionId.toString());
        voteOptionRepository.increaseCounterVotesById(voteOptionId);
    }
}
