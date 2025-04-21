package dev.rueda.Agents.ModelBasedReflexAgents;

import dev.rueda.Agents.ModelBasedReflexAgents.Agents.ReflexVacuumAgent;
import dev.rueda.Agents.ModelBasedReflexAgents.Environments.ReflexVacuumEnvironment;

public class Main {
    public static void main(String[] args) {
        ReflexVacuumEnvironment env = new ReflexVacuumEnvironment(); // Considere que o local ja foi mapeado !
        ReflexVacuumAgent agent = new ReflexVacuumAgent(env, env.getLocationState().keySet().iterator().next());

        agent.act();

        System.out.println("=-=-=-= CLEANED EVERYTHING =-=-=-=");

        for (int i = 0; i < 10; i++) {
            String key = env.randomIncidental();
            agent.act(key);
        }

    }
}
