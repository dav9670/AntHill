package com.company.Graph;

import processing.core.PVector;

import java.awt.*;

public class TunnelNode {

    private PVector position;
    public final NodeType nodeType;

    private Color color;

    public TunnelNode(PVector position, NodeType nodeType) {
        this.position = position;
        this.nodeType = nodeType;
        color = Color.black;
    }

    public PVector getPosition() {
        return position.copy();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public enum NodeType {
        ENTRY(75),
        POI(100),
        CROSSROAD(0),
        CHAMBER(150);

        public final float radius;

        NodeType(float radius) {
            this.radius = radius;
        }
    }
}
