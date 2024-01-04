package org.soonerrobotics;

import org.hexworks.mixite.core.api.*;
import org.hexworks.mixite.core.api.Point;
import org.hexworks.mixite.core.api.contract.SatelliteData;
import org.hexworks.mixite.core.api.defaults.DefaultSatelliteData;
import org.soonerrobotics.world.Tile;
import org.soonerrobotics.world.World;
import org.soonerrobotics.world.WorldGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.lang.Math.sqrt;
import static org.soonerrobotics.HexUtility.getPolygon;

public class MapEditor {
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Nectar Nations");

        Container content = frame.getContentPane();
        content.add(new DrawingPanel());

        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setSize( (int)(1920), 1080);
        frame.setResizable(false);
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
    }

    static class DrawingPanel extends JPanel
    {
        //mouse variables here
        //Point mPt = new Point(0,0);

        HexagonalGrid<SatelliteData> grid;
        World world;

        public DrawingPanel()
        {
            setBackground(Color.WHITE);

            HexagonalGridBuilder<SatelliteData> builder = new HexagonalGridBuilder<>()
                    .setGridHeight(42)
                    .setGridWidth(64)
                    .setGridLayout(HexagonalGridLayout.RECTANGULAR)
                    .setOrientation(HexagonOrientation.POINTY_TOP)
                    .setRadius(16.0);
            grid = builder.build();

            WorldGenerator worldGenerator = new WorldGenerator();
            worldGenerator.setTemperatureNoise(20, 10, 3, 3);
            worldGenerator.setHumidityNoise(40, 20, 8, 8);
            worldGenerator.setHeightNoise(0, 10, 3, 3);

            world = worldGenerator.Generate(grid);

        }

        public void paintComponent(Graphics g)
        {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            super.paintComponent(g2);

            for (Hexagon<SatelliteData> hexagon : grid.getHexagons()) {
                Polygon polygon = new Polygon();

                for(Point p : hexagon.getPoints()) {
                    polygon.addPoint((int) p.getCoordinateX(), (int) p.getCoordinateY());
                }

                Tile tile = world.getTile(hexagon.getId());

                float cr = 0;
                float cg = 0;
                float cb = 0;

                if (tile.getHeight() > 5) {
                    cr = 0.3f;
                    cg = 0.3f;
                    cb = 0.3f;
                } else if (tile.getHeight() < -0) {
                    cr = 0.2f;
                    cg = 0.4f;
                    cb = 1;
                } else {
                    cr = (tile.getTemperature() - 20) / 40.0f + (tile.getHumidity() - 40) / 200.0f + 0.35f;
                    cg = -(tile.getTemperature() - 20) / 40.0f - (tile.getHumidity() - 40) / 200.0f + 0.65f;
                    cb = 0;
                }


                g2.setColor(new Color(cr, cg, cb));
                g2.fillPolygon(polygon);
                g2.setColor(Color.DARK_GRAY);
                g2.drawPolygon(polygon);
            }
        }
    } // end of DrawingPanel class

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
