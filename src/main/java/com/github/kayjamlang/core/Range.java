package com.github.kayjamlang.core;

public class Range {

    public final Long from;
    public final Long to;
    public final Long changeValue;

    public Range(Long from, Long to, Long changeValue) {
        this.from = from;
        this.to = to;
        this.changeValue = changeValue;
    }
}
