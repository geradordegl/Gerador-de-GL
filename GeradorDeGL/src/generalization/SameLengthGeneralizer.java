package generalization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class SameLengthGeneralizer {
    
    private ArrayList<Map<String,Integer>> solution;

    
    public SameLengthGeneralizer() {
        
        solution = new ArrayList<>();
    }
    
    
    public boolean isSameLength(ArrayList<Episode> episodes) {        
        int count = 0;
        for(int i = 0; i < episodes.size(); i++) {
            if(i == 0) {
                count = episodes.get(i).getTamananho();
            } else if(episodes.get(i).getTamananho() != count) {
                return false;
            }            
        }
            
        return true;
    }
    
    public ArrayList<Map<String,Integer>> generalize(ArrayList<Episode> episodes) {
        
        InflectionGeneralizer flexgen = new InflectionGeneralizer();
        
        for (int j = 0; j < episodes.get(0).getTamananho(); j++) {
            
            Map<String,Integer> mapLiterais = new HashMap<>();
            Map<String,Integer> mapLemas = new HashMap<>();
            Map<String,Integer> mapCodigos = new HashMap<>();
            Map<String,Integer> mapFlexoes = new HashMap<>();
            
            for (int i = 0; i < episodes.size(); i++) {
                
                Event event = episodes.get(i).getSequenceEvents().get(j);
                
                calcFrequency(mapLiterais, event.getLiteral());
                
                if(event.getEventType().contains("|")) {
                    
                    calcFrequency(mapLemas, event.getLemma().split("\\|"));
                    calcFrequency(mapCodigos, event.getGrammaticalCodes().split("\\|"));
                    mapFlexoes = flexgen.generalize(mapFlexoes, event.getInflections().split("\\|"));
                    
                } else {
                    
                    calcFrequency(mapLemas, "<" + event.getLemma() + ">");                    
                    calcFrequency(mapCodigos, "<" + event.getGrammaticalCodes() + ">");
                    mapFlexoes = flexgen.generalize(mapFlexoes, event.getInflections());
                    
                }                
            }
            
            createSolution(mapLiterais, mapLemas, mapCodigos, mapFlexoes);
            
        }
        
        return solution; 
        
    }
    
    private void calcFrequency(Map<String,Integer> map, String[] splited) {
        
        Set<String> propertiesSameEpisode = new HashSet<>(Arrays.asList(splited));
        for(String s : propertiesSameEpisode) {
            s = s.replaceAll(":.*", "");
            calcFrequency(map, "<" + s + ">");
        }
        
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
    
    private Map<String,Integer> createFlex(Map<String,Integer> mapCodigos, Map<String,Integer> mapFlexoes, int codeFrequency, int inflectionFrequency) {
        
        Map<String,Integer> mapCodigosFlexoes = new HashMap<>();
        
        mapCodigos.forEach((chaveCodigo, valorCodigo)-> {
            if(valorCodigo == codeFrequency) {
                StringBuilder codigoFlexao = new StringBuilder();
                String codigo = chaveCodigo.replaceAll("<|>", "");
                codigoFlexao.append("<").append(codigo).append(":");
                mapFlexoes.forEach((chaveFlexao, valorFlexoes)-> {
                    if(valorFlexoes == inflectionFrequency) {
                        codigoFlexao.append(chaveFlexao);
                    }
                });
                codigoFlexao.append(">");
                mapCodigosFlexoes.put(codigoFlexao.toString(), inflectionFrequency);
            }
        });
        
        return mapCodigosFlexoes;
    }
    
    private void createSolution(Map<String,Integer> mapLiterais, Map<String,Integer> mapLemas, Map<String,Integer> mapCodigos, Map<String,Integer> mapFlexoes) {
        
        int literalMaxFrequency = Collections.max(mapLiterais.values());
        int lemaMaxFrequency = Collections.max(mapLemas.values());
        int codeMaxFrequency = Collections.max(mapCodigos.values());
        int inflectionMaxFrequency = Collections.max(mapFlexoes.values());
        
        if((literalMaxFrequency >= lemaMaxFrequency && literalMaxFrequency >= codeMaxFrequency && literalMaxFrequency >= inflectionMaxFrequency)) {
            solution.add(mapLiterais);
        } else {
            
            if(lemaMaxFrequency >= codeMaxFrequency && lemaMaxFrequency >= inflectionMaxFrequency) {
                solution.add(mapLemas);
            } else if(codeMaxFrequency > inflectionMaxFrequency) {
                solution.add(mapCodigos);
            } else {
                solution.add(createFlex(mapCodigos, mapFlexoes, codeMaxFrequency, inflectionMaxFrequency));
            }
        }
        
    }
    
    private void filter(ArrayList<Map<String,Integer>> solution) {
        
        for(Map<String,Integer> map: solution) {
            ArrayList<String> chaves = new ArrayList<>();
            int maxValue = Collections.max(map.values());
            map.forEach((chave,valor)-> {
                if(valor < maxValue)
                    chaves.add(chave);
            });
            for(String s : chaves)
                map.remove(s);
        }
    }
    
    private void printMap(Map<String,Integer> map) {
        
        map.forEach((chave, valor) -> {
            System.out.println("Chave: " + chave + ", Valor: " + valor);
        });
    }
    
}
