package org.soonerrobotics.world;

public class Tile {
    private int temperature;
    private int humidity;
    private int height;

    protected Tile(int temperature, int humidity, int height) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.height = height;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getHeight() {
        return height;
    }

    public int getHumidity() {
        return humidity;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "temperature=" + temperature +
                ", humidity=" + humidity +
                ", height=" + height +
                '}';
    }
}
