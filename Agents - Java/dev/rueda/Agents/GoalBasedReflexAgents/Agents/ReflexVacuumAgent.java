package dev.rueda.Agents.GoalBasedReflexAgents.Agents;

import dev.rueda.Agents.GoalBasedReflexAgents.Environments.ReflexVacuumEnvironment;
import dev.rueda.Agents.GoalBasedReflexAgents.Goals.ReflexVacuumAgentGoals;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ReflexVacuumAgent {

    private ReflexVacuumEnvironment environment;
    private String location;
    private String moving;
    private Map<String, String> internalModel;
    private Stack<ReflexVacuumAgentGoals> goal;
    private int environmentSize;
    private final Scanner scanner = new Scanner(System.in);

    // Agent's specs
    private double battery = 20;
    private final double moveCost = 1;
    private final double cleanCost = 1;

    public ReflexVacuumAgent(ReflexVacuumEnvironment env) {
        this.environment = env;
        this.environmentSize = this.environment.getLocationState().size();
        this.location = null;
        this.moving = "right";
        this.internalModel = this.environment.getLocationState();
        this.goal = new Stack<>();
    }

    public ReflexVacuumAgent(ReflexVacuumEnvironment env, String location) {
        this.environment = env;
        this.environmentSize = this.environment.getLocationState().size();
        this.location = location;
        this.moving = "right";
        this.internalModel = this.environment.getLocationState();
        this.goal = new Stack<>();
    }

    public int getEnvironmentSize() {
        return this.environmentSize;
    }

    public double getMoveCost() {
        return this.moveCost;
    }

    public double getCleanCost() {
        return this.cleanCost;
    }

    public double getBattery() {
        return this.battery;
    }

    private void decide() {
        // Checks if the battery is enough to cover the entire environment.
        if ((this.location == null) && (this.internalModel.isEmpty())) {
            if (this.environmentSize <= 0) {
                System.out.println("How long is the environment ? (amount of locations)\n");
                int size = Integer.parseInt(scanner.nextLine());
                this.environmentSize = size;
            }

            if ((this.battery != 100) && ((this.battery - ((this.environmentSize * this.moveCost) * 2)) < 0)) {
                System.out.println("The agent's battery is not sufficient to cover this area. Recharge the battery " +
                        "manually and try again.\n");
                scanner.close();
                System.exit(0);
            } else if (this.battery == 100 && ((this.battery - ((this.environmentSize * this.moveCost) * 2)) < 0)) {
                System.out.println("The agent's battery in total is not sufficient to cover this area. Exiting...\n");
                scanner.close();
                System.exit(0);
            }

            whatsTheGoal(scanner);
            checkGoal();
        } else {
            double cost = 0;
            String reached = null;

            this.environmentSize = internalModel.size();

            for (String key : internalModel.keySet()) {
                if (!key.equals(this.location) && reached == null) {
                    cost += this.moveCost;
                    reached = key;
                } else if (reached != null) {
                    break;
                }
            }

            if (this.battery != 100 && (this.battery - (this.environmentSize * this.moveCost * 2)) < 0) {
                System.out.println("The agent's battery is not sufficient to cover this area. Trying to recharge the " +
                        "battery...\n");

                if (this.battery - cost < 0) {
                    System.out.println("The agent's battery is not sufficient to reach the recharge area. " +
                            "Please, manually recharge the battery.\n");
                    scanner.close();
                    System.exit(0);
                }

                this.goal.push(ReflexVacuumAgentGoals.CHARGE_BATTERY);
            } else if (this.battery == 100 && (this.battery - (this.environmentSize * this.moveCost * 2)) < 0) {
                System.out.println("The agent's battery in total is not sufficient to cover this area. Exiting...\n");
                scanner.close();
                System.exit(0);
            } else {
                whatsTheGoal(scanner);
                checkGoal();
            }
        }

        if (null == this.environment.getChargePoint()) {
            System.out.println("Unable to find the recharge area. Looking for it, while mapping the region.\n");
            this.goal.push(ReflexVacuumAgentGoals.MAP_ENVIRONMENT);
        }
    }

    private void whatsTheGoal(Scanner scanner) {
        System.out.println("Battery level is sufficient. Proceeding with chosen task.\n");
        System.out.println("What is the agent's goal ?\n");
        System.out.println("1 - Map the environment.\n2 - Clean dirty locations.\n3 - Recharge the battery\n" +
                "4 - Nothing\n");
        int goal = Integer.parseInt(scanner.nextLine());

        try {
            this.goal.push(ReflexVacuumAgentGoals.values()[goal - 1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Not a valid goal. Please, try again.\n");
            System.exit(0);
        }
    }

    private void checkGoal() {
        if (this.goal.isEmpty()) return;

        ReflexVacuumAgentGoals nextGoal = this.goal.peek();
        double estimatedCost = nextGoal.estimateCost(this);

        if (this.battery - estimatedCost < 0) {
            System.out.println("[!] Battery insufficient for the next goal. Scheduling recharge before proceeding.");
            this.goal.push(ReflexVacuumAgentGoals.CHARGE_BATTERY);
            if (this.goal.peek().estimateCost(this) < 0) {

            }
        }
    }

    private void rechargeBattery() {
        List<String> keys = this.internalModel.keySet().stream().toList();

        int locationIndex = keys.indexOf(this.environment.getChargePoint());
        int atualIndex = keys.indexOf(this.location);

        String state = internalModel.get(this.environment.getChargePoint());

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
        System.out.println("Recharging battery... " + this.battery + "%" + " -> " + "100%");
        this.battery = 100;
    }

    private void move(String direction, String newLocation) {
        String oldLocation = this.location;

        this.battery = this.battery - (moveCost);

        if ("right".equals(direction)) {
            this.location = newLocation;
            System.out.println("Moving right: " + oldLocation + " -> " + newLocation);
        } else if ("left".equals(direction)) {
            this.location = newLocation;
            System.out.println("Moving left: " + newLocation + " <- " + oldLocation);
        }
    }

    private void clean() {
        Map<String, String> locationState = this.internalModel;
        this.battery = this.battery - (cleanCost);
        locationState.put(this.location, "clean");
        System.out.println("Dirty detected ! Cleaning " + location + "...");
        this.internalModel = locationState;
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
        this.internalModel = this.environment.getLocationState();
        this.moving = oldDirection;
    }

    public void showBattery() {
        System.out.println("[!] BATTERY LEVEL: " + this.battery + "%");
    }

    private void randomMovingUntilHit() {
        Random random = new Random();
        String initialMoving = this.moving;
        boolean hasChargePoint = this.environment.getChargePoint() != null;

        if (!this.internalModel.isEmpty()) {
            System.out.println("Environment mounted but location or charge area not found... Detecting location and " +
                    "charge area automatically...\n");
            List<String> keys = this.internalModel.keySet().stream().toList();

            int locationAmount = keys.size();
            boolean stop = false;
            int counterMoving = 1;
            String chargePoint = null;

            this.moving = "right";

            while (!stop && (counterMoving < locationAmount)) {

                boolean willHit = random.nextInt(50000) == 0; // 20% chance of hitting a wall.
                boolean willBeAChargePoint = random.nextInt(10) == 0; // 10% chance of being a charge spot

                move(this.moving, counterMoving + " step(s) to the " + this.moving);

                if (willBeAChargePoint) {
                    chargePoint = String.valueOf(numberToLetter(counterMoving - 1));
                }

                if (willHit) {
                    stop = true;
                }

                if (++counterMoving >= locationAmount && !hasChargePoint && chargePoint == null) {
                    chargePoint = String.valueOf(numberToLetter(counterMoving - 1));
                }
            }

            if (counterMoving == (locationAmount) && chargePoint != null
                    && chargePoint.equals(numberToLetter(counterMoving - 1) +
                    "")) {
                this.environment.setChargePoint(numberToLetter(counterMoving - 1) + "");
            }

            this.moving = "left";

            if (stop) {
                this.location = String.valueOf(numberToLetter(locationAmount - 1));

                if (this.environment.getChargePoint() == null) {
                    for (int i = 1; i <= locationAmount - 1; i++) {
                        move(this.moving, keys.get(locationAmount - i - 1));
                        this.location = String.valueOf(numberToLetter(locationAmount - i - 1));
                        if (this.location.equals(chargePoint)) {
                            this.environment.setChargePoint(this.location);
                            break;
                        }
                    }
                } else {
                    for (int i = 1; i <= locationAmount - 1; i++) {
                        move(this.moving, keys.get(locationAmount - i - 1));
                        this.location = String.valueOf(numberToLetter(locationAmount - i - 1));
                        if (this.location.equals(this.environment.getChargePoint())) {
                            break;
                        }
                    }
                }

                if (this.environment.getChargePoint() == null) {
                    this.environment.setChargePoint(this.location);
                }
            } else {
                this.location = String.valueOf(numberToLetter(locationAmount - 1));

                if (counterMoving == (locationAmount) && chargePoint != null
                        && chargePoint.equals(numberToLetter(counterMoving - 1) +
                        "")) {
                    this.environment.setChargePoint(numberToLetter(counterMoving - 1) + "");
                }

                if (this.environment.getChargePoint() == null) {
                    for (int i = 1; i <= locationAmount - 1; i++) {
                        move(this.moving, keys.get(locationAmount - i - 1));
                        this.location = String.valueOf(numberToLetter(locationAmount - i - 1));
                        if (this.location.equals(chargePoint)) {
                            this.environment.setChargePoint(this.location);
                            break;
                        }
                    }
                } else {
                    for (int i = 1; i <= locationAmount - 1; i++) {
                        move(this.moving, keys.get(locationAmount - i - 1));
                        this.location = String.valueOf(numberToLetter(locationAmount - i - 1));
                        if (this.location.equals(this.environment.getChargePoint())) {
                            break;
                        }
                    }
                }
            }
        } else {
            System.out.println("Environment not mounted. Mapping the environment automatically...\n");

            int counterMoving = 1;
            String chargePoint = null;
            boolean stop = false;
            int locationAmount = this.environmentSize;

            while (!stop && (counterMoving < this.environmentSize)) {
                boolean willHit = random.nextInt(5) == 0; // 20% chance of hitting a wall.
                boolean willBeAChargePoint = random.nextInt(10) == 0; // 10% chance of being a charge spot

                move(this.moving, counterMoving + " step(s) to the " + this.moving);

                if (willBeAChargePoint) {
                    chargePoint = String.valueOf(numberToLetter(counterMoving - 1));
                }

                if (willHit) {
                    stop = true;
                }

                if (++counterMoving >= locationAmount && !hasChargePoint && chargePoint == null) {
                    chargePoint = String.valueOf(numberToLetter(counterMoving - 1));
                }
            }

            this.moving = "left";
            this.location = String.valueOf(numberToLetter(locationAmount - 1));

            if (this.internalModel.get(this.location) == null || this.internalModel.get(this.location).isEmpty()) {
                this.internalModel.put(this.location, this.environment.randomState());
            }

            if (chargePoint != null && chargePoint.equals(numberToLetter(counterMoving - 1) + "")) {
                this.environment.setChargePoint(numberToLetter(counterMoving - 1) + "");
            }

            for (int i = 1; i <= locationAmount - 1; i++) {
                move(this.moving, numberToLetter(locationAmount - i - 1) + "");
                this.location = String.valueOf(numberToLetter(locationAmount - i - 1));

                if (this.environment.getChargePoint() == null && this.location.equals(chargePoint)) {
                    this.environment.setChargePoint(this.location);
                }

                if (this.internalModel.get(this.location) == null || this.internalModel.get(this.location).isEmpty()) {
                    this.internalModel.put(this.location, this.environment.randomState());
                }
            }

            if (this.environment.getChargePoint() == null) {
                this.environment.setChargePoint(this.location);
            }

            LinkedHashMap<String, String> reversed = new LinkedHashMap<>();

            List<Map.Entry<String, String>> entries = new ArrayList<>(this.internalModel.entrySet());
            Collections.reverse(entries);

            for (Map.Entry<String, String> entry : entries) {
                reversed.put(entry.getKey(), entry.getValue());
            }

            this.internalModel = reversed;
        }

        System.out.println("[!] CHARGE POINT: " + this.environment.getChargePoint());
        System.out.println("[!] ACTUAL LOCATION: " + this.location);

        this.moving = initialMoving;
    }

    private char numberToLetter(int num) {
        return (char) ('A' + num);
    }

    private void map() {
        if (this.internalModel.isEmpty() || this.location == null || this.environment.getChargePoint() == null) {
            randomMovingUntilHit();
        } else {
            System.out.println("Environment already mounted. Mapping the current environment looking for states...\n");

            List<String> keys = new ArrayList<>(this.internalModel.keySet());

            int locationAmount = keys.size();
            int locationAmountLeft =
                    (int) this.internalModel.keySet().stream()
                            .takeWhile(key -> !key.equals(this.location))
                            .count();
            int locationAmountRight = locationAmount - locationAmountLeft - 1;

            String initialMoving = this.moving;

            if (locationAmountLeft > locationAmountRight) {
                this.moving = "right";

                if (locationAmountRight != 0) {
                    for (int i = 1; i <= locationAmountRight; i++) {
                        move(this.moving, keys.get(locationAmountLeft + i));
                    }
                }

                this.moving = "left";

                for (int i = locationAmount - 2; i >= 0; i--) {
                    move(this.moving, keys.get(i));
                    this.internalModel.put(this.location, this.environment.randomState());
                }
            } else {
                this.moving = "left";

                for (int i = 1; i <= locationAmountLeft; i++) {
                    move(this.moving, keys.get(locationAmount - locationAmountRight - i - 1));
                }

                this.moving = "right";

                for (int i = 1; i < locationAmount; i++) {
                    move(this.moving, keys.get(i));
                    this.internalModel.put(this.location, this.environment.randomState());
                }
            }
            System.out.println("ENVIRONMENT SUCCESSFULLY MAPPED: " + this.internalModel);
        }

    }

    private void reflex() {
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
                System.out.println("Nothing to clean/desobfuscate.");
                break;
            }
        }
    }

    public void act() {
        boolean running = true;

        while (running) {
            decide();

            while (!this.goal.isEmpty()) {
                ReflexVacuumAgentGoals goal = this.goal.pop();
                switch (goal) {
                    case MAP_ENVIRONMENT: {
                        map();
                        showBattery();
                        break;
                    }
                    case CLEAN_DIRTY_LOCATIONS: {
                        reflex();
                        showBattery();
                        break;
                    }
                    case CHARGE_BATTERY: {
                        rechargeBattery();
                        showBattery();
                        break;
                    }
                    case IDLE: {
                        running = false;
                        break;
                    }
                }
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
