package io.zipcoder.tc_spring_poll_application.controller;

import io.zipcoder.tc_spring_poll_application.dtos.OptionCount;
import io.zipcoder.tc_spring_poll_application.dtos.VoteResult;
import io.zipcoder.tc_spring_poll_application.domain.Vote;
import io.zipcoder.tc_spring_poll_application.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ComputeResultController {
    
    private VoteRepository voteRepository;
    
    @Autowired
    public ComputeResultController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @RequestMapping(value = "/computeresult", method = RequestMethod.GET)
    public ResponseEntity<?> computeResult(@RequestParam Long pollId) {
        VoteResult voteResult = new VoteResult();
        Iterable<Vote> allVotes = voteRepository.findVotesByPoll(pollId);
        
        // Count votes for each option
        Map<Long, OptionCount> tempMap = new HashMap<>();
        int totalVotes = 0;
        
        for (Vote vote : allVotes) {
            totalVotes++;
            OptionCount optionCount = tempMap.get(vote.getOption().getId());
            if (optionCount == null) {
                optionCount = new OptionCount();
                optionCount.setOptionId(vote.getOption().getId());
                optionCount.setCount(0);
                tempMap.put(vote.getOption().getId(), optionCount);
            }
            optionCount.setCount(optionCount.getCount() + 1);
        }
        
        voteResult.setTotalVotes(totalVotes);
        voteResult.setResults(tempMap.values());
        
        return new ResponseEntity<VoteResult>(voteResult, HttpStatus.OK);
    }
}
