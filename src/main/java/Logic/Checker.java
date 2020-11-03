package Logic;

import Entity.Token;
import Machines.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Checker {

    Set<String> identifiers;
    Set<String> keywords;
    Set<String> numbers;
    Set<String> punctuationSigns;
    Set<String> numberSigns;
    Set<String> types;

    public Set<String> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(Set<String> identifiers) {
        this.identifiers = identifiers;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords = keywords;
    }

    public Set<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(Set<String> numbers) {
        this.numbers = numbers;
    }

    public Set<String> getPunctuationSigns() {
        return punctuationSigns;
    }

    public void setPunctuationSigns(Set<String> punctuationSigns) {
        this.punctuationSigns = punctuationSigns;
    }

    public Set<String> getNumberSigns() {
        return numberSigns;
    }

    public void setNumberSigns(Set<String> numberSigns) {
        this.numberSigns = numberSigns;
    }

    public Set<String> getTypes() {
        return types;
    }

    public void setTypes(Set<String> types) {
        this.types = types;
    }

    public Checker(Set<String> identifiers, Set<String> keywords, Set<String> numbers,
                   Set<String> punctuationSigns, Set<String> numberSigns, Set<String> types){
        this.identifiers = identifiers;
        this.keywords = keywords;
        this.numbers = numbers;
        this.punctuationSigns = punctuationSigns;
        this.numberSigns = numberSigns;
        this.types = types;
    }




    public String ReadFile(String fileName) throws IOException {
        File file = new File(fileName);
        StringBuffer program = new StringBuffer("");
        FileInputStream fis = new FileInputStream(file);
        if (fis!= null){
            int i=-1;
            while((i=fis.read())!=-1){
                program.append((char)i);
            }
            fis.close();
        }
        return program.toString();
    }

    public List<Token> CheckProgram(String program){
        List<Token> tokens = new ArrayList<>();
        NumbersMachine numbersMachine = new NumbersMachine(numbers);
        IdentifiersMachine identifiersMachine = new IdentifiersMachine(identifiers);
        TypesMachine typesMachine = new TypesMachine(types);
        KeyWordsMachine keyWordsMachine = new KeyWordsMachine(keywords);
        SignsMachine signsMachine = new SignsMachine(numberSigns);
        SignsMachine punctuationSignsMachine = new SignsMachine(punctuationSigns);

        int skip = 0;
        Boolean inProcess = true;
        Token token = null;
        program = program.replaceAll(" ", "");
        while (inProcess == true){
            if (keyWordsMachine.max(program, skip).getKey() == true){
                token = new Token("keyword", program.substring(skip, skip + keyWordsMachine.max(program, skip).getValue()));
                tokens.add(token);
                skip+= token.getValue().length();
            }
            else if (typesMachine.max(program, skip).getKey() == true){
                token = new Token("type", program.substring(skip, skip + typesMachine.max(program, skip).getValue()));
                tokens.add(token);
                skip+= token.getValue().length();
            }
            else if(punctuationSignsMachine.max(program, skip).getKey() == true){
                token = new Token("punctuationSign", program.substring(skip, skip + punctuationSignsMachine.max(program, skip).getValue()));
                tokens.add(token);
                skip+= token.getValue().length();
            }
            else if(signsMachine.max(program,skip).getKey() == true){
                token = new Token("numberSign", program.substring(skip, skip + signsMachine.max(program, skip).getValue()));
                tokens.add(token);
                skip+= token.getValue().length();
            }
            else if(numbersMachine.max(program, skip).getKey() == true){
                token = new Token("number", program.substring(skip, skip + numbersMachine.max(program, skip).getValue()));
                tokens.add(token);
                skip+= token.getValue().length();
            }
            else if(identifiersMachine.max(program, skip).getKey() == true){
                token = new Token("identifier", program.substring(skip, skip + identifiersMachine.max(program, skip).getValue()));
                tokens.add(token);
                skip+= token.getValue().length();
            }
            else {
                inProcess = false;
            }
        }
        return tokens;
    }
}
