package com.example.demo.util.sift;

import java.util.List;
import java.util.stream.Collectors;

public class ResultMap<L, R> {
    private List<ResultCell<L, R>> collectResult;
    private List<ResultCell<L, R>> actionResult;

    ResultMap(List<ResultCell<L, R>> collectResult, List<ResultCell<L, R>> actionResult) {
        this.collectResult = collectResult;
        this.actionResult = actionResult;
    }

    public List<ResultCell<L, R>> getCollectResult() {
        return collectResult;
    }

    public List<ResultCell<L, R>> getActionResult() {
        return actionResult;
    }

    public List<L> getLeft() {
        return collectResult.stream().filter(cell -> cell.getCellType().equals(CellType.LEFT)).map(ResultCell::getL).collect(Collectors.toList());
    }

    public List<R> getRight() {
        return collectResult.stream().filter(cell -> cell.getCellType().equals(CellType.RIGHT)).map(ResultCell::getR).collect(Collectors.toList());
    }

    public List<ResultCell<L, R>> getIntersection() {
        return collectResult.stream().filter(cell -> cell.getCellType().equals(CellType.INTERSECTION)).collect(Collectors.toList());
    }

    public List<L> getActionLeft() {
        return actionResult.stream().filter(cell -> cell.getCellType().equals(CellType.LEFT)).map(ResultCell::getL).collect(Collectors.toList());
    }

    public List<R> getActionRight() {
        return actionResult.stream().filter(cell -> cell.getCellType().equals(CellType.RIGHT)).map(ResultCell::getR).collect(Collectors.toList());
    }

    public List<ResultCell<L, R>> getActionIntersection() {
        return actionResult.stream().filter(cell -> cell.getCellType().equals(CellType.INTERSECTION)).collect(Collectors.toList());
    }
}
