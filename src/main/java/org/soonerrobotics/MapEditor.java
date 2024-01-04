package org.soonerrobotics;

import org.hexworks.mixite.core.api.*;
import org.hexworks.mixite.core.api.Point;
import org.hexworks.mixite.core.api.contract.SatelliteData;
import org.hexworks.mixite.core.api.defaults.DefaultSatelliteData;
import org.soonerrobotics.world.Tile;
import org.soonerrobotics.world.World;
import org.soonerrobotics.world.WorldGenerator;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.lang.Math.*;
import static org.soonerrobotics.HexUtility.getPolygon;

public class MapEditor {
    static JFrame frame;
    static double heightScale = 3;
    static double tempScale = 3;
    static int xOffset = 0;
    static boolean makeNewSeed = false;
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        frame = new JFrame("Nectar Nations");

        Container content = frame.getContentPane();
        content.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.9;
        c.weighty = 1.0;

        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));

        content.add(new DrawingPanel(), c);

        c.weightx = 0.1;
        c.gridx = 2;

        content.add(settingsPanel, c);

        JSlider heightSlider = new JSlider(JSlider.HORIZONTAL,
                10, 60, (int) (heightScale * 10));
        heightSlider.addChangeListener(e -> {
            heightScale = heightSlider.getValue() / 10.0;
            heightSlider.setBorder(BorderFactory.createTitledBorder("Height Noise Scale " + heightScale));
            frame.repaint();
        });
        heightSlider.setBorder(BorderFactory.createTitledBorder("Height Noise Scale " + heightScale));
        heightSlider.setMajorTickSpacing(10);
        heightSlider.setMinorTickSpacing(1);
        heightSlider.setPaintTicks(false);
        heightSlider.setPaintLabels(false);
        settingsPanel.add(heightSlider);

        JSlider tempSlider = new JSlider(JSlider.HORIZONTAL,
                10, 60, (int) (tempScale * 10));
        tempSlider.addChangeListener(e -> {
            tempScale = tempSlider.getValue() / 10.0;
            tempSlider.setBorder(BorderFactory.createTitledBorder("Temp Noise Scale " + tempScale));
            frame.repaint();
        });
        tempSlider.setBorder(BorderFactory.createTitledBorder("Temp Noise Scale " + tempScale));
        tempSlider.setMajorTickSpacing(10);
        tempSlider.setMinorTickSpacing(1);
        tempSlider.setPaintTicks(false);
        tempSlider.setPaintLabels(false);
        settingsPanel.add(tempSlider);

        JButton seedButton = new JButton("New Seed");
        seedButton.addActionListener(e -> {
            makeNewSeed = true;
            frame.repaint();
        });
        settingsPanel.add(seedButton);

        JSlider xOffsetSlider = new JSlider(JSlider.HORIZONTAL,
                0, 63, (int) (tempScale));
        xOffsetSlider.addChangeListener(e -> {
            xOffset = xOffsetSlider.getValue();
            xOffsetSlider.setBorder(BorderFactory.createTitledBorder("X Offset " + xOffset));
            frame.repaint();
        });
        xOffsetSlider.setBorder(BorderFactory.createTitledBorder("X Offset " + xOffset));
        xOffsetSlider.setMajorTickSpacing(10);
        xOffsetSlider.setMinorTickSpacing(1);
        xOffsetSlider.setPaintTicks(false);
        xOffsetSlider.setPaintLabels(false);
        settingsPanel.add(xOffsetSlider);

        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setSize( (int)(1920), 1080);
        frame.setResizable(true);
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
    }

    static class DrawingPanel extends JPanel
    {
        //mouse variables here
        //Point mPt = new Point(0,0);

        HexagonalGridBuilder<SatelliteData> builder;
        HexagonalGrid<SatelliteData> grid;
        WorldGenerator worldGenerator;
        World world;

        public DrawingPanel()
        {
            setBackground(Color.WHITE);
            builder = new HexagonalGridBuilder<>()
                    .setGridHeight(40)
                    .setGridWidth(64)
                    .setGridLayout(HexagonalGridLayout.RECTANGULAR)
                    .setOrientation(HexagonOrientation.POINTY_TOP)
                    .setRadius(16.0);
            grid = builder.build();

            worldGenerator = new WorldGenerator();
            worldGenerator.setTemperatureNoise(20, 10, 3, 3);
            worldGenerator.setHumidityNoise(40, 20, 8, 8);
            worldGenerator.setHeightNoise(0, 10, 3, 3);

        }

        public void paintComponent(Graphics g)
        {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setFont(new Font("TimesRoman", Font.BOLD, 16));
            super.paintComponent(g2);

            double radius = (min((this.getHeight()-8) / (grid.getGridData().getGridHeight() * 1.5), (this.getWidth()-8) / (grid.getGridData().getGridWidth() * sqrt(3))));

            builder.setRadius(radius);
            grid = builder.build();

            worldGenerator.setTemperatureNoise(20, 10, tempScale, tempScale);
            worldGenerator.setHeightNoise(0, 10, heightScale, heightScale);

            if (makeNewSeed) {
                makeNewSeed = false;
                worldGenerator.regenSeed();
            }

            world = worldGenerator.Generate(grid, xOffset);

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
//                g2.drawString(hexagon.getGridX() + "," + hexagon.getGridY() + "," + hexagon.getGridZ(), (int) (hexagon.getCenterX() - 10), (int) (hexagon.getCenterY() + 6));
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
