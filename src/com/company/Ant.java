package com.company;

import com.company.Graph.Tunnel;
import com.company.Graph.TunnelNode;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static processing.core.PConstants.ELLIPSE;

public class Ant {

    private PApplet app;

    public final static float RADIUS = 15;
    private float movementSpeed;

    private PVector position;

    private PShape body;

    private TunnelNode currentNode;
    private TunnelNode targetNode = null;
    private List<TunnelNode> path = new ArrayList<>();

    public Ant(PApplet app, TunnelNode start) {
        this.app = app;
        this.currentNode = start;

        //At start, 0,0 is upper left

        position = start.getPosition().copy();

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

        movementSpeed = app.random(RADIUS / 10, RADIUS / 7);
    }

    public TunnelNode getCurrentNode() {
        return currentNode;
    }

    public TunnelNode getTargetNode() {
        return targetNode;
    }

    public void setGoal(Graph graph, TunnelNode target) {
        DijkstraShortestPath shortestPath = new DijkstraShortestPath(graph);
        path = new LinkedList<>(shortestPath.getPath(currentNode, target).getVertexList());
        this.targetNode = path.get(0);
    }

    public void moveBy(PVector target) {
        position.add(target);
        body.translate(target.x, target.y);
        app.translate(position.x, position.y);
        //body.rotate(target.heading());
        app.translate(-position.x, -position.y);
    }

    public void moveTo(PVector target) {
        position = target.copy();
        body.translate(target.x - position.x, target.x - position.y);
    }

    public void update(Tunnel tunnel) {
        if (path.isEmpty()) {
            setGoal(tunnel.getGraph(), tunnel.getRandomNode());
        }

        PVector movement = new PVector(targetNode.getPosition().x - position.x, targetNode.getPosition().y - position.y);
        movement.setMag(movementSpeed);
        moveBy(movement);

        if (position.dist(targetNode.getPosition()) < RADIUS / 2) {
            path.remove(currentNode);
            currentNode = targetNode;
            targetNode = path.isEmpty() ? null : path.get(0);
        }
    }

    public void draw() {
        app.shape(body);
    }
}
