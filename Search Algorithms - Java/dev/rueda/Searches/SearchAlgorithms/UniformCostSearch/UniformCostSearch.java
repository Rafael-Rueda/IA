package dev.rueda.Searches.SearchAlgorithms.UniformCostSearch;

import dev.rueda.Searches.Environment.City;
import dev.rueda.Searches.Environment.Environment;

import java.util.*;

public class UniformCostSearch {
    Environment env;
    City target;
    City beggining;

    public UniformCostSearch(Environment env) {
        this.env = env;
        this.target = null;
        this.beggining = env.getBeginning();
    }

    private City getMinCostNode(List<City> nodes) {
        double minCost = Double.MAX_VALUE;
        City minCostNode = null;
        for (City node : nodes) {
            if (node.getDistanceFromBeginning() < minCost) {
                minCost = node.getDistanceFromBeginning();
                minCostNode = node;
            }
        }
        return minCostNode;
    }

    private List<City> getOrderedByCost(List<City> nodes) {
        List<City> orderedNodes = new ArrayList<>(nodes);
        orderedNodes.sort(Comparator.comparingDouble(City::getDistanceFromBeginning));
        return orderedNodes;
    }

    public void search(City target) {
        this.target = target;

        System.out.println("Initiating Uniform Cost Search...");

        double minCost = this.beggining.getDistanceFromBeginning();
        Set<City> visited = new HashSet<>();
        Queue<City> queue = new LinkedList<>();
        queue.add(this.beggining);

        while (!queue.isEmpty()) {
            // Generate nodes
            City current = queue.poll();
            List<City> allowedNeighbors = new ArrayList<>();

            each_neighbor:
            for (City neighbor : current.getNeighbors()) {
                double currentCost = neighbor.getDistanceFromBeginning(); // + current.getDistanceFromBeginning() // Non necessary
                for (City visitedNode : visited) {
                    if (visitedNode.equals(neighbor) && currentCost > visitedNode.getDistanceFromBeginning()) {
                        continue each_neighbor;
                    }
                }
                allowedNeighbors.add(neighbor);
            }
            
            // Expand nodes

        }




    }
    public static void main(String[] args) {
        Environment env = new Environment("random", "");
        UniformCostSearch search = new UniformCostSearch(env);
        search.search(env.getLocations()[11]);
    }
}
