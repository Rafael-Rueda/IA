package dev.rueda.Agents.GoalBasedReflexAgents;

import dev.rueda.Agents.GoalBasedReflexAgents.Agents.ReflexVacuumAgent;
import dev.rueda.Agents.GoalBasedReflexAgents.Environments.ReflexVacuumEnvironment;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
//        ReflexVacuumEnvironment env = new ReflexVacuumEnvironment(); // The agent creates the mapping!
//        ReflexVacuumAgent agent = new ReflexVacuumAgent(env);
//
//        agent.act();

        Map<String, String> initialEnvironmentState = new LinkedHashMap<>();
        initialEnvironmentState.put("A", "OBFUSCATED");
        initialEnvironmentState.put("B", "dirty");
        initialEnvironmentState.put("C", "OBFUSCATED");
        initialEnvironmentState.put("D", "clean");
        initialEnvironmentState.put("E", "dirty");
        initialEnvironmentState.put("F", "clean");
        initialEnvironmentState.put("G", "OBFUSCATED");
        initialEnvironmentState.put("H", "dirty");
        initialEnvironmentState.put("I", "clean");
        initialEnvironmentState.put("J", "OBFUSCATED");

        ReflexVacuumEnvironment env = new ReflexVacuumEnvironment();
        ReflexVacuumAgent agent = new ReflexVacuumAgent(env);

        agent.act();

    }
}
