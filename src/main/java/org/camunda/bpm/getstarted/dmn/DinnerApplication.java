package org.camunda.bpm.getstarted.dmn;

import org.camunda.bpm.application.PostDeploy;
import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.application.impl.ServletProcessApplication;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;

import java.util.List;

@ProcessApplication("Dinner App DMN")
public class DinnerApplication extends ServletProcessApplication {
    @PostDeploy
    public void evaluateDecisionTable(ProcessEngine processEngine){
        DecisionService decisionService = processEngine.getDecisionService();
        VariableMap variableMap = Variables.createVariables()
                .putValue("season","Spring")
                .putValue("guestCount", 10)
                .putValue("guestsWithChildren", true);
        DmnDecisionTableResult dmnDecisionRuleResults = decisionService.evaluateDecisionTableByKey("dish",variableMap);
        String desriedDish = dmnDecisionRuleResults.getSingleEntry();
        System.out.println("Desired dish "+ desriedDish);

        DmnDecisionTableResult dmnBeveragesResults = decisionService.evaluateDecisionTableByKey("beverages",variableMap);
        List<Object> beverages = dmnBeveragesResults.collectEntries("beverages");
        System.out.println("Desired beverages: " + beverages);
    }
}
