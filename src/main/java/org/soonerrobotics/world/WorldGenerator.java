package org.soonerrobotics.world;

import org.hexworks.mixite.core.api.Hexagon;
import org.hexworks.mixite.core.api.HexagonalGrid;
import org.hexworks.mixite.core.api.contract.SatelliteData;

import java.util.Random;

public class WorldGenerator {

    // World Parameters
    private long seed = 0;
    private final NoiseConfig temperatureNoise;
    private final NoiseConfig humidityNoise;
    private final NoiseConfig heightNoise;

    public WorldGenerator() {
        this.temperatureNoise = new NoiseConfig();
        this.humidityNoise = new NoiseConfig();
        this.heightNoise = new NoiseConfig();

        this.seed = new Random().nextLong();
    }

    public void regenSeed() {
        this.seed = new Random().nextLong();
    }

    public void setTemperatureNoise(int mean, int range, double xScale, double yScale) {
        this.temperatureNoise.mean = mean;
        this.temperatureNoise.range = range;
        this.temperatureNoise.xScale = xScale;
        this.temperatureNoise.yScale = yScale;
    }

    public void setHumidityNoise(int mean, int range, double xScale, double yScale) {
        this.humidityNoise.mean = mean;
        this.humidityNoise.range = range;
        this.humidityNoise.xScale = xScale;
        this.humidityNoise.yScale = yScale;
    }

    public void setHeightNoise(int mean, int range, double xScale, double yScale) {
        this.heightNoise.mean = mean;
        this.heightNoise.range = range;
        this.heightNoise.xScale = xScale;
        this.heightNoise.yScale = yScale;
    }

    public World Generate(HexagonalGrid<SatelliteData> grid) {
        World world = new World((short) grid.getGridData().getGridWidth(), (short) grid.getGridData().getGridHeight());

        for (Hexagon<SatelliteData> hexagon : grid.getHexagons()) {
            // TODO: Replace with custom grid that has radius 1 to fix a bug with the window rescaling
            int x = (int) (hexagon.getCenterX() / grid.getGridData().getRadius());
            int y = (int) (hexagon.getCenterY() / grid.getGridData().getRadius());
            int width = grid.getGridData().getGridWidth();
            int height = grid.getGridData().getGridHeight();

            int temperature = (int) temperatureNoise.get(this.seed, x, y, width, height);
            int humidity = (int) humidityNoise.get(this.seed + 1, x, y, width, height);
            int elevation = (int) heightNoise.get(this.seed + 2, x, y, width, height);

            world.tileMap.put(hexagon.getId(), new Tile(temperature, humidity, elevation));
        }

        return world;
    }
}

