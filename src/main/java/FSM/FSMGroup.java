package FSM;

import util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FSMGroup {
    List<FSM> fsms = new ArrayList<FSM>();

    public List<FSM> getFsms() {
        return fsms;
    }

    public void setFsms(List<FSM> fsms) {
        this.fsms = fsms;
    }

    public FSMGroup() {
    }

    /*public Pair<Integer, Boolean> max(String input, int skip){
        List<Pair<Integer, Boolean>> results = new ArrayList<>();
        Pair<Integer, Boolean> pairOfResult = Pair.of(0, false);
        for (var fsm: fsms){
            results.add(fsm.max(input, skip));
        }
        for (var result : results){
            if (result.getKey() > pairOfResult.getKey()){
                pairOfResult = Pair.of(result.getKey(), result.getValue());
            }
        }
        return pairOfResult;
    }*/
    public Pair<Integer, Boolean> max(String input, int skip){
        Pair<Integer, Boolean> result = Pair.of(0, false);
        Map<FSM, Pair<Integer, Boolean>> results = new HashMap<FSM, Pair<Integer, Boolean>>();
        Map<FSM, Boolean> success = new HashMap<FSM, Boolean>();
        for (var fsm: fsms){
            results.put(fsm, Pair.of(0, false));
            success.put(fsm, true);
        }
        for(int i=skip; i<input.length(); i++) {
            for (var fsm : fsms) {
                if (success.get(fsm)){
                    boolean isCorrect = fsm.feedFSM(input.charAt(i));
                    if (isCorrect){
                        results.put(fsm, Pair.of(i+1-skip, true));
                    }
                    else {
                        success.put(fsm, false);
                    }
                }
            }
        }
        for(var fsm: fsms){
            if (fsm.getEndStates().contains(fsm.currentState)){
                fsm.setCurrentState("q0");
            }
            else {
                fsm.setCurrentState("q0");
                results.put(fsm, Pair.of(0, false));
            }
        }
        int priority = -1;
        for (var fsm : results.keySet()){
            //System.out.println("" + fsm.getClassName() + " " + results.get(fsm) + " fsmPriority = " + fsm.getPriority());
            if (results.get(fsm) != null && fsm.getPriority() > priority && results.get(fsm).getKey() != 0){
                //System.out.println(fsm);
                priority = fsm.getPriority();
                result = Pair.of(results.get(fsm).getKey(), results.get(fsm).getKey()!=0);
                //System.out.println(fsm);
            }
            else if (results.get(fsm) != null && fsm.getPriority() == priority && results.get(fsm).getKey() > result.getKey()) {
                priority = fsm.getPriority();
                result = Pair.of(results.get(fsm).getKey(), results.get(fsm).getKey()!=0);
            }
            /*if (results.get(fsm).getKey() != 0){
                System.out.println(fsm);
            }*/
        }


        return result;
    }

    public Pair<FSM, Pair<Integer, Boolean>> defineTokens(String input, int skip){
        Pair<FSM, Pair<Integer, Boolean>> fsmPair = null;
        Pair<Integer, Boolean> result = Pair.of(0, false);
        Map<FSM, Pair<Integer, Boolean>> results = new HashMap<FSM, Pair<Integer, Boolean>>();
        Map<FSM, Boolean> success = new HashMap<FSM, Boolean>();
        for (var fsm: fsms){
            results.put(fsm, Pair.of(0, false));
            success.put(fsm, true);
        }
        for(int i=skip; i<input.length(); i++) {
            for (var fsm : fsms) {
                if (success.get(fsm)){
                    boolean isCorrect = fsm.feedFSM(input.charAt(i));
                    if (isCorrect){
                        results.put(fsm, Pair.of(i+1-skip, true));
                    }
                    else {
                        success.put(fsm, false);
                    }
                }
            }
        }
        for(var fsm: fsms){
            if (fsm.getEndStates().contains(fsm.currentState)){
                fsm.setCurrentState("q0");
            }
            else {
                fsm.setCurrentState("q0");
                results.put(fsm, Pair.of(0, false));
            }
        }
        int priority = -1;
        for (var fsm : results.keySet()){
            //System.out.println("" + fsm.getClassName() + " " + results.get(fsm) + " fsmPriority = " + fsm.getPriority());
            if (results.get(fsm) != null && fsm.getPriority() > priority && results.get(fsm).getKey() != 0){
                priority = fsm.getPriority();
                result = Pair.of(results.get(fsm).getKey(), results.get(fsm).getKey()!=0);
                fsmPair = Pair.of(fsm, result);
            }
            else if (results.get(fsm) != null && fsm.getPriority() == priority && results.get(fsm).getKey() >= result.getKey()) {
                priority = fsm.getPriority();
                result = Pair.of(results.get(fsm).getKey(), results.get(fsm).getKey()!=0);
                fsmPair = Pair.of(fsm, result);
            }
        }
        return fsmPair;

    }
}
