package com.example.demo.util.sift;

public class ResultCell<L, R> {
    private L l;
    private R r;
    private CellType cellType;

    public ResultCell(L l, R r, CellType cellType) {
        this.l = l;
        this.r = r;
        this.cellType = cellType;
    }

    public L getL() {
        return l;
    }

    public R getR() {
        return r;
    }

    CellType getCellType() {
        return cellType;
    }
}
