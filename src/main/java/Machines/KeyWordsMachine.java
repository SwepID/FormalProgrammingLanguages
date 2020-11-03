package Machines;

import util.Pair;

import java.util.Set;

public class KeyWordsMachine {
    Set<String> keywords;

    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords = keywords;
    }

    public KeyWordsMachine(Set<String> keywords){
        this.keywords = keywords;
    }

    public Pair<Boolean, Integer> max(String s, int skip){
        Pair<Boolean, Integer> pair = Pair.of(false,0);
        for (int i = skip; i < s.length(); i++){
            if (keywords.contains(s.substring(skip, i + 1))){
                pair = Pair.of(true, s.substring(skip, i + 1).length());
            }
        }
        return pair;
    }
}
