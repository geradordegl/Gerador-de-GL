package generalization;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InflectionGeneralizer {
    
    
    
    public InflectionGeneralizer() {
        
    }
    
    public Map<String,Integer> generalize(Map<String,Integer> mapFlexoes, String[] splited) {
        
        Set<String> inflections = new HashSet<>(Arrays.asList(splited));
        Set<String> inflectionsSplitted = new HashSet<>();
        
        for(String s : inflections) {
            inflectionsSplitted.addAll(Arrays.asList(s.split("")));
        }
        
        for(String s : inflectionsSplitted) {
            calcFrequency(mapFlexoes, s);
        }
        
        return mapFlexoes;
        
        
    }
    
    public Map<String,Integer> generalize(Map<String,Integer> mapFlexoes, String property) {
        
        Set<String> inflectionsSplitted = new HashSet<>();
        
        inflectionsSplitted.addAll(Arrays.asList(property.split("")));
        
        for(String s : inflectionsSplitted) {
            calcFrequency(mapFlexoes, s);
        }
        
        return mapFlexoes;
    }
    
    private void calcFrequency(Map<String,Integer> map, String property) {
        
        if(map.containsKey(property)) {
            int frequency = map.get(property);
            frequency++;
            map.put(property, frequency);
        } else {
            map.put(property, 1);
        }

    }
    
    
    private void printMap(Map<String,Integer> map) {
        
        map.forEach((chave, valor) -> {
            System.out.println("Chave: " + chave + ", Valor: " + valor);
        });
    }
    
    private void printSet(Set<String> set) {
        
        for(String s : set) {
            System.out.println(s);
        }
    }
    
}
