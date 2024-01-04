package org.soonerrobotics.world;

import static java.lang.Math.*;

public class NoiseConfig {
    public int mean;
    public int range;
    public double xScale;
    public double yScale;

    public NoiseConfig() {
        this.mean = 0;
        this.range = 1;
        this.xScale = 1;
        this.yScale = 1;
    }
    
    public float get(long seed, int x, int y, int width, int height) {

        double x1 = 0;
        double y1 = 0;
        double x2 = this.xScale;
        double y2 = this.yScale;

        double s= (double) x / (width * sqrt(3));
        double t= (double) y / (height * 1.5);
        double dx=x2-x1;
        double dy=y2-y1;

        double pi = 3.141593;
        double nx=x1+cos(s*2*pi)*dx/(2*pi);
        double ny=y1+cos(t*2*pi)*dy/(2*pi);
        double nz=x1+sin(s*2*pi)*dx/(2*pi);
        double nw=y1+sin(t*2*pi)*dy/(2*pi);

        return OpenSimplex2S.noise4_ImproveXYZ(seed, nx, ny, nz, nw) * this.range + this.mean;
    }
}
