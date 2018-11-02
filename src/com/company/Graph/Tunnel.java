package com.company.Graph;

import com.company.Ground;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.stream.Collectors;

public class Tunnel {
    private PApplet app;
    private Graph<TunnelNode, TunnelEdge> graph;

    public Tunnel(PApplet app, Ground ground) {
        this.app = app;
        this.graph = new DefaultUndirectedWeightedGraph<>(TunnelEdge.class);

        //Create entries of the tunnel matching with the ground height at a specific x
        //TODO Make sure nodes are spaced enough
        int nbEntries = (int) app.random(1, 3);
        for (int i = 0; i < nbEntries; i++) {
            TunnelNode entry = new TunnelNode(ground.getShape().getVertex((int) app.random(0, app.width)), TunnelNode.NodeType.POI);
            graph.addVertex(entry);
        }

        //Create underground nodes at random
        //TODO Make sure nodes are spaced enough
        int nbNodes = (int) app.random(5, 10);
        for (int i = 0; i < nbNodes; i++) {
            //Create node from x=0...width, y=ground_lowest_point...height
            TunnelNode node = new TunnelNode(new PVector(app.random(0, app.width), app.random(Ground.LOWEST_POINT, app.height)), TunnelNode.NodeType.POI);
            graph.addVertex(node);
        }

        //TODO Make connections between nodes : node needs to have atleast 1 tunnel leading to it (nearest other node), then link all the other nodes within as specific radius around the node to it
        for (TunnelNode node : graph.vertexSet()) {
            boolean hasEdge = false;
            TunnelNode closestNode = null;
            for (TunnelNode neighbor : graph.vertexSet().parallelStream().filter(x -> !x.equals(node)).collect(Collectors.toList())) {
                if (!graph.containsEdge(node, neighbor)) {
                    double distance = node.getPosition().dist(neighbor.getPosition());
                    if (closestNode == null || distance < node.getPosition().dist(closestNode.getPosition())) {
                        closestNode = neighbor;
                    }
                    if (distance < node.nodeType.radius) {
                        TunnelEdge tunnelEdge = new TunnelEdge(distance);
                        graph.addEdge(node, neighbor, tunnelEdge);
                        graph.setEdgeWeight(tunnelEdge, distance);
                        hasEdge = true;
                    }
                }
            }
            if (hasEdge == false) {
                double distance = node.getPosition().dist(closestNode.getPosition());
                TunnelEdge tunnelEdge = new TunnelEdge(distance);
                graph.addEdge(node, closestNode, tunnelEdge);
                graph.setEdgeWeight(tunnelEdge, distance);
            }
        }

        //TODO Make paths with perlin noise

        //TODO Generate crossroad nodes on overlapping paths
    }

    public void draw() {
        //TODO For all nodes inside graph, draw paths between them if dugOut is true
    }
}
