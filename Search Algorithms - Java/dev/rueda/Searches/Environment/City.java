package dev.rueda.Searches.Environment;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class City {
    private final String name;
    private City[] neighbors;
    private double distanceFromBeginning;
    private final int population;
    private final double area;
    private final double x;
    private final double y;

    City(String name, int population, double area, double x, double y) {
        this.name = name;
        this.population = population;
        this.area = area;
        this.distanceFromBeginning = 0;
        this.neighbors = new City[]{};
        this.x = x;
        this.y = y;
    }



    // Getters and Setters
    public String getName() {
        return this.name;
    }

    public int getPopulation() {
        return this.population;
    }

    public double getArea() {
        return this.area;
    }

    public double getDistanceFromBeginning() {
        return this.distanceFromBeginning;
    }

    public City[] getNeighbors() {
        return this.neighbors;
    }

    public void setNeighbors(City[] neighbors) {
        this.neighbors = neighbors;
    }

    public void setDistanceFromBeginning(City beginning) {
        double x = this.x;
        double y = this.y;
        double rawDistance = Math.sqrt(Math.pow(x - beginning.x, 2) + Math.pow(y - beginning.y, 2));

        BigDecimal bd = new BigDecimal(rawDistance).setScale(2, RoundingMode.HALF_UP);
        this.distanceFromBeginning = bd.doubleValue();
    }

    // Overrides
    @Override
    public String toString() {
        return this.name + " (" + this.distanceFromBeginning + ")";
    }
}
