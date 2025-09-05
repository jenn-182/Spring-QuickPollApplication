package io.zipcoder.tc_spring_poll_application.controller;

import io.zipcoder.tc_spring_poll_application.domain.Poll;
import io.zipcoder.tc_spring_poll_application.repository.PollRepository;
import io.zipcoder.tc_spring_poll_application.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
public class PollController {
    
    private PollRepository pollRepository;
    
    @Autowired
    public PollController(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }
    
    // Verification method
    protected void verifyPoll(Long pollId) throws ResourceNotFoundException {
        Optional<Poll> poll = pollRepository.findById(pollId);
        if (!poll.isPresent()) {
            throw new ResourceNotFoundException("Poll with id " + pollId + " not found");
        }
    }
    
    // GET all polls
    @RequestMapping(value="/polls", method=RequestMethod.GET)
    public ResponseEntity<Page<Poll>> getAllPolls(Pageable pageable) {
        Page<Poll> allPolls = pollRepository.findAll(pageable);
        return new ResponseEntity<>(allPolls, HttpStatus.OK);
    }
    
    // POST create poll
    @RequestMapping(value="/polls", method=RequestMethod.POST)
    public ResponseEntity<?> createPoll(@Valid @RequestBody Poll poll) {
        poll = pollRepository.save(poll);
        
        URI newPollUri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(poll.getId())
            .toUri();
        
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(newPollUri);
        
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    // GET single poll
    @RequestMapping(value="/polls/{pollId}", method=RequestMethod.GET)
    public ResponseEntity<?> getPoll(@PathVariable Long pollId) {
        verifyPoll(pollId);
        Poll poll = pollRepository.findById(pollId).get();
        return new ResponseEntity<>(poll, HttpStatus.OK);
    }
    
    // PUT update poll
    @RequestMapping(value="/polls/{pollId}", method=RequestMethod.PUT)
    public ResponseEntity<?> updatePoll(@Valid @RequestBody Poll poll, @PathVariable Long pollId) {
        verifyPoll(pollId);
        Poll updatedPoll = pollRepository.save(poll);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // DELETE poll
    @RequestMapping(value="/polls/{pollId}", method=RequestMethod.DELETE)
    public ResponseEntity<?> deletePoll(@PathVariable Long pollId) {
        verifyPoll(pollId);
        pollRepository.deleteById(pollId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
