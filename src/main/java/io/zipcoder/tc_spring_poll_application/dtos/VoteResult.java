package io.zipcoder.tc_spring_poll_application.dtos;

import java.util.Collection;

public class VoteResult {
    private int totalVotes;
    private Collection<OptionCount> results;

    // Constructors
    public VoteResult() {}
    
    public VoteResult(int totalVotes, Collection<OptionCount> results) {
        this.totalVotes = totalVotes;
        this.results = results;
    }

    // Getters and Setters
    public int getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
    }

    public Collection<OptionCount> getResults() {
        return results;
    }

    public void setResults(Collection<OptionCount> results) {
        this.results = results;
    }
}
