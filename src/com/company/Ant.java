package com.company;

import com.company.Graph.Tunnel;
import com.company.Graph.TunnelNode;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static processing.core.PConstants.ELLIPSE;
import static processing.core.PConstants.TWO_PI;

public class Ant {

    private PApplet app;

    public final static float RADIUS = 15;

    private PShape body;
    private Color fillColor;

    private PVector position;
    private PVector velocity;
    private float movementSpeed;

    private TunnelNode currentNode;
    private TunnelNode targetNode = null;
    private List<TunnelNode> path = new ArrayList<>();

    public Ant(PApplet app, Color fillColor, TunnelNode start) {
        this.app = app;
        this.fillColor = fillColor;
        this.currentNode = start;

        //At start, 0,0 is upper left

        position = start.getPosition().copy();
        position.set(position.x - RADIUS / 2, position.y - RADIUS / 2);

        body = app.createShape(PConstants.GROUP);

        float headWidth = RADIUS / 3;
        float headHeight = RADIUS / 3;
        PShape head = app.createShape(ELLIPSE, position.x + (RADIUS / 2), position.y + headHeight / 2, headWidth, headHeight);
        head.setFill(fillColor.getRGB());
        body.addChild(head);

        float torsoWidth = RADIUS / 2;
        float torsoHeight = RADIUS / 3 * 2;
        PShape torso = app.createShape(ELLIPSE, position.x + (RADIUS / 2), position.y + headHeight + (torsoHeight / 2), torsoWidth, torsoHeight);
        torso.setFill(fillColor.getRGB());
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

        velocity = new PVector(0, 0);

        movementSpeed = app.random(RADIUS / 10, RADIUS / 7);
    }

    private void setVelocity(PVector newVelocity) {
        body.translate(-position.x, -position.y);
        float rotationAngle = 0;
        if (velocity.mag() == 0)
            rotationAngle = (float) (Math.atan2(newVelocity.y, newVelocity.x) - Math.atan2(-1, 0));
        else
            rotationAngle = (float) (Math.atan2(newVelocity.y, newVelocity.x) - Math.atan2(velocity.y, velocity.x));
        rotationAngle += rotationAngle < 0 ? TWO_PI : 0;
        body.rotate(rotationAngle);
        body.translate(position.x, position.y);
        velocity = newVelocity.copy();
    }

    public TunnelNode getCurrentNode() {
        return currentNode;
    }

    public TunnelNode getTargetNode() {
        return targetNode;
    }

    private void setTargetNode(TunnelNode node) {
        targetNode = node;
        if (targetNode != null) {
            PVector newVelocity = new PVector(targetNode.getPosition().x - position.x, targetNode.getPosition().y - position.y);
            newVelocity.setMag(movementSpeed);
            setVelocity(newVelocity);
        }
    }

    private void setGoal(Graph graph, TunnelNode target) throws NullPointerException {
        if (currentNode.getColor().equals(fillColor))
            currentNode.setColor(Color.black);
        target.setColor(fillColor);
        DijkstraShortestPath shortestPath = new DijkstraShortestPath(graph);
        path = new LinkedList<>(shortestPath.getPath(currentNode, target).getVertexList());
        setTargetNode(path.get(1));
    }

    private void move() {
        position.add(velocity);
        body.translate(velocity.x, velocity.y);
    }

    public void update(Tunnel tunnel) {
        try {
            if (targetNode == null) {
                setGoal(tunnel.getGraph(), tunnel.getRandomNodeExcept(currentNode));
            }

            if (position.dist(targetNode.getPosition()) < RADIUS / 5) {
                currentNode = targetNode;
                setTargetNode(path.indexOf(currentNode) != path.size() - 1 ? path.get(path.indexOf(currentNode) + 1) : null);
            }
            move();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw() {
        app.shape(body);
    }
}
