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

    //TODO Make tree with nodes containing a boolean if theyre dug out, make tunnel from node to node 

    private Ground ground;
    private Tunnel tunnel;
    private List<Ant> antList;
    private List<GrassBlade> grassBladeList;

    @Override
    public void settings() {
        super.settings();
        size(800, 800, P2D);

        antList = new ArrayList<>();
        grassBladeList = new ArrayList<>();
    }

    @Override
    public void setup() {
        ground = new Ground(this);
        tunnel = new Tunnel(this, ground);
        antList.add(new Ant(this));

        int nbGrass = (int) random(50 / Constants.ratio, 150 / Constants.ratio);
        for (int i = 0; i < nbGrass; i++) {
            //Grass will be between 12 and 5 segments, averaging 8.5
            int nbSegments = (int) (Math.abs(randomGaussian() / 2) * 7 + 5);
            grassBladeList.add(new GrassBlade(this, ground.getShape().getVertex((int) random(0, width)), nbSegments));
        }
    }

    public void update() {
        antList.get(0).moveTo(mouseX, mouseY);

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

        for (Ant ant : antList) {
            ant.draw();
        }
    }
}
