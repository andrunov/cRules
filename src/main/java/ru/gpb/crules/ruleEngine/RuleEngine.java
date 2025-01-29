package ru.gpb.crules.ruleEngine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class RuleEngine {

    private static final Logger LOG = LogManager.getLogger();

    public String performRules(Object ...args) {
        LOG.info("RULES STARTED");
        /*
        some code...
         */
        LOG.info("RULES FINISHED");
        return null;
    }
}
