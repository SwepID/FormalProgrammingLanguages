import FSM.FSM;
import FSM.FSMGroup;
import FSM.JSONDeserializer;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import util.Pair;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class Program {
    public static String readFile(String path) throws IOException {
        return new String(Files.readAllBytes(Path.of(path))).replace('\r', ' ')
                .replace('\n', ' ').replaceAll("( )+", " ")
                .replaceAll(";", " ;").replaceAll("\\(", "( ")
                .replaceAll("\\)", " )");
    }
    public static void main(String[] args) throws IOException {
        List<FSM> fsmList = new ArrayList<FSM>();
        FSMGroup fsmGroup = new FSMGroup();
        FileReader config = new FileReader("src/main/resources/config/config.json");
        JsonReader jsonReader = new JsonReader(config);
        JsonObject jsonObject = new JsonParser().parse(jsonReader).getAsJsonObject();
        //the first task
        for (var key : jsonObject.keySet()){
            String jsonPath = jsonObject.get(key).getAsJsonObject().get("path").toString().replaceAll("\"", "");
            int priority = Integer.parseInt(jsonObject.get(key).getAsJsonObject().get("priority").toString());
            //System.out.println(jsonPath);
            if (jsonPath.equals("src/main/resources/lexer/integer.json") || jsonPath.equals("src/main/resources/lexer/real.json")){
                FileReader in = new FileReader(jsonPath);
                jsonReader = new JsonReader(in);
                JsonArray jsonArray = new JsonParser().parse(jsonReader).getAsJsonArray();
                JSONDeserializer jsonDeserializer = new JSONDeserializer();
                jsonDeserializer.deserialize(jsonArray, key, priority).forEach(fsm -> fsmList.add(fsm));
            }
        }
        //fsmList.forEach(System.out::println);
        fsmGroup.setFsms(fsmList);
        System.out.println(fsmGroup.max(".", 0)); // 0, false
        System.out.println(fsmGroup.max(".1", 0)); // 2, true
        System.out.println(fsmGroup.max("123",0)); // 3, true
        System.out.println(fsmGroup.max("a123", 0)); // 0, false
        System.out.println(fsmGroup.max("123.",0)); // 4, true
        System.out.println(fsmGroup.max("12.3.",0)); // 4, true
        System.out.println(fsmGroup.max("123.1",0)); // 5, true
        System.out.println(fsmGroup.max("123.123",0)); // 7, true
        System.out.println(fsmGroup.max("+123.123",0)); // 8, true
        System.out.println(fsmGroup.max("-123.123",0)); // 8, true
        System.out.println(fsmGroup.max("a123.123",0)); // 0, false
        System.out.println(fsmGroup.max("a123.123", 1)); // 7, true
        System.out.println(fsmGroup.max("a123.123", 4)); // 4, true
        System.out.println(fsmGroup.max("a123.123", 5)); // 3, true

        System.out.println();
        System.out.println();
        System.out.println();


        //the second task
        fsmList.clear();

        for (var key : jsonObject.keySet()){
            String jsonPath = jsonObject.get(key).getAsJsonObject().get("path").toString().replaceAll("\"", "");
            int priority = Integer.parseInt(jsonObject.get(key).getAsJsonObject().get("priority").toString());
            FileReader in = new FileReader(jsonPath);
            jsonReader = new JsonReader(in);
            JsonArray jsonArray = new JsonParser().parse(jsonReader).getAsJsonArray();
            JSONDeserializer jsonDeserializer = new JSONDeserializer();
            jsonDeserializer.deserialize(jsonArray, key, priority).forEach(fsm -> fsmList.add(fsm));
        }
        fsmGroup.setFsms(fsmList);
        //fsmList.forEach(System.out::println);
        System.out.println(readFile("src/main/resources/program.txt"));
        String[] unrecognizedTokens = readFile("src/main/resources/program.txt").split(" ");
        for (String unrecognizedToken : unrecognizedTokens) {
            Pair<FSM, Pair<Integer, Boolean>> fsmPair = fsmGroup.defineTokens(unrecognizedToken, 0);
            if (fsmPair!= null){
                System.out.println("" + unrecognizedToken + " --> " + fsmPair.getKey().getClassName() + " priority = " + fsmPair.getKey().getPriority());
            }
        }
        //System.out.println(fsmGroup.defineTokens("15", 0));
    }
}
