package org.example;

import java.util.Objects;

public class AsteriskCoordinate {

    private int x;

    private int y;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AsteriskCoordinate that = (AsteriskCoordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "AsteriskCoordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
