package com.github.kayjamlang.core.expressions.data;

public class Range {

    /** Range start from */
    public final Number from;

    /** Range end to */
    public final Number to;

    /** Change value */
    public final Number changeValue;

    /**
     * Creates range
     * @param from Range start from
     * @param to Range end to
     * @param changeValue Change value
     */
    public Range(Number from, Number to, Number changeValue){
        this.from = from;
        this.to = to;
        this.changeValue = changeValue;
    }
}
