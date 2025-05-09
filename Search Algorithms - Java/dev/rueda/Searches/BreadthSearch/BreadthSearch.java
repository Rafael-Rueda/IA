package dev.rueda.Searches.BreadthSearch;

import dev.rueda.Searches.Environment.City;
import dev.rueda.Searches.Environment.Environment;

import java.util.*;

public class BreadthSearch {
    Environment env;
    City target;
    City beggining;

    public BreadthSearch(Environment env) {
        this.env = env;
        this.target = null;
        this.beggining = env.getBeginning();
    }

    private void reconstructPath(HashMap<City, City> pathPrev, City target) {
        System.out.println("Path:");

        City currentPrev = pathPrev.get(target);

        List<City> path = new LinkedList<>();

        path.add(target);

        while (currentPrev != null) {
            path.add(currentPrev);
            currentPrev = pathPrev.get(currentPrev);
        }

        for(int i = 0; i < path.size(); i++) {
            System.out.print(path.reversed().get(i) + " -> ");
        }
    }

    public void search(City target) {
        this.target = target;

        System.out.println("Initiating Breadth Search...");
        Queue<City> queue = new LinkedList<>();
        Set<City> visited = new HashSet<>();

        queue.add(this.beggining);
        visited.add(this.beggining);
        HashMap<City, City> pathPrev = new HashMap<>();
        pathPrev.put(this.beggining, null);

        while (!queue.isEmpty()) {
            City current = queue.poll();

            City[] neighbors = current.getNeighbors();

            if (current.equals(this.target)) {
                reconstructPath(pathPrev, current);
                return;
            }

            for (City neighbor : neighbors) {
                if (visited.contains(neighbor)) continue;

                visited.add(neighbor);
                queue.add(neighbor);
                pathPrev.put(neighbor, current);
            }
        }
    }
    public static void main(String[] args) {
        Environment env = new Environment("random");
        BreadthSearch search = new BreadthSearch(env);
        search.search(env.getLocations()[11]);
    }
}
