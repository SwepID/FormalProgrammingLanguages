import Machines.IdentifiersMachine;
import Machines.NumbersMachine;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    /*public static Pair<Boolean, Integer> max(String input, int skip) {
        Pair<Boolean, Integer> pair = new Pair<>(false, 0);

        for (int i = skip; i < input.length(); i++) {
            if (Character.isDigit(input.charAt(i))) {
                pair = pair.of(true, i + 1);
            } else {
                break;
            }
        }
        return pair;
    }*/

    public static void main(String[] args) throws FileNotFoundException,
            IOException, ParseException {
        String jsonFilename = "src/main/resources/javaParts.json";
        FileReader reader = new FileReader(jsonFilename);

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(reader);

        JSONArray array = (JSONArray) jsonObject.get("digits");
        Set<String> digits = (Set<String>) array.stream().collect(Collectors.toSet());
        array = (JSONArray) jsonObject.get("types");
        Set<String> types = (Set<String>) array.stream().collect(Collectors.toSet());
        array = (JSONArray) jsonObject.get("alphabet");
        Set<String> alphabet = (Set<String>) array.stream().collect(Collectors.toSet());
        array = (JSONArray) jsonObject.get("numberSigns");
        Set<String> numberSigns = (Set<String>) array.stream().collect(Collectors.toSet());
        array = (JSONArray) jsonObject.get("punctuationSigns");
        Set<String> punctuationSigns = (Set<String>) array.stream().collect(Collectors.toSet());
        array = (JSONArray) jsonObject.get("keywords");
        Set<String> keywords = (Set<String>) array.stream().collect(Collectors.toSet());

        NumbersMachine numbersMachine = new NumbersMachine(digits);
        System.out.println(numbersMachine.max("12f3", 0));
        IdentifiersMachine identifiersMachine = new IdentifiersMachine(alphabet);
        System.out.println(identifiersMachine.max("abc_"));

    }

}