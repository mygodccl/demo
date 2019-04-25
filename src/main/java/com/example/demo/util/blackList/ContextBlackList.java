package com.example.demo.util.blackList;

public class ContextBlackList extends AbstractBlackList {

    private static ContextBlackList instance = new ContextBlackList();

    private ContextBlackList() {
        super();
    }

    public static ContextBlackList getInstance() {
        return instance;
    }

}
