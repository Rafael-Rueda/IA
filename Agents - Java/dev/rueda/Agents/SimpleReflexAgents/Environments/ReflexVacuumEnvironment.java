package dev.rueda.Agents.SimpleReflexAgents.Environments;

import java.util.HashMap;
import java.util.Map;

public class ReflexVacuumEnvironment {

    private Map<String, String> LocationState;

    public ReflexVacuumEnvironment() {
        Map<String, String> LocationState = new HashMap<>();
        LocationState.put("A", "dirty");
        LocationState.put("B", "dirty");

        this.LocationState = LocationState;
    }

    public ReflexVacuumEnvironment(Map<String, String> LocationState) {
        this.LocationState = LocationState;
    }

    public Map<String, String> getLocationState() {
        return LocationState;
    }

    public void setLocationState(Map<String, String> LocationState) {
        this.LocationState = LocationState;
    }
}
