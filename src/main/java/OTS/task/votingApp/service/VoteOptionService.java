package OTS.task.votingApp.service;

import OTS.task.votingApp.exceptions.VoteOptionNotFoundException;

public interface VoteOptionService {

    void registerVote(Long voteOptionId) throws VoteOptionNotFoundException;

}
