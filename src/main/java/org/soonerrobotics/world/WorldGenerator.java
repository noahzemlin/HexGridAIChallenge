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

    public void setTemperatureNoise(int mean, int range, float xScale, float yScale) {
        this.temperatureNoise.mean = mean;
        this.temperatureNoise.range = range;
        this.temperatureNoise.xScale = xScale;
        this.temperatureNoise.yScale = yScale;
    }

    public void setHumidityNoise(int mean, int range, float xScale, float yScale) {
        this.humidityNoise.mean = mean;
        this.humidityNoise.range = range;
        this.humidityNoise.xScale = xScale;
        this.humidityNoise.yScale = yScale;
    }

    public void setHeightNoise(int mean, int range, float xScale, float yScale) {
        this.heightNoise.mean = mean;
        this.heightNoise.range = range;
        this.heightNoise.xScale = xScale;
        this.heightNoise.yScale = yScale;
    }

    public World Generate(HexagonalGrid<SatelliteData> grid) {
        World world = new World((short) grid.getGridData().getGridWidth(), (short) grid.getGridData().getGridHeight());

        for (Hexagon<SatelliteData> hexagon : grid.getHexagons()) {
            int x = hexagon.getGridX();
            int y = hexagon.getGridZ();

            int temperature = (int) temperatureNoise.get(this.seed, x, y, grid.getGridData().getGridWidth(), grid.getGridData().getGridHeight());
            int humidity = (int) humidityNoise.get(this.seed + 1, x, y, grid.getGridData().getGridWidth(), grid.getGridData().getGridHeight());
            int height = (int) heightNoise.get(this.seed + 2, x, y, grid.getGridData().getGridWidth(), grid.getGridData().getGridHeight());

            world.tileMap.put(hexagon.getId(), new Tile(temperature, humidity, height));
        }

        return world;
    }
}

