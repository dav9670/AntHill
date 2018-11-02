package com.company;

import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;

import static processing.core.PConstants.TRIANGLE_STRIP;

class GrassBlade {
    private final static float RATIO = Constants.ratio;
    private final static float SEGMENT_LENGTH = 20 * RATIO;
    private PApplet app;
    private float offset;
    private float stiffness;

    private Color color;

    private ArrayList<PVector> segments;

    GrassBlade(PApplet app, PVector root, int nbSegments) {
        this.app = app;

        segments = new ArrayList<PVector>();
        for (int x = 0; x < nbSegments; x++) {
            segments.add(new PVector(root.x, root.y - SEGMENT_LENGTH * x));
        }

        //set offset using distance from middle and adding a little bit of randomness so that not all the blades switch direction at the same time
        offset = 1 + ((root.x - app.width / 2) / app.width) + app.random(0.5f);

        color = new Color((int) app.random(0, 50), (int) app.random(100, 255), (int) app.random(0, 100));

        stiffness = app.random(1, 2);

        //grounding force
        segments.get(0).x = root.x;
        segments.get(0).y = root.y;
    }

    void update() {
        //perlin noise wind, slightly offset from every other blade
        float wind = (float) (app.noise((float) (app.frameCount / 100.0 + offset)) - 0.5);

        //apply forces to each blade segment, except base
        //Blade is constantly trying to straighten itself
        for (int x = 1; x < segments.size(); x++) {
            PVector segment = segments.get(x);
            //Higher straighten slower
            segment.y -= ((segments.size() - x) * RATIO) * stiffness;
            segment.x += x * wind * (4 * RATIO);
        }

        //Cap distance between segments at SEGMENT_LENGTH
        for (int x = 0; x < segments.size() - 1; x++) {
            float jointx = segments.get(x).x - segments.get(x + 1).x;
            float jointy = segments.get(x).y - segments.get(x + 1).y;

            float jointlength = PApplet.sqrt(jointx * jointx + jointy * jointy);
            if (jointlength > SEGMENT_LENGTH) {
                //If jointX was more out of bounds than jointY, then jointY is more affected, and vice-versa
                //This is what makes the y goes down
                jointx /= jointlength;
                jointy /= jointlength;
                jointx *= -SEGMENT_LENGTH;
                jointy *= -SEGMENT_LENGTH;
                segments.get(x + 1).x = segments.get(x).x + jointx;
                segments.get(x + 1).y = segments.get(x).y + jointy;
            }
        }
    }

    void draw() {
        app.fill(color.getRGB());
        app.stroke(color.getRGB());

        int h = segments.size() - 1;

        //Draw segments, reducing width depending on height
        app.beginShape(TRIANGLE_STRIP);
        for (int x = 0; x <= h; x++) {
            float segmentx = segments.get(x).x;
            float segmenty = segments.get(x).y;

            //Taller blades have larger width
            app.vertex(segmentx + (h * RATIO - x), segmenty);
            app.vertex(segmentx - (h * RATIO - x), segmenty);
        }
        app.endShape();
    }
}
