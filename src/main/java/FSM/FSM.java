package FSM;

import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import util.Pair;

import java.util.*;

public class FSM {
    Set<String> startStates;
    Set<String> endStates;
    String currentState = "q0";
    Set<String> allStates = new HashSet<>();
    Map<String, String> inputs = new HashMap<>();
    Map<String, Map<String, List<String>>> matrix = new HashMap<>();
    String className = "";
    int priority;

    public Map<String, Map<String, List<String>>> getMatrix() {
        return matrix;
    }

    public void setMatrix(Map<String, Map<String, List<String>>> matrix) {
        this.matrix = matrix;
    }

    public Map<String, String> getInputs() {
        return inputs;
    }

    public void setInputs(Map<String, String> inputs) {
        this.inputs = inputs;
    }

    public Set<String> getStartStates() {
        return startStates;
    }

    public void setStartStates(Set<String> startStates) {
        this.startStates = startStates;
    }

    public Set<String> getEndStates() {
        return endStates;
    }

    public void setEndStates(Set<String> endStates) {
        this.endStates = endStates;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public Set<String> getAllStates() {
        return allStates;
    }

    public void setAllStates(Set<String> allStates) {
        this.allStates = allStates;
    }

    public FSM() {
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return  "class = " + className + "\npriority = " + priority + "\nstart: " + startStates + "\nfinish: " + endStates + "\ninputs: " + inputs + "\nmatrix: " + matrix + "\n";
    }
    public Pair<Integer, Boolean> max(String input, int skip) {
        Pair<Integer, Boolean> pair = Pair.of(0, false);
        for(int i = skip; i < input.length(); i++){
            //System.out.println(input.charAt(i));
            boolean isCorrect = feedFSM(input.charAt(i));
            if (isCorrect){
                pair = Pair.of(i + 1 - skip, true);
            }
            else {
                break;
            }
        }
        if (endStates.contains(currentState)){
            currentState = "q0";
            //System.out.println(pair);
            return pair;
        }
        else {
            currentState = "q0";
            //System.out.println(pair);
            return Pair.of(0,false);
        }


    }
    public Boolean feedFSM(Character character){
        //System.out.println(currentState);
        String inputType = null;
        String state = null;
        if (!inputs.isEmpty()){
            inputType = getInputType(character);
        }
        if (matrix.get(currentState) != null && matrix.get(currentState).get("" + character) != null){
            currentState = matrix.get(currentState).get("" + character).get(0);
        }
        else if (inputType!= null && matrix.get(currentState) != null && matrix.get(currentState).get(inputType)!= null){
            //System.out.println(matrix.get(currentState));
            currentState = matrix.get(currentState).get(inputType).get(0);
            //System.out.println(currentState);
        }
        else {
            return false;
        }
        //System.out.println(currentState);
        return true;

    }

    public String getInputType(Character character){
        //System.out.println("'" + character.toString().replace("\\", "\\\\") + "'");
        String outType = null;
        for (var key : inputs.keySet()){
            JexlEngine expressionFactory = new JexlEngine();
            JexlContext context = new MapContext();
            String characterString = "'" + character.toString().replace("\\", "\\\\") + "'";
            org.apache.commons.jexl2.Expression expression = expressionFactory.createExpression(inputs.get(key)
                    .replace("'x'", characterString).replaceAll("\"", ""));
            boolean result=(Boolean)expression.evaluate(context);
            if (result)
                outType = key;
        }
        //System.out.println(outType);
        return outType;
    }
}
