package OTS.task.votingApp.service;

import OTS.task.votingApp.exceptions.VotingNotFoundException;
import OTS.task.votingApp.repository.VotingRepository;
import OTS.task.votingApp.model.Voting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VotingServiceImpl implements VotingService {

    private static final int PAGE_SIZE = 10;

    @Autowired
    VotingRepository votingRepository;


    @Override
    public List<Voting> getVotings(Integer pageNumber) {
        PageRequest pageRequest = new PageRequest(pageNumber, PAGE_SIZE, Sort.Direction.ASC, "id");
        return votingRepository.findAll(pageRequest).getContent();
    }

    @Override
    public Voting getVoting(Long votingId) {
        return votingRepository.findOne(votingId);
    }

    @Override
    public Voting add(Voting voting) {
        voting.getOptions().forEach(option -> option.setVoting(voting));
        return votingRepository.save(voting);
    }

    @Override
    public void close(Long votingId) throws VotingNotFoundException {
        if (!votingRepository.exists(votingId))
            throw new VotingNotFoundException(votingId.toString());
        votingRepository.closeVotingById(votingId);
    }

}
