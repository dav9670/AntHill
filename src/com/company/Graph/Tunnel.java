package com.company.Graph;

import com.company.Constants;
import com.company.Ground;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Tunnel {
    private PApplet app;
    private Graph<TunnelNode, TunnelEdge> graph;

    public Tunnel(PApplet app, Ground ground) {
        this.app = app;
        this.graph = new DefaultUndirectedWeightedGraph<>(TunnelEdge.class);

        int nbColumns = (int) (app.width / app.random(50 / Constants.RATIO, 100 / Constants.RATIO));
        int nbRows = (int) ((app.height - ground.getLowestPoint()) / app.random(50 / Constants.RATIO, 100 / Constants.RATIO));
        float cellWidth = app.width / nbColumns;
        float cellHeight = (app.height - ground.getLowestPoint()) / nbRows;

        TunnelNode[][] grid = new TunnelNode[nbRows][nbColumns];
        int[] nbNodes = new int[nbRows];

        //Generate nodes for each row
        for (int i = 0; i < grid.length; i++) {
            nbNodes[i] = i == 0 ? ((int) app.random(1, 5)) : ((int) app.random(grid[i].length / 20, grid[i].length) + 1);
            ArrayList<Integer> nodesIndexes = new ArrayList<>(nbNodes[i]);

            for (int x = 0; x < nbNodes[i]; x++) {
                int index = -1;
                do {
                    index = (int) app.random(0, grid[i].length);
                } while (nodesIndexes.contains(index));
                nodesIndexes.add(index);
            }

            for (int index : nodesIndexes) {
                float startWidth = index * cellWidth;
                float endWidth = startWidth + cellWidth;
                float startHeight = i * cellHeight + ground.getLowestPoint();
                float endHeight = startHeight + cellHeight;

                float x = app.random(startWidth, endWidth);
                float y = i == 0 ? ground.getShape().getVertex((int) x).y : app.random(startHeight, endHeight);

                PVector nodePosition = new PVector(x, y);
                grid[i][index] = new TunnelNode(nodePosition, i == 0 ? TunnelNode.NodeType.ENTRY : TunnelNode.NodeType.POI);
                graph.addVertex(grid[i][index]);
            }
        }

        //Generate connections between nodes of same level, except for entries
        for (int i = 1; i < grid.length; i++) {
            for (int x = 0; x < grid[i].length; x++) {
                TunnelNode currentNode = grid[i][x];
                if (currentNode != null) {
                    TunnelNode nextNode = null;
                    for (int j = x + 1; j < grid[i].length && nextNode == null; j++) {
                        nextNode = grid[i][j];
                    }
                    if (nextNode != null) {
                        TunnelEdge edge = new TunnelEdge(currentNode, nextNode);
                        graph.addEdge(currentNode, nextNode, edge);
                        graph.setEdgeWeight(edge, edge.getWeight());
                    }
                }
            }
        }

        //Generate connections between layers
        //TODO Fix double connections for entries making nbConenctions +=2, so not all entries have paths
        for (int i = 0; i < grid.length - 1; i++) {
            int offset = 0;
            float nbConnections = 0;
            //While nbConnections is lower than : 100% if entries, 50% for rest
            while (nbConnections < (i == 0 ? (float) (nbNodes[i]) : (float) (nbNodes[i]) / 2)) {
                for (int x = 0; x < grid[i].length; x++) {
                    TunnelNode node = grid[i][x];
                    if (node != null) {
                        //if entries and entry already has edge, skips
                        if (i > 0 || (i == 0 && !(graph.edgeSet().stream().filter(tunnelEdge -> tunnelEdge.firstNode == node || tunnelEdge.secondNode == node).collect(Collectors.toList()).size() > 0))) {
                            //Make connections with nodes, gradually farther (if 2 nodes are same distance, connect the 2)
                            int nbAlreadyConnected = 0;
                            for (int j = 0; j < (offset == 0 ? 1 : 2); j++) {
                                try {
                                    TunnelNode bottomNode = grid[i + 1][j % 2 == 0 ? x - offset : x + offset];
                                    if (bottomNode != null) {
                                        TunnelEdge edge = new TunnelEdge(node, bottomNode);
                                        graph.addEdge(node, bottomNode, edge);
                                        graph.setEdgeWeight(edge, edge.getWeight());
                                        //Fix so that entries nodes always have to be connected
                                        nbConnections += i == 0 ? nbAlreadyConnected > 0 ? 0 : 1 : 1;
                                        nbAlreadyConnected++;
                                    }
                                } catch (ArrayIndexOutOfBoundsException e) {

                                }
                            }
                        }
                    }
                }
                offset++;
            }
        }
    }

    public Graph<TunnelNode, TunnelEdge> getGraph() {
        return graph;
    }

    public TunnelNode getRandomNode() {
        return (TunnelNode) graph.vertexSet().toArray()[(int) app.random(0, graph.vertexSet().size())];
    }

    public TunnelNode getRandomNodeExcept(TunnelNode node) {
        List<TunnelNode> nodeList = graph.vertexSet().stream().filter(nodeP -> nodeP != node).collect(Collectors.toList());
        return nodeList.get((int) app.random(0, nodeList.size()));
    }

    public TunnelNode getRandomEntryNode() {
        List<TunnelNode> entryList = graph.vertexSet().stream().filter(node -> node.nodeType == TunnelNode.NodeType.ENTRY).collect(Collectors.toList());
        return entryList.get((int) app.random(0, entryList.size()));
    }

    public int getNbNodes() {
        return graph.vertexSet().size();
    }

    public void draw() {

        app.stroke(Color.BLACK.getRGB());
        app.strokeWeight(1);

        for (TunnelEdge edge : graph.edgeSet()) {
            app.line(graph.getEdgeSource(edge).getPosition().x, graph.getEdgeSource(edge).getPosition().y, graph.getEdgeTarget(edge).getPosition().x, graph.getEdgeTarget(edge).getPosition().y);
        }

        app.strokeWeight(10);

        for (TunnelNode node : graph.vertexSet()) {
            app.stroke(node.getColor().getRGB());
            app.point(node.getPosition().x, node.getPosition().y);
        }

        app.stroke(Color.BLACK.getRGB());
        app.strokeWeight(1);
    }
}
