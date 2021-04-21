package com.github.kayjamlang.core.expressions.data;

public class Range {

    public final Number from;
    public final Number to;
    public final Number changeValue;

    @Deprecated
    public Range(Long from, Long to, Long changeValue) {
        this.from = from;
        this.to = to;
        this.changeValue = changeValue;
    }

    public Range(Number from, Number to, Number changeValue){
        this.from = from;
        this.to = to;
        this.changeValue = changeValue;
    }
}
