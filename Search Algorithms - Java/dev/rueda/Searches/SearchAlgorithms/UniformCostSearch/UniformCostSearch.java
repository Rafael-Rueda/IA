package dev.rueda.Searches.SearchAlgorithms.UniformCostSearch;

import dev.rueda.Searches.Environment.City;
import dev.rueda.Searches.Environment.Environment;

import java.util.*;

public class UniformCostSearch {
    Environment env;
    City target;
    City beggining;
    private List<City> bestPath = new ArrayList<>();

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

    private void printBestPath() {
        if (bestPath == null || bestPath.isEmpty()) {
            System.out.println("Nenhum caminho foi encontrado.");
            return;
        }

        System.out.println("Best path found to reach " + target.getName() + ":");
        for (int i = 0; i < bestPath.size(); i++) {
            System.out.print(bestPath.get(i).getName());
            if (i < bestPath.size() - 1) System.out.print(" -> ");
        }
        System.out.println();
    }

    public void search(City target) {
        this.target = target;

        System.out.println("Initiating Uniform Cost Search...");

        Map<City, Double> costSoFar = new HashMap<>();
        costSoFar.put(this.beggining, 0.0);
        Set<City> visited = new HashSet<>();
        Queue<City> queue = new LinkedList<>();
        queue.add(this.beggining);

        Map<City, List<City>> paths = new HashMap<>();
        paths.put(this.beggining, new ArrayList<>(List.of(this.beggining)));

        while (!queue.isEmpty()) {
            // Generate nodes
            City current = queue.poll();
            List<City> allowedNeighbors = new ArrayList<>();

            for (Map.Entry<City, Double> neighborEntry : current.getNeighbors().entrySet()) {
                City neighbor = neighborEntry.getKey();
                double edgeCost = neighborEntry.getValue();
                double newCost = costSoFar.get(current) + edgeCost;

                if (!costSoFar.containsKey(neighbor) || newCost < costSoFar.get(neighbor)) {
                    costSoFar.put(neighbor, newCost);

                    List<City> pathToNeighbor = new ArrayList<>(paths.get(current));
                    pathToNeighbor.add(neighbor);
                    paths.put(neighbor, pathToNeighbor);

                    allowedNeighbors.add(neighbor);
                }
            }


            // Expand nodes
            List<City> list = new ArrayList<>(queue);
            List<City> orderedNodes = getOrderedByCost(list);
            Queue<City> sortedQueue = new LinkedList<>(orderedNodes);
            queue = sortedQueue;

            visited.add(current);

            queue.addAll(getOrderedByCost(allowedNeighbors));

            if (current.equals(target)) {
                this.bestPath = paths.get(current);
                break;
            }
        }
        printBestPath();
    }
    public static void main(String[] args) {
        Environment env = new Environment("default", "Belo Horizonte");
        UniformCostSearch search = new UniformCostSearch(env);
        search.search(env.getLocations()[7]);

        System.out.println("\n" + "============ DISTANCE FROM BEGGINING (" + env.getBeginning() + ") ============");
        System.out.println();
        for(City city : env.getLocations()) {
            System.out.print(city.getName());
            System.out.println(" -> " + city.getDistanceFromBeginning());
        }

        System.out.println("\n" + "============ DISTANCE BETWEEN CITIES ============");
        for (City city : env.getLocations()) {
            for (Map.Entry<City, Double> neighbor : city.getNeighbors().entrySet()) {
                System.out.println(city.getName() + " -> " + neighbor.getKey().getName() + " : " + neighbor.getValue());
            }
        }

    }
}
