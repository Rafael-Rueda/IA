package dev.rueda.Searches.Environment;

import dev.rueda.Searches.DataStructures.BinarySearchTree;

import java.util.Comparator;
import java.util.Random;

public class Environment {

    private final City[] locations;
    private final City beginning;
    public final BinarySearchTree<City> tree;

    public Environment(String type) {
        this.locations = new City[12];

        locations[0] = new City("São Paulo", 11895578, 1521.11, -23.5505, -46.6333);
        locations[1] = new City("Rio de Janeiro", 6729894, 1255.00, -22.9068, -43.1729);
        locations[2] = new City("Brasília", 2982818, 5760.78, -15.7939, -47.8828);
        locations[3] = new City("Fortaleza", 2574412, 313.80, -3.7172, -38.5433);
        locations[4] = new City("Salvador", 2568928, 693.80, -12.9777, -38.5016);
        locations[5] = new City("Belo Horizonte", 2416339, 331.40, -19.9167, -43.9345);
        locations[6] = new City("Manaus", 2279686, 11401.00, -3.1190, -60.0217);
        locations[7] = new City("Curitiba", 1829225, 319.40, -25.4284, -49.2733);
        locations[8] = new City("Recife", 1587707, 218.00, -8.0476, -34.8770);
        locations[9] = new City("Goiânia", 1494599, 728.84, -16.6869, -49.2648);
        locations[10] = new City("Belém", 1398531, 1059.40, -1.4558, -48.4902);
        locations[11] = new City("Porto Alegre", 1483771, 496.80, -30.0346, -51.2177);

        // Neightbors
        // São Paulo
        locations[0].setNeighbors(new City[]{locations[1], locations[5], locations[7]});
        // Rio de Janeiro
        locations[1].setNeighbors(new City[]{locations[0], locations[5]});
        // Brasília
        locations[2].setNeighbors(new City[]{locations[5], locations[9]});
        // Fortaleza
        locations[3].setNeighbors(new City[]{locations[8], locations[10]});
        // Salvador
        locations[4].setNeighbors(new City[]{locations[5], locations[8]});
        // Belo Horizonte
        locations[5].setNeighbors(new City[]{locations[0], locations[1], locations[2], locations[4]});
        // Manaus
        locations[6].setNeighbors(new City[]{locations[10]});
        // Curitiba
        locations[7].setNeighbors(new City[]{locations[0], locations[11]});
        // Recife
        locations[8].setNeighbors(new City[]{locations[4], locations[3]});
        // Goiânia
        locations[9].setNeighbors(new City[]{locations[2]});
        // Belém
        locations[10].setNeighbors(new City[]{locations[3], locations[6]});
        // Porto Alegre
        locations[11].setNeighbors(new City[]{locations[7]});

        if (type.equals("random")) {
            Random random = new Random();
            this.beginning = locations[random.nextInt(locations.length)];
        } else if (type.equals("default")) {
            this.beginning = locations[0]; // São Paulo
        } else {
            throw new IllegalArgumentException("Invalid environment type.");
        }

        for (City city : locations) {
            city.setDistanceFromBeginning(this.beginning);
        }

        this.tree = new BinarySearchTree<>(Comparator.comparingDouble(City::getDistanceFromBeginning));
        this.tree.insert(this.beginning);

        for (City city : locations) {
            if (!city.equals(this.beginning)) {
                this.tree.insert(city);
            }
        }
    }

    public City[] getLocations() {
        return this.locations;
    }

    public City getBeginning() {
        return this.beginning;
    }
}
