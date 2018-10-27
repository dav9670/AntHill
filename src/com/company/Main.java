package com.company;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class Main extends PApplet {

    public static void main(String[] args) {
        PApplet.main(Main.class.getName());
    }

    private List<Ant> antList;

    @Override
    public void settings() {
        super.settings();
        size(800, 800, P2D);

        antList = new ArrayList<>();
    }

    @Override
    public void setup() {
        antList.add(new Ant(this));
    }

    @Override
    public void draw() {
        for (Ant ant : antList) {
            ant.draw();
        }
    }
}
