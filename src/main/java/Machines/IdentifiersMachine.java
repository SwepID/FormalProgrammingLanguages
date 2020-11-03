package Machines;

import util.Pair;

import java.util.Set;

public class IdentifiersMachine {
    Set<String> identifiers;

    public IdentifiersMachine(Set<String> identifiers) {
        this.identifiers = identifiers;
    }

    public Set<String> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(Set<String> identifiers) {
        this.identifiers = identifiers;
    }
    public Pair<Boolean, Integer>max(String s){
        Pair<Boolean, Integer> pair = Pair.of(false, 0);
        pair = new Pair<>(false, 0);
        for (int i = 0; i < s.length(); i++) {
            if (identifiers.contains(Character.toString(s.charAt(i)))) {
                pair = pair.of(true, i + 1);
            } else {
                break;
            }
        }
        return pair;
    }
}
