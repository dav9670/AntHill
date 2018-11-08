package com.company.Graph.Djisktra;

import com.company.Graph.TunnelEdge;
import com.company.Graph.TunnelNode;
import org.jgrapht.Graph;

import java.util.*;
import java.util.stream.Collectors;

public class DjisktraPathFinder {

    public final Graph graph;

    public DjisktraPathFinder(Graph graph) {
        this.graph = graph;
    }

    public List<TunnelNode> getShortestPath(TunnelNode startNode, TunnelNode endNode) {
        PriorityQueue<DjStep> unsettledNodes = new PriorityQueue<>();
        Map<TunnelNode, DjStep> settledNodes = new HashMap<>();

        unsettledNodes.add(new DjStep(startNode));

        while (unsettledNodes.size() > 0) {
            DjStep step = unsettledNodes.remove();
            settledNodes.put(step.node, step);
            Set<TunnelEdge> edges = graph.edgesOf(step.node);
            for (TunnelEdge edge : edges) {
                TunnelNode targetNode = edge.firstNode != step.node ? edge.firstNode : edge.secondNode;
                if (settledNodes.get(targetNode) == null) {
                    List<DjStep> unprocessedStepPred = unsettledNodes.stream().filter(djStep -> djStep.node == targetNode).collect(Collectors.toList());
                    if (unprocessedStepPred.size() > 0) {
                        DjStep unprocessedStep = unprocessedStepPred.get(0);
                        if (unprocessedStep.getTotalWeight() > step.getWeightAddedToNode(unprocessedStep.node)) {
                            unprocessedStep.setPrevious(step.node);
                        }
                    } else {
                        unsettledNodes.add(new DjStep(targetNode, step.node, step.getTotalWeight()));
                    }
                }
            }
        }

        List<TunnelNode> path = new ArrayList<>();

        DjStep step = settledNodes.get(endNode);
        while (step != null) {
            path.add(step.node);
            step = settledNodes.get(step.previous);
        }
        Collections.reverse(path);

        return path.size() > 0 ? path : null;
    }
}
