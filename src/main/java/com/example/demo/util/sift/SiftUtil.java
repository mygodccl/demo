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
        LeftOutAction<L, R> leftOutAction;
        RightOutAction<L, R> rightOutAction;
        IntersectionAction<L, R> intersectionAction;

        Processor(Collection<L> ll, Collection<R> rr) {
            this.ll = ll;
            this.rr = rr;
        }

        public Processor<L, R> setLeftOutAction(LeftOutAction<L, R> leftOutAction) {
            this.leftOutAction = leftOutAction;
            return this;
        }

        public Processor<L, R> setRightOutAction(RightOutAction<L, R> rightOutAction) {
            this.rightOutAction = rightOutAction;
            return this;
        }

        public Processor<L, R> setIntersectionAction(IntersectionAction<L, R> intersectionAction) {
            this.intersectionAction = intersectionAction;
            return this;
        }

        public ResultMap<L, R> process(Comparator<L, R> predicate) {
            List<R> rrTemp = new ArrayList<>(rr);
            List<ResultCell<L, R>> collectResult = new ArrayList<>();
            List<ResultCell<L, R>> actionResult = instanceActionResult();
            out:for (L l : ll) {
                Iterator<R> iterator = rrTemp.iterator();
                while (iterator.hasNext()) {
                    R r = iterator.next();
                    // add intersection
                    if (predicate.equal(l, r)) {
                        if (intersectionAction != null) {
                            mergeActionResult(actionResult, intersectionAction.execute(l, r));
                        }
                        collectResult.add(new ResultCell<>(l, r, CellType.INTERSECTION));
                        iterator.remove();
                        continue out;
                    }
                }
                // add left out
                if (leftOutAction != null) {
                    mergeActionResult(actionResult, leftOutAction.execute(l));
                }
                collectResult.add(new ResultCell<>(l, null, CellType.LEFT));
            }
            // add right out
            rrTemp.forEach(r -> {
                if (rightOutAction != null) {
                    mergeActionResult(actionResult, rightOutAction.execute(r));
                }
                collectResult.add(new ResultCell<>(null, r, CellType.RIGHT));
            });
            return new ResultMap<>(collectResult, actionResult);
        }

        private List<ResultCell<L, R>> instanceActionResult() {
            if (intersectionAction != null || leftOutAction != null || rightOutAction != null) {
                return new ArrayList<>();
            }
            return null;
        }

        private void mergeActionResult(List<ResultCell<L, R>> dest, ResultCell<L, R> sour) {
            if (sour != null) {
                dest.add(sour);
            }
        }

        @FunctionalInterface
        public interface Comparator<L, R> {
            boolean equal(L l, R r);
        }

        @FunctionalInterface
        public interface LeftOutAction<L, R> {
            ResultCell<L, R> execute(L l);
        }

        @FunctionalInterface
        public interface RightOutAction<L, R> {
            ResultCell<L, R> execute(R r);
        }

        @FunctionalInterface
        public interface IntersectionAction<L, R> {
            ResultCell<L, R> execute(L l, R r);
        }
    }
}
