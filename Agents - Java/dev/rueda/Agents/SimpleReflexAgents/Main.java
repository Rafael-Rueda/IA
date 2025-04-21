package dev.rueda.Agents.SimpleReflexAgents;

import dev.rueda.Agents.SimpleReflexAgents.Agents.ReflexVacuumAgent;
import dev.rueda.Agents.SimpleReflexAgents.Environments.ReflexVacuumEnvironment;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Map<String, String> initialEnvironmentState = new HashMap<>();

        initialEnvironmentState.put("A", "dirty");
        initialEnvironmentState.put("B", "clean");
        initialEnvironmentState.put("C", "dirty");
        initialEnvironmentState.put("D", "dirty");
        initialEnvironmentState.put("E", "dirty");
        initialEnvironmentState.put("F", "dirty");
        initialEnvironmentState.put("G", "clean");
        initialEnvironmentState.put("H", "dirty");
        initialEnvironmentState.put("I", "dirty");
        initialEnvironmentState.put("J", "dirty");
        initialEnvironmentState.put("K", "dirty");
        initialEnvironmentState.put("L", "clean");
        initialEnvironmentState.put("M", "clean");
        initialEnvironmentState.put("N", "clean");
        initialEnvironmentState.put("O", "dirty");
        initialEnvironmentState.put("P", "dirty");
        initialEnvironmentState.put("Q", "dirty");
        initialEnvironmentState.put("R", "clean");


        ReflexVacuumEnvironment env = new ReflexVacuumEnvironment(initialEnvironmentState);
        ReflexVacuumAgent agent = new ReflexVacuumAgent(env, env.getLocationState().keySet().iterator().next());

        agent.act();
    }
}
