package OTS.task.votingApp.controller;

import OTS.task.votingApp.exceptions.VoteOptionNotFoundException;
import OTS.task.votingApp.exceptions.VotingNotFoundException;
import OTS.task.votingApp.service.VoteOptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import OTS.task.votingApp.model.Voting;
import OTS.task.votingApp.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/voting")
public class VotingRestController {

    @Autowired
    private VotingService votingService;
    @Autowired
    private VoteOptionService voteOptionService;

    @RequestMapping(value = "/page/{page}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Voting> getAllVotings(@PathVariable("page") Integer page) {
        return votingService.getVotings(page);
    }

    @RequestMapping(value = "/add", method=RequestMethod.POST, consumes="application/json", produces="application/json")
    @Transactional
    public Voting addMeetings(@RequestBody Voting voting) {
        return votingService.add(voting);
    }

    @RequestMapping(value = "/{id}/vote", method=RequestMethod.PUT, consumes="application/json")
    @Transactional
    public ResponseEntity vote(@RequestBody Long voteOptionId) {
        try {
            voteOptionService.registerVote(voteOptionId);
        } catch (VoteOptionNotFoundException e) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Transactional
    @RequestMapping(value = "/{id}/close", method = RequestMethod.PUT)
    public ResponseEntity close(@PathVariable("id") Long votingId) {
        try {
            votingService.close(votingId);
        } catch (VotingNotFoundException e) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

}
