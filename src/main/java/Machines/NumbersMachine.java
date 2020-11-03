package Machines;

import util.Pair;

import java.util.Set;

public class NumbersMachine {
    Set<String> digits;

    public Set<String> getDigits() {
        return digits;
    }

    public void setDigits(Set<String> digits) {
        this.digits = digits;
    }

    public NumbersMachine(Set<String> digits){
        this.digits = digits;
    }

    public Pair<Boolean, Integer> max(String s, int skip){
        Pair<Boolean, Integer> pair = Pair.of(false,0);
        for (int i = skip; i < s.length(); i++) {
            if (digits.contains(Character.toString(s.charAt(i)))) {
                pair = pair.of(true, s.substring(skip, i + 1).length());
            } else {
                break;
            }
        }
        return pair;
    }
}
