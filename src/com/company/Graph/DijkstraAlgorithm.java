package com.company.Graph;

import org.jgrapht.Graph;

import java.util.*;

public class DijkstraAlgorithm {

    private Set<TunnelNode> settledNodes;
    private Set<TunnelNode> unSettledNodes;
    private Map<TunnelNode, TunnelNode> predecessors;
    private Map<TunnelNode, Double> distance;

    private Graph graph;

    public DijkstraAlgorithm(Graph graph) {
        this.graph = graph;
    }

    public void execute(TunnelNode source) {
        settledNodes = new HashSet<>();
        unSettledNodes = new HashSet<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();
        distance.put(source, 0d);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            TunnelNode node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(TunnelNode node) {
        List<TunnelNode> adjacentNodes = getNeighbors(node);
        for (TunnelNode target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    private double getDistance(TunnelNode node, TunnelNode target) {
        return graph.getEdgeWeight(graph.getEdge(node, target));
    }

    private List<TunnelNode> getNeighbors(TunnelNode node) {
        List<TunnelEdge> edges = new ArrayList<>(graph.edgesOf(node));
        List<TunnelNode> neighbors = new ArrayList<>();
        for (TunnelEdge edge : edges) {
            neighbors.add(edge.firstNode != node ? edge.firstNode : edge.secondNode);
        }
        return neighbors;
    }

    private TunnelNode getMinimum(Set<TunnelNode> tunnelNodes) {
        TunnelNode minimum = null;
        for (TunnelNode tunnelNode : tunnelNodes) {
            if (minimum == null) {
                minimum = tunnelNode;
            } else {
                if (getShortestDistance(tunnelNode) < getShortestDistance(minimum)) {
                    minimum = tunnelNode;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(TunnelNode TunnelNode) {
        return settledNodes.contains(TunnelNode);
    }

    private Double getShortestDistance(TunnelNode destination) {
        Double d = distance.get(destination);
        if (d == null) {
            return Double.MAX_VALUE;
        } else {
            return d;
        }
    }

    public LinkedList<TunnelNode> getPath(TunnelNode target) {
        LinkedList<TunnelNode> path = new LinkedList<TunnelNode>();
        TunnelNode step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }

}
