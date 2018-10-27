package com.company;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;

import static processing.core.PConstants.ELLIPSE;

public class Ant {

    private PApplet app;

    private PVector position;
    private float width;
    private float heigth;

    private PShape body;

    public Ant(PApplet app) {
        this.app = app;

        position = new PVector(50, 50);
        width = 30;
        heigth = 30;

        body = app.createShape(PConstants.GROUP);

        float headWidth = width / 3;
        float headHeight = heigth / 3;
        PShape head = app.createShape(ELLIPSE, position.x + (width / 2), position.y + headHeight / 2, headWidth, headHeight);
        body.addChild(head);

        float torsoWidth = width / 2;
        float torsoHeight = heigth / 3 * 2;
        PShape torso = app.createShape(ELLIPSE, position.x + (width / 2), position.y + headHeight + (torsoHeight / 2), torsoWidth, torsoHeight);
        body.addChild(torso);

        for (int i = 0; i < 2; i++) {
            PShape leftLeg = app.createShape();
            leftLeg.beginShape();
            leftLeg.vertex(position.x + (width / 3), (i * torsoHeight / 2) + position.y + headHeight + (torsoHeight / 3));
            leftLeg.vertex(position.x + (width / 3) - width / 6, (i * torsoHeight / 2) + position.y + headHeight + (torsoHeight / 3) - torsoHeight / 3);
            leftLeg.vertex(position.x + (width / 3) - width / 6 * 2, (i * torsoHeight / 2) + position.y + headHeight + (torsoHeight / 3));
            leftLeg.endShape();
            leftLeg.setFill(false);
            body.addChild(leftLeg);
        }

        for (int i = 0; i < 2; i++) {
            PShape rightLeg = app.createShape();
            rightLeg.beginShape();
            rightLeg.vertex(position.x + (width / 3 * 2), (i * torsoHeight / 2) + position.y + headHeight + (torsoHeight / 3));
            rightLeg.vertex(position.x + (width / 3 * 2) + width / 6, (i * torsoHeight / 2) + position.y + headHeight + (torsoHeight / 3) - torsoHeight / 3);
            rightLeg.vertex(position.x + (width / 3 * 2) + width / 6 * 2, (i * torsoHeight / 2) + position.y + headHeight + (torsoHeight / 3));
            rightLeg.endShape();
            rightLeg.setFill(false);
            body.addChild(rightLeg);
        }
    }

    public void draw() {
        app.shape(body);
    }
}
