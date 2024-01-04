package org.soonerrobotics.world;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class NoiseConfig {
    public int mean;
    public int range;
    public float xScale;
    public float yScale;

    public NoiseConfig() {
        this.mean = 0;
        this.range = 1;
        this.xScale = 1;
        this.yScale = 1;
    }
    
    public float get(long seed, int x, int y, int width, int height) {

        float x1 = -this.xScale;
        float y1 = -this.yScale;
        float x2 = this.xScale;
        float y2 = this.yScale;

        float s= (float) x / (width - 2);
        float t= (float) y / (height - 2);
        float dx=x2-x1;
        float dy=y2-y1;

        double pi = 3.141593;
        double nx=x1+cos(s*2*pi)*dx/(2*pi);
        double ny=y1+cos(t*2*pi)*dy/(2*pi);
        double nz=x1+sin(s*2*pi)*dx/(2*pi);
        double nw=y1+sin(t*2*pi)*dy/(2*pi);

        return OpenSimplex2S.noise4_ImproveXYZ(seed, nx, ny, nz, nw) * this.range + this.mean;
    }
}
