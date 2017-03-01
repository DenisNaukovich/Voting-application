package OTS.task.votingApp.controller;

import OTS.task.votingApp.model.Voting;
import OTS.task.votingApp.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/voting")
public class VotingController {

    @Autowired
    private VotingService votingService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getVoting(@PathVariable("id") Long votingId, Model model) {
        Voting voting = votingService.getVoting(votingId);
        model.addAttribute("voting", voting);
        return "votingStat";
    }

}
