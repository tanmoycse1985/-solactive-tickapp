package com.solactive.solactivetickapp.model;

import javax.validation.constraints.NotNull;


public class TickEntity {

    @NotNull
    private String instrument;
    @NotNull
    private double price;
    @NotNull
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

    @Override
    public String toString() {
        return "TickEntity{" +
                "instrument='" + instrument + '\'' +
                ", price=" + price +
                ", timestamp=" + timestamp +
                '}';
    }
}
