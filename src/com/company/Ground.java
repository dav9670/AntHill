package com.company;

import processing.core.PApplet;
import processing.core.PShape;

import java.awt.*;

public class Ground {

    public static final float LOWEST_POINT = 250;

    private PApplet app;

    private PShape shape;

    public Ground(PApplet app) {
        this.app = app;

        //At start, 0,0 is upper left

        shape = app.createShape();
        shape.beginShape();
        //Create wave

        float i = 0;
        for (i = 0; i <= app.width; i++) {
            shape.vertex(i, app.noise(i / 1000) * LOWEST_POINT);
        }
        shape.vertex(app.width, app.height);
        shape.vertex(0, app.height);
        shape.endShape();

        shape.setFill(Color.ORANGE.getRGB());
    }

    public PShape getShape() {
        return shape;
    }

    public void draw() {
        app.shape(shape);
    }
}
