package com.example.demo.util.sift;

public class ResultMap<L, R> {
    private ResultCell<L, R> intersection;
    private ResultCell<L, R> difference;

    ResultMap(ResultCell<L, R> intersection, ResultCell<L, R> difference) {
        this.intersection = intersection;
        this.difference = difference;
    }

    public ResultCell<L, R> getDifference() {
        return difference;
    }

    public ResultCell<L, R> getIntersection() {
        return intersection;
    }
}
