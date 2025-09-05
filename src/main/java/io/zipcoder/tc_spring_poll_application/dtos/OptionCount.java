package io.zipcoder.tc_spring_poll_application.dtos;

public class OptionCount {
    private Long optionId;
    private int count;

    // Constructors
    public OptionCount() {}
    
    public OptionCount(Long optionId, int count) {
        this.optionId = optionId;
        this.count = count;
    }

    // Getters and Setters
    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
