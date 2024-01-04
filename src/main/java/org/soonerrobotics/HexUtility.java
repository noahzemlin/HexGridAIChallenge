package org.soonerrobotics;

import java.awt.*;

import static java.lang.Math.sqrt;

public class HexUtility {
    public static Polygon getPolygon(int x0, int y0, int size) {

        int qw = size / 2;
        int hh = (int) (sqrt(3) / 2 * size);

        int[] xs = new int[] {x0, x0 + 2 * qw, x0 + 3 * qw, x0 + 2 * qw, x0, x0 - qw};
        int[] ys = new int[] {y0, y0, y0 - hh, y0 - 2 * hh, y0 - 2 * hh, y0 - hh};

        return new Polygon(xs, ys, 6);
    }
}
