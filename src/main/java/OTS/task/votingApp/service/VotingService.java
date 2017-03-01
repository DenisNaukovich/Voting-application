package OTS.task.votingApp.service;

import OTS.task.votingApp.exceptions.VotingNotFoundException;
import OTS.task.votingApp.model.Voting;

import java.util.*;

public interface VotingService {

    List<Voting> getVotings(Integer pageNumber);

    Voting getVoting(Long votingId);

    Voting add(Voting voting);

    void close(Long votingId) throws VotingNotFoundException;

}
