package com.company;

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
    private List<Ant> antList;

    @Override
    public void settings() {
        super.settings();
        size(800, 800, P2D);

        antList = new ArrayList<>();
    }

    @Override
    public void setup() {
        ground = new Ground(this);
        antList.add(new Ant(this));
    }

    public void update() {
        antList.get(0).moveTo(mouseX, mouseY);
    }

    @Override
    public void draw() {
        update();

        background(Color.CYAN.getRGB());

        ground.draw();

        for (Ant ant : antList) {
            ant.draw();
        }
    }
}
