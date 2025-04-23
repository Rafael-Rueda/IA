package dev.rueda.Agents.ModelBasedReflexAgents.Environments;

import java.util.*;

public class ReflexVacuumEnvironment {

    private Map<String, String> locationState;
    private List<String> states;

    public ReflexVacuumEnvironment() {
        Map<String, String> locationState = new LinkedHashMap<>();
        locationState.put("A", "OBFUSCATED");
        locationState.put("B", "OBFUSCATED");
        locationState.put("C", "OBFUSCATED");
        locationState.put("D", "OBFUSCATED");
        locationState.put("E", "OBFUSCATED");

        List<String> states = new ArrayList<>();
        states.add("dirty");
        states.add("OBFUSCATED");
        states.add("clean");

        this.locationState = locationState;
        this.states = states;
    }

    public ReflexVacuumEnvironment(Map<String, String> locationState) {
        this.locationState = locationState;

        Set<String> states = new HashSet<>();
        locationState.keySet().forEach(key -> states.add(locationState.get(key)));
        this.states = new ArrayList<>(states);
    }

    private String randomState() {
        Random rand = new Random();
        int index = rand.nextInt(states.size());

        return states.get(index);
    }

    public Map<String, String> getLocationState() {
        return locationState;
    }

    public void setLocationState(Map<String, String> locationState) {
        this.locationState = locationState;
    }

    public String randomIncidental() {
        Random rand = new Random();
        int index = rand.nextInt(locationState.size());

        List<String> keys = new ArrayList<>(locationState.keySet());
        String chosenKey = keys.get(index);
        String chosenState = randomState();
        System.out.println("<!> Incidental [" + chosenState + "] occurred in location: " + chosenKey + " <!>");

        this.locationState.put(chosenKey, chosenState);

        return chosenKey;
    }

    public void reReadLocationState(String location) {
        String state = randomState();
        this.locationState.put(location, state);
    }
}
