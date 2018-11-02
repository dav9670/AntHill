package com.company;

import com.company.Graph.TunnelNode;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;

import static processing.core.PConstants.ELLIPSE;

public class Ant {

    private PApplet app;

    public final static float RADIUS = 15;

    private PVector position;

    private PShape body;

    private TunnelNode target;

    public Ant(PApplet app) {
        this.app = app;

        //At start, 0,0 is upper left

        position = new PVector(50, 50);

        body = app.createShape(PConstants.GROUP);

        float headWidth = RADIUS / 3;
        float headHeight = RADIUS / 3;
        PShape head = app.createShape(ELLIPSE, position.x + (RADIUS / 2), position.y + headHeight / 2, headWidth, headHeight);
        body.addChild(head);

        float torsoWidth = RADIUS / 2;
        float torsoHeight = RADIUS / 3 * 2;
        PShape torso = app.createShape(ELLIPSE, position.x + (RADIUS / 2), position.y + headHeight + (torsoHeight / 2), torsoWidth, torsoHeight);
        body.addChild(torso);

        for (int i = 0; i < 2; i++) {
            PShape leftLeg = app.createShape();
            leftLeg.beginShape();
            leftLeg.vertex(position.x + (RADIUS / 3), (i * torsoHeight / 2) + position.y + headHeight + (torsoHeight / 3));
            leftLeg.vertex(position.x + (RADIUS / 3) - RADIUS / 6, (i * torsoHeight / 2) + position.y + headHeight + (torsoHeight / 3) - torsoHeight / 3);
            leftLeg.vertex(position.x + (RADIUS / 3) - RADIUS / 6 * 2, (i * torsoHeight / 2) + position.y + headHeight + (torsoHeight / 3));
            leftLeg.endShape();
            leftLeg.setFill(false);
            body.addChild(leftLeg);
        }

        for (int i = 0; i < 2; i++) {
            PShape rightLeg = app.createShape();
            rightLeg.beginShape();
            rightLeg.vertex(position.x + (RADIUS / 3 * 2), (i * torsoHeight / 2) + position.y + headHeight + (torsoHeight / 3));
            rightLeg.vertex(position.x + (RADIUS / 3 * 2) + RADIUS / 6, (i * torsoHeight / 2) + position.y + headHeight + (torsoHeight / 3) - torsoHeight / 3);
            rightLeg.vertex(position.x + (RADIUS / 3 * 2) + RADIUS / 6 * 2, (i * torsoHeight / 2) + position.y + headHeight + (torsoHeight / 3));
            rightLeg.endShape();
            rightLeg.setFill(false);
            body.addChild(rightLeg);
        }

        //At end, 0,0 is middle

        position.x += RADIUS / 2;
        position.y += RADIUS / 2;
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
