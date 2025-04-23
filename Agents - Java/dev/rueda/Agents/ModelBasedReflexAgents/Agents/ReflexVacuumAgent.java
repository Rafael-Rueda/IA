package dev.rueda.Agents.ModelBasedReflexAgents.Agents;

import dev.rueda.Agents.ModelBasedReflexAgents.Environments.ReflexVacuumEnvironment;

import java.util.*;
import java.util.stream.Collectors;

public class ReflexVacuumAgent {

    private ReflexVacuumEnvironment environment;
    private String location;
    private String moving;
    private Map<String, String> internalModel;

    public ReflexVacuumAgent(ReflexVacuumEnvironment env, String location) {
        this.environment = env;
        this.location = location;
        this.moving = "right";
        this.internalModel = new LinkedHashMap<>();
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

    private void clean() {
        Map<String, String> locationState = this.environment.getLocationState();
        locationState.put(this.location, "clean");
        System.out.println("Dirty detected ! Cleaning " + location + "...");
        this.environment.setLocationState(locationState);
    }

    private void desobfuscate() {
        Iterator<String> iterator = this.internalModel.keySet().stream().iterator();
        String previous = null;
        String key = null;
        String oldDirection = this.moving;

        System.out.println("[!] Desobfuscating " + this.location + "...");

        while (iterator.hasNext()) {
            this.moving = "right";
            key = iterator.next();

            if (key.equals(location)) {
                break;
            }

            previous = key;
        }

        if (iterator.hasNext()) {
            this.move(moving, iterator.next());
            this.moving = "left";
            this.move(moving, key);
        } else {
            this.moving = "left";
            if (previous != null) {
                this.move(moving, previous);
                this.moving = "right";
                this.move(moving, key);
            } else {
                System.out.println("Impossible to move.");
            }
        }
        this.environment.reReadLocationState(this.location);
        this.moving = oldDirection;
    }

    public void act() {
        this.internalModel = this.environment.getLocationState();

        while (true) {
            String state = internalModel.get(location);

            if ("dirty".equals(state)) {
                this.clean();
            } else if ("clean".equals(state)) {
                System.out.println(location + " is clean.");
                Iterator<String> iterator = this.internalModel.keySet().stream().iterator();
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
                                this.moving = "right";
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
            } else if ("OBFUSCATED".equals(state)) {
                desobfuscate();
            }

            if (!internalModel.containsValue("dirty") && !internalModel.containsValue("OBFUSCATED")) {
                break;
            }
        }
    }

    public void act(String location) {
        List<String> keys = this.internalModel.keySet().stream().toList();

        int locationIndex = keys.indexOf(location);
        int atualIndex = keys.indexOf(this.location);

        String state = internalModel.get(location);

        if (!"clean".equals(state)) {
            while (locationIndex != atualIndex) {
                if (locationIndex > atualIndex && atualIndex + 1 < keys.size()) {
                    this.moving = "right";
                    this.move(moving, keys.get(atualIndex + 1));
                } else if (locationIndex < atualIndex && atualIndex - 1 >= 0) {
                    this.moving = "left";
                    this.move(moving, keys.get(atualIndex - 1));
                }
                atualIndex = keys.indexOf(this.location);
            }
        }

        if ("dirty".equals(state)) {
            this.clean();
        } else if ("OBFUSCATED".equals(state)) {
            this.desobfuscate();
        }

        System.out.println(location + " is clean.");
    }
}
