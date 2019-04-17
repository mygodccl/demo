package com.example.demo.util.sift;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class SiftUtil {

    public static <L, R> Processor<L, R> buildProcess(Collection<L> ll, Collection<R> rr) {
        return new Processor<>(ll, rr);
    }

    public static class Processor<L, R> {
        Collection<L> ll;
        Collection<R> rr;
        LeftOutAction<L> leftOutAction;
        RightOutAction<R> rightOutAction;
        IntersectionAction<L, R> intersectionAction;

        Processor(Collection<L> ll, Collection<R> rr) {
            this.ll = ll;
            this.rr = rr;
        }

        public Processor<L, R> setLeftOutAction(LeftOutAction<L> leftOutAction) {
            this.leftOutAction = leftOutAction;
            return this;
        }

        public Processor<L, R> setRightOutAction(RightOutAction<R> rightOutAction) {
            this.rightOutAction = rightOutAction;
            return this;
        }

        public Processor<L, R> setIntersectionAction(IntersectionAction<L, R> intersectionAction) {
            this.intersectionAction = intersectionAction;
            return this;
        }

        public ResultMap<L, R> process(Comparator<L, R> predicate) {
            List<R> rrTemp = new ArrayList<>(rr);
            ResultCell<L, R> intersection = new ResultCell<>(new ArrayList<>(), new ArrayList<>());
            ResultCell<L, R> difference = new ResultCell<>(new ArrayList<>(), new ArrayList<>());
            for (L l : ll) {
                Iterator<R> iterator = rrTemp.iterator();
                int j = 0;
                for (; j < rrTemp.size(); ) {
                    R r = iterator.next();
                    // add intersection
                    if (predicate.equal(l, r)) {
                        if (intersectionAction != null) {
                            intersectionAction.execute(l, r);
                        }
                        intersection.getLeft().add(l);
                        intersection.getRight().add(r);
                        iterator.remove();
                        break;
                    }
                    j++;
                }
                // add left out
                if (j == rrTemp.size()) {
                    if (leftOutAction != null) {
                        leftOutAction.execute(l);
                    }
                    difference.getLeft().add(l);
                }
            }
            //add right out
            if (rightOutAction != null) {
                rrTemp.forEach(r -> {
                    rightOutAction.execute(r);
                    difference.getRight().add(r);
                });
            }
            return new ResultMap<>(intersection, difference);
        }

        @FunctionalInterface
        public interface Comparator<L, R> {
            boolean equal(L l, R r);
        }

        @FunctionalInterface
        public interface LeftOutAction<L> {
            boolean execute(L l);
        }

        @FunctionalInterface
        public interface RightOutAction<R> {
            boolean execute(R r);
        }

        @FunctionalInterface
        public interface IntersectionAction<L, R> {
            boolean execute(L l, R r);
        }
    }
}
