package dev.rueda.Searches.Environment;

import dev.rueda.Searches.DataStructures.BinarySearchTree;

import java.lang.reflect.Array;
import java.util.*;

public class Environment {

    private final City[] locations;
    private final City beginning;
    public final BinarySearchTree<City> tree;

    public Environment(String type, String cityName) {
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

        // Neighbors (distâncias Euclidiana simples)

        // São Paulo (0)
        Map<City, Double> spNeighbors = new HashMap<>();
        spNeighbors.put(locations[1], 3.52);  // Rio de Janeiro
        spNeighbors.put(locations[5], 4.53);  // Belo Horizonte
        spNeighbors.put(locations[7], 3.24);  // Curitiba
        locations[0].setNeighbors(spNeighbors);

        // Rio de Janeiro (1)
        Map<City, Double> rjNeighbors = new HashMap<>();
        rjNeighbors.put(locations[0], 3.52);  // São Paulo
        rjNeighbors.put(locations[5], 3.09);  // Belo Horizonte
        locations[1].setNeighbors(rjNeighbors);

        // Brasília (2)
        Map<City, Double> bsNeighbors = new HashMap<>();
        bsNeighbors.put(locations[5], 5.71);  // Belo Horizonte
        bsNeighbors.put(locations[9], 1.65);  // Goiânia
        locations[2].setNeighbors(bsNeighbors);

        // Fortaleza (3)
        Map<City, Double> ftNeighbors = new HashMap<>();
        ftNeighbors.put(locations[8], 5.67);  // Recife
        ftNeighbors.put(locations[10], 10.20); // Belém
        locations[3].setNeighbors(ftNeighbors);

        // Salvador (4)
        Map<City, Double> slNeighbors = new HashMap<>();
        slNeighbors.put(locations[5], 8.81);  // Belo Horizonte
        slNeighbors.put(locations[8], 6.12);  // Recife
        locations[4].setNeighbors(slNeighbors);

        // Belo Horizonte (5)
        Map<City, Double> bhNeighbors = new HashMap<>();
        bhNeighbors.put(locations[0], 4.53);  // São Paulo
        bhNeighbors.put(locations[1], 3.09);  // Rio de Janeiro
        bhNeighbors.put(locations[2], 5.71);  // Brasília
        bhNeighbors.put(locations[4], 8.81);  // Salvador
        locations[5].setNeighbors(bhNeighbors);

        // Manaus (6)
        Map<City, Double> mzNeighbors = new HashMap<>();
        mzNeighbors.put(locations[10], 11.65); // Belém
        locations[6].setNeighbors(mzNeighbors);

        // Curitiba (7)
        Map<City, Double> ctNeighbors = new HashMap<>();
        ctNeighbors.put(locations[0], 3.24);  // São Paulo
        ctNeighbors.put(locations[11], 5.00); // Porto Alegre
        locations[7].setNeighbors(ctNeighbors);

        // Recife (8)
        Map<City, Double> rcNeighbors = new HashMap<>();
        rcNeighbors.put(locations[4], 6.12);  // Salvador
        rcNeighbors.put(locations[3], 5.67);  // Fortaleza
        locations[8].setNeighbors(rcNeighbors);

        // Goiânia (9)
        Map<City, Double> gaNeighbors = new HashMap<>();
        gaNeighbors.put(locations[2], 1.65);  // Brasília
        locations[9].setNeighbors(gaNeighbors);

        // Belém (10)
        Map<City, Double> blmNeighbors = new HashMap<>();
        blmNeighbors.put(locations[3], 10.20); // Fortaleza
        blmNeighbors.put(locations[6], 11.65); // Manaus
        locations[10].setNeighbors(blmNeighbors);

        // Porto Alegre (11)
        Map<City, Double> paNeighbors = new HashMap<>();
        paNeighbors.put(locations[7], 5.00);  // Curitiba
        locations[11].setNeighbors(paNeighbors);


        if (type.equals("random")) {
            Random random = new Random();
            this.beginning = locations[random.nextInt(locations.length)];
        } else if (type.equals("default")) {
            this.beginning = Arrays.stream(locations).filter(loc -> loc.getName() == cityName).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("City not found: " + cityName));
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
