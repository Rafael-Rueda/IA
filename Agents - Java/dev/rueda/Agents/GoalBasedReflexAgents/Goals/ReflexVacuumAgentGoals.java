package dev.rueda.Agents.GoalBasedReflexAgents.Goals;

import dev.rueda.Agents.GoalBasedReflexAgents.Agents.ReflexVacuumAgent;

public enum ReflexVacuumAgentGoals {
    MAP_ENVIRONMENT,
    CLEAN_DIRTY_LOCATIONS,
    CHARGE_BATTERY,
    IDLE;

    public double estimateCost(ReflexVacuumAgent agent) {
        int envSize = agent.getEnvironmentSize();
        double moveCost = agent.getMoveCost();
        double cleanCost = agent.getCleanCost();

        switch (this) {
            case MAP_ENVIRONMENT:
                return (envSize * moveCost * 2);
            case CLEAN_DIRTY_LOCATIONS:
                return (envSize * moveCost * 4) + (envSize * cleanCost);
            case CHARGE_BATTERY:
                return 0;
            case IDLE:
                return 0;
            default:
                return 0;
        }
    }
}
