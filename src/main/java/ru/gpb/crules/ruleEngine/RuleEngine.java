package ru.gpb.crules.ruleEngine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.engine.variable.VariableMap;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.gpb.crules.Application;
import ru.gpb.crules.businessModel.CreditProgram;
import ru.gpb.crules.businessModel.CreditRequest;

import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class RuleEngine {

    private static final Logger LOG = LogManager.getLogger();
    private static final String RULES_PATH = "Rules";

    private DmnEngine dmnEngine;
    private Map<String, DmnDecision> rules;

    @EventListener(ApplicationReadyEvent.class)
    public void readDir() throws IOException {
        rules  = new HashMap<>();
        String basePath = Application.class.getClassLoader().getResource(RULES_PATH).getPath();
        File baseDir = new File(basePath);
        dmnEngine = DmnEngineConfiguration.createDefaultDmnEngineConfiguration().buildEngine();
        readRecursion(baseDir, dmnEngine);
    }

    private void readRecursion(File dir, DmnEngine dmnEngine) throws IOException {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                readRecursion(file, dmnEngine);
            } else if (file.getName().endsWith(".dmn")) {
                FileInputStream stream = new FileInputStream(file);
                DmnDecision decision = dmnEngine.parseDecision("final", stream);
                rules.put(file.getName().substring(0,file.getName().indexOf('.')), decision);
            }
        }
    }

    public double performRules(CreditRequest creditRequest, VariableMap variables) {
        String key= creditRequest.getProgramCode().replaceAll("\\.", "_");
        DmnDecision decision = rules.get(key);
        LOG.info("Run rule " + key);
        DmnDecisionResult result = dmnEngine.evaluateDecision(decision, variables);
        double rate = result.getSingleEntry();
        LOG.info("Rate is: " + rate);
        return rate;
    }
}
