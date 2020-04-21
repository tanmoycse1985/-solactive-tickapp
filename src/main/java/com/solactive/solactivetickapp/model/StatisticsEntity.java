package com.solactive.solactivetickapp.model;

public class StatisticsEntity {

    private Double average;
    private Double max;
    private Double min;
    private Long count;

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "StatisticsEntity{" +
                "average=" + average +
                ", max=" + max +
                ", min=" + min +
                ", count=" + count +
                '}';
    }

    public StatisticsEntity() {
    }

    public StatisticsEntity(Double average, Double max, Double min, Long count) {
        this.average = average;
        this.max = max;
        this.min = min;
        this.count = count;
    }
}
