package FSM;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.*;

public class JSONDeserializer{
    public List<FSM> deserialize(JsonArray jsonArray, String fsmName, int priority) throws JsonProcessingException {
        List<FSM> fsms = new ArrayList<FSM>();
        for(var jsonElem : jsonArray){
            JsonObject json = jsonElem.getAsJsonObject();
            //System.out.println(json);
            Set<String> start = new HashSet<>();
            json.getAsJsonArray("start").forEach(elem -> start.add(elem.toString().replaceAll("\"", "")));
            Set<String> finish = new HashSet<>();
            json.getAsJsonArray("finish").forEach(elem -> finish.add(elem.toString().replaceAll("\"", "")));
            Map<String, String> inputs = new HashMap<>();
            if (json.get("inputs") != null) {
                for(var input : json.get("inputs").getAsJsonObject().keySet()) {
                    inputs.put(input, json.get("inputs").getAsJsonObject().get(input).toString());
                }
            }
            Map<String, Map<String, List<String>>> jsonMatrix = new HashMap<>();
            for (var q : json.get("matrix").getAsJsonObject().keySet()) {
                Map<String, List<String>> map = new HashMap<>();
                for(var input : json.get("matrix").getAsJsonObject().get(q).getAsJsonObject().keySet()){
                    List<String> list = new ArrayList<>();
                    json.get("matrix").getAsJsonObject().get(q).getAsJsonObject().get(input).getAsJsonArray()
                            .forEach(elem-> list.add(elem.toString().replaceAll("\"","")));
                    map.put(input, list);
                }
                jsonMatrix.put(q, map);
            }
            FSM fsm = new FSM();
            fsm.setClassName(fsmName);
            fsm.setPriority(priority);
            fsm.setStartStates(start);
            fsm.setEndStates(finish);
            fsm.setAllStates(jsonMatrix.keySet());
            fsm.setInputs(inputs);
            fsm.setMatrix(jsonMatrix);
            fsms.add(fsm);
        }

        return fsms;
    }
}
