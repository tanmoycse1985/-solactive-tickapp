package com.solactive.solactivetickapp.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class TicksVO {
    @NotEmpty(message = "Instrument is mandatory cannot be null or empty")
    private String instrument;
    @Positive(message = "Price should be positive cannot be negative or 0")
    private double price;
    @Positive(message = "Timestamp should be positive cannot be negative")
    private long timestamp;

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public TicksVO() {
    }

    public TicksVO(@NotNull String instrument, @NotNull double price, @NotNull long timestamp) {
        this.instrument = instrument;
        this.price = price;
        this.timestamp = timestamp;
    }
}
