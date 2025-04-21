package dev.rueda.Agents.SimpleReflexAgents.Agents;

import dev.rueda.Agents.SimpleReflexAgents.Environments.ReflexVacuumEnvironment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ReflexVacuumAgent {

    private ReflexVacuumEnvironment environment;
    private String location;
    private String moving;
    private List<String> pastStates; // Memória básica

    public ReflexVacuumAgent(ReflexVacuumEnvironment env, String location) {
        this.environment = env;
        this.location = location;
        this.moving = "right";
        this.pastStates = new ArrayList<>();
    }

    private void move(String direction, String newLocation) {
        String oldLocation = this.location;

        if ("right".equals(direction)) {
            this.location = newLocation;
            System.out.println("Moving right: " + oldLocation + " -> " + newLocation);
        } else if ("left".equals(direction)) {
            this.location = newLocation;
            System.out.println("Moving left: " + newLocation + " <- " + oldLocation);
        }
    }

    public void act() {
        Map<String, String> locationState = this.environment.getLocationState();

        while (true) {
            String state = locationState.get(location);

            if ("dirty".equals(state)) {
                locationState.put(location, "clean");
                System.out.println("Dirty detected ! Cleaning " + location + "...");
                this.environment.setLocationState(locationState);
            } else if ("clean".equals(state)) {
                Iterator<String> iterator = locationState.keySet().stream().iterator();
                String previous = null;
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    if (key.equals(location)) {
                        if ("right".equals(moving)) {
                            if (iterator.hasNext()) {
                                this.move(moving, iterator.next());
                                break;
                            } else {
                                this.moving = "left";
                                if (previous != null) {
                                    this.move(moving, previous);
                                    break;
                                } else {
                                    System.out.println("Impossible to move.");
                                }
                            }
                        } else if ("left".equals(moving)) {
                            if (previous != null) {
                                this.move(moving, previous);
                                break;
                            } else {
                                moving = "right";
                                if (iterator.hasNext()) {
                                    this.move(moving, iterator.next());
                                    break;
                                } else {
                                    System.out.println("Impossible to move.");
                                }
                            }
                        }
                    }
                    previous = key;
                }
            } else break;

            // Aplicação da memória básica para evitar loop infinito
            this.pastStates.add(state);

            boolean foundDirty = false;

            for (int i = 0; i < environment.getLocationState().size(); i++) {
                if ("dirty".equals(pastStates.reversed().get(i))) {
                    foundDirty = true;
                    break;
                }
            }

            if (!foundDirty) {
                System.out.println("No dirty detected anymore. Stopping the execution.");
                break;
            }
        }
    }
}
