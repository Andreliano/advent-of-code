package org.example;

public class CubeInformation {

    private int totalCubes;

    private String color;

    public int getTotalCubes() {
        return totalCubes;
    }

    public void setTotalCubes(int totalCubes) {
        this.totalCubes = totalCubes;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "CubeInformation{" +
                "totalCubes=" + totalCubes +
                ", color='" + color + '\'' +
                '}';
    }
}
