import Entity.Token;
import Logic.Checker;
import Machines.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws FileNotFoundException,
            IOException, ParseException {
        String jsonFilename = "src/main/resources/javaParts.json";
        FileReader reader = new FileReader(jsonFilename);
        String programFileName = "src/main/resources/program.txt";

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


        //the first task

        NumbersMachine numbersMachine = new NumbersMachine(digits);
        System.out.println(numbersMachine.max("12f3", 0));
        IdentifiersMachine identifiersMachine = new IdentifiersMachine(alphabet);
        System.out.println(identifiersMachine.max("main(String[]args){i", 0));
        TypesMachine typesMachine = new TypesMachine(types);
        System.out.println(typesMachine.max("Byte", 0));
        KeyWordsMachine keyWordsMachine = new KeyWordsMachine(keywords);
        System.out.println(keyWordsMachine.max("abstract", 0));
        SignsMachine signsMachine = new SignsMachine(numberSigns);
        System.out.println(signsMachine.max("+", 0));
        SignsMachine punctuationSignsMachine = new SignsMachine(punctuationSigns);
        System.out.println(punctuationSignsMachine.max("|", 0));

        //the second task


        Checker checker = new Checker(alphabet, keywords, digits, punctuationSigns, numberSigns, types);
        String program = checker.ReadFile(programFileName);
        List<Token> tokens = checker.CheckProgram(program);
        tokens.forEach(System.out::println);
    }
}