package com.company;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;

import static processing.core.PConstants.ELLIPSE;

public class Ant {

    private PApplet app;

    public final static float WIDTH = 15;
    public final static float HEIGHT = 15;

    private PVector position;

    private PShape body;

    public Ant(PApplet app) {
        this.app = app;

        //At start, 0,0 is upper left

        position = new PVector(50, 50);

        body = app.createShape(PConstants.GROUP);

        float headWidth = WIDTH / 3;
        float headHeight = HEIGHT / 3;
        PShape head = app.createShape(ELLIPSE, position.x + (WIDTH / 2), position.y + headHeight / 2, headWidth, headHeight);
        body.addChild(head);

        float torsoWidth = WIDTH / 2;
        float torsoHeight = HEIGHT / 3 * 2;
        PShape torso = app.createShape(ELLIPSE, position.x + (WIDTH / 2), position.y + headHeight + (torsoHeight / 2), torsoWidth, torsoHeight);
        body.addChild(torso);

        for (int i = 0; i < 2; i++) {
            PShape leftLeg = app.createShape();
            leftLeg.beginShape();
            leftLeg.vertex(position.x + (WIDTH / 3), (i * torsoHeight / 2) + position.y + headHeight + (torsoHeight / 3));
            leftLeg.vertex(position.x + (WIDTH / 3) - WIDTH / 6, (i * torsoHeight / 2) + position.y + headHeight + (torsoHeight / 3) - torsoHeight / 3);
            leftLeg.vertex(position.x + (WIDTH / 3) - WIDTH / 6 * 2, (i * torsoHeight / 2) + position.y + headHeight + (torsoHeight / 3));
            leftLeg.endShape();
            leftLeg.setFill(false);
            body.addChild(leftLeg);
        }

        for (int i = 0; i < 2; i++) {
            PShape rightLeg = app.createShape();
            rightLeg.beginShape();
            rightLeg.vertex(position.x + (WIDTH / 3 * 2), (i * torsoHeight / 2) + position.y + headHeight + (torsoHeight / 3));
            rightLeg.vertex(position.x + (WIDTH / 3 * 2) + WIDTH / 6, (i * torsoHeight / 2) + position.y + headHeight + (torsoHeight / 3) - torsoHeight / 3);
            rightLeg.vertex(position.x + (WIDTH / 3 * 2) + WIDTH / 6 * 2, (i * torsoHeight / 2) + position.y + headHeight + (torsoHeight / 3));
            rightLeg.endShape();
            rightLeg.setFill(false);
            body.addChild(rightLeg);
        }

        //At end, 0,0 is middle

        position.x += WIDTH / 2;
        position.y += HEIGHT / 2;
    }

    public void move(float x, float y) {
        body.translate(x, y);
        position.x += x;
        position.y += y;
    }

    public void moveTo(float x, float y) {
        body.translate(x - position.x, y - position.y);
        position.x = x;
        position.y = y;
    }

    public void draw() {
        app.shape(body);
    }
}
