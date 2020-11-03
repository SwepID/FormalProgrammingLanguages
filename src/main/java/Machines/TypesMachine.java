package Machines;

import util.Pair;

import java.util.Set;

public class TypesMachine {
    Set<String> types;

    public Set<String> getTypes() {
        return types;
    }

    public void setTypes(Set<String> types) {
        this.types = types;
    }

    public TypesMachine(Set<String> types){
        this.types = types;
    }

    public Pair<Boolean, Integer> max(String s, int skip){
        Pair<Boolean, Integer> pair = Pair.of(false,0);
        for (int i = skip; i < s.length(); i++){
            if (types.contains(s.substring(skip, i + 1))){
                pair = Pair.of(true, s.substring(skip, i + 1).length());
            }
        }
        return pair;
    }
}
