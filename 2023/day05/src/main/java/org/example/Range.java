package org.example;

public class Range {

    private long destinationRangeStart;

    private long sourceRangeStart;

    private long rangeLength;

    public long getDestinationRangeStart() {
        return destinationRangeStart;
    }

    public void setDestinationRangeStart(long destinationRangeStart) {
        this.destinationRangeStart = destinationRangeStart;
    }

    public long getSourceRangeStart() {
        return sourceRangeStart;
    }

    public void setSourceRangeStart(long sourceRangeStart) {
        this.sourceRangeStart = sourceRangeStart;
    }

    public long getRangeLength() {
        return rangeLength;
    }

    public void setRangeLength(long rangeLength) {
        this.rangeLength = rangeLength;
    }

    @Override
    public String toString() {
        return "Range{" +
                "destinationRangeStart=" + destinationRangeStart +
                ", sourceRangeStart=" + sourceRangeStart +
                ", rangeLength=" + rangeLength +
                '}';
    }
}
