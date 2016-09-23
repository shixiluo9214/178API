package com.framework.utils;


public final class PagingTools {

    private PagingTools() {
    }

    public static int getPageNum(int index, int pageSize) {
        return (index - 1) / pageSize + 1;
    }
}
