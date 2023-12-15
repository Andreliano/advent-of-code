package org.example;

import java.util.Objects;

public class DigitWithPosition {

    private String digit;

    private int startPosition;

    public String getDigit() {
        return digit;
    }

    public void setDigit(String digit) {
        this.digit = digit;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DigitWithPosition that = (DigitWithPosition) o;
        return startPosition == that.startPosition && Objects.equals(digit, that.digit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(digit, startPosition);
    }
}
