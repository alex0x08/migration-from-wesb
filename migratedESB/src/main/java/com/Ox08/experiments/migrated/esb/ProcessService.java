package com.Ox08.experiments.migrated.esb;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class ProcessService {
    private final Map<String, ProcessDefinition> defs = new HashMap<>();
    public void nextStep(String current, ProcessContext ctx) {
        if (!defs.containsKey(current)) {
            return;
        }
        ProcessDefinition d = defs.get(current);
        if (d.nextSteps.isEmpty()) {
            return;
        }
        for (String s : d.nextSteps) {
            if (!defs.containsKey(s)) {
                continue;
            }
            ProcessDefinition nd = defs.get(s);
            if (d.async) {
                runAsync(nd.step, ctx);
            } else {
                nd.step.exec(ctx);
            }
        }
    }
    public void call(String name, ProcessContext ctx) {
        if (!defs.containsKey(name)) {
            return;
        }
        ProcessDefinition d = defs.get(name);
        d.step.exec(ctx);
    }
    void addStep(ProcessStep step, boolean async, List<String> nextSteps) {
        defs.put(step.getClass().getSimpleName(), new ProcessDefinition(step, async, nextSteps));
    }
    @Async
    void runAsync(ProcessStep step, ProcessContext ctx) {
        step.exec(ctx);
    }
    static class ProcessDefinition {
        private final ProcessStep step;
        private final boolean async;
        private final List<String> nextSteps;
        ProcessDefinition(ProcessStep step, boolean async, List<String> nextSteps) {
            this.step = step;
            this.async = async;
            this.nextSteps = nextSteps;
        }
    }
}
