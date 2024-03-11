package com.Ox08.experiments.migrated.esb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
public abstract class AbstractStep {
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass().getName());
    @Autowired
    protected ProcessService ps;
    public void nextStep(ProcessContext ctx) {
        ps.nextStep(getClass().getSimpleName(), ctx);
    }
}
