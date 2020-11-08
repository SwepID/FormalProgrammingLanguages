package FSM;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

public class Program {
    public static void main(String[] args) throws IOException, ParseException {
        FileReader in = new FileReader("src/main/java/FSM/integer.json");
        JsonReader jsonReader = new JsonReader(in);
        JsonArray jsonArray = new JsonParser().parse(jsonReader).getAsJsonArray();
        JSONDeserializer jsonDeserializer = new JSONDeserializer();
        jsonDeserializer.deserialize(jsonArray);

    }
}
