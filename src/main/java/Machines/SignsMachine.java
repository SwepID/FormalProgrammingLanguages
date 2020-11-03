package Machines;

import util.Pair;

import java.util.Set;

public class SignsMachine {
    Set<String> signs;

    public Set<String> getsigns() {
        return signs;
    }

    public void setsigns(Set<String> signs) {
        this.signs = signs;
    }

    public SignsMachine(Set<String> signs){
        this.signs = signs;
    }

    public Pair<Boolean, Integer> max(String s, int skip){
        Pair<Boolean, Integer> pair = Pair.of(false,0);
        for (int i = skip; i < s.length(); i++) {
            if (signs.contains(Character.toString(s.charAt(i)))) {
                pair = pair.of(true, 1);
                break;
            } else {
                break;
            }
        }
        return pair;
    }
}
