package com.example.demo.util.sift;

import java.util.Collection;

public class ResultCell<L, R> {
    private Collection<L> left;
    private Collection<R> right;

    ResultCell(Collection<L> ll, Collection<R> rr) {
        this.left = ll;
        this.right = rr;
    }

    public Collection<L> getLeft() {
        return left;
    }

    public Collection<R> getRight() {
        return right;
    }
}