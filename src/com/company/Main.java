package com.company;

import com.company.Graph.Tunnel;
import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends PApplet {

    public static void main(String[] args) {
        PApplet.main(Main.class.getName());
    }

    //TODO Make tree with nodes containing a boolean if they're dug out, make tunnel from node to node

    private Ground ground;
    private Tunnel tunnel;
    private List<Ant> antList;
    private List<GrassBlade> grassBladeList;

    @Override
    public void settings() {
        super.settings();
        fullScreen(P2D, 1);
        //size(800, 800, P2D);

        antList = new ArrayList<>();
        grassBladeList = new ArrayList<>();
    }

    @Override
    public void setup() {
        ground = new Ground(this);
        tunnel = new Tunnel(this, ground);

        int nbAnts = (int) random(tunnel.getNbNodes() / 25, tunnel.getNbNodes() / 10) + 1;
        for (int i = 0; i < nbAnts; i++) {
            Color color = null;
            switch (i % 9) {
                case 0:
                    color = Color.CYAN;
                    break;
                case 1:
                    color = Color.GREEN;
                    break;
                case 2:
                    color = Color.MAGENTA;
                    break;
                case 3:
                    color = Color.RED;
                    break;
                case 4:
                    color = Color.YELLOW;
                    break;
                case 5:
                    color = Color.BLUE;
                    break;
                case 6:
                    color = Color.PINK;
                    break;
                case 7:
                    color = Color.GRAY;
                    break;
                case 8:
                default:
                    color = Color.WHITE;
                    break;
            }
            antList.add(new Ant(this, color, tunnel.getRandomEntryNode()));
        }

        int nbGrass = (int) random(width / 30 / Constants.RATIO, width / 15 / Constants.RATIO);
        for (int i = 0; i < nbGrass; i++) {
            //Grass will be between 12 and 5 segments, averaging 8.5
            int nbSegments = (int) (Math.abs(randomGaussian() / 2) * 7 + 5);
            grassBladeList.add(new GrassBlade(this, ground.getShape().getVertex((int) random(0, width)), nbSegments));
        }
    }

    public void update() {
        for (Ant ant : antList) {
            ant.update(tunnel);
        }

        for (GrassBlade b : grassBladeList) {
            b.update();
        }
    }

    @Override
    public void draw() {
        update();

        background(Color.CYAN.getRGB());

        for (GrassBlade grassBlade : grassBladeList) {
            grassBlade.draw();
        }

        ground.draw();

        tunnel.draw();

        for (Ant ant : antList) {
            ant.draw();
        }
    }
}
