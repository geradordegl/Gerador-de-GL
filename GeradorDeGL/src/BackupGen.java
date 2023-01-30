import generalization.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class BackupGen {
    
    ArrayList<Map<String,Integer>> arrayLiterais;
    ArrayList<Map<String,Integer>> arrayLemas;
    ArrayList<Map<String,Integer>> arrayCodigos;
    ArrayList<Map<String,Integer>> arrayFlexoes;
    ArrayList<Map<String,Integer>> solution = new ArrayList<>();
    
   
    //usado como parametro para saber se determinada posição chegou 
    //a generalização maxima
    private int tamanho;
    
    public boolean isSameLength(ArrayList<Episode> episodes){        
        int count=0;
        for(int i=0; i<episodes.size();i++){
            if(i==0){
                count = episodes.get(i).getTamananho();
            }else if(episodes.get(i).getTamananho()!=count){
                return false;
            }            
        }
            
        return true;
    }
    
   public void frequencyByProperties(Map<String, Integer> map, String property){
       if(map.containsKey(property)){
                    int frequency = map.get(property);
                    frequency++;
                    map.put(property, frequency);
                }else{
                    map.put(property, 1);
                }
   }
    
  
    public void gen(ArrayList<Episode> episodes){
        tamanho = episodes.size();
        //iniciando um map por propriedade para cada posição
        for(int j = 0; j < episodes.get(0).getTamananho(); j++){           
           Map<String,Integer> mapLiteral = new HashMap<String,Integer>();
           Map<String,Integer> mapLema = new HashMap<String,Integer>();
           Map<String,Integer> mapCodigos = new HashMap<String,Integer>();
           Map<String,Integer> mapFlexoes = new HashMap<String,Integer>();
           
            for(int i=0; i < episodes.size(); i++){               
                Event event = episodes.get(i).getSequenceEvents().get(j);
                if (event.getEventType().contains("|"))
                    System.out.println("varios eventos");
                    
                if(mapLiteral.containsKey(event.getLiteral())){
                    int frequency = mapLiteral.get(event.getLiteral());
                    frequency++;
                    mapLiteral.put(event.getLiteral(), frequency);
                }else{
                    mapLiteral.put(event.getLiteral(), 1);
                }
                if(mapLema.containsKey(event.getLemma())){
                    int frequency = mapLema.get(event.getLemma());
                    frequency++;
                    mapLema.put(event.getLemma(), frequency);
                }else{
                    mapLema.put(event.getLemma(), 1);
                }
                if(mapCodigos.containsKey(event.getGrammaticalCodes())){
                    int frequency = mapCodigos.get(event.getGrammaticalCodes());
                    frequency++;
                    mapCodigos.put(event.getGrammaticalCodes(), frequency);
                }else{
                    mapCodigos.put(event.getGrammaticalCodes(), 1);
                }
                if(mapFlexoes.containsKey(event.getInflections())){
                    int frequency = mapFlexoes.get(event.getInflections());
                    frequency++;
                    mapFlexoes.put(event.getInflections(), frequency);
                }else{
                     mapFlexoes.put(event.getInflections(), 1);
                }                    
            }
           
           int literalMaxFrequency = Collections.max(mapLiteral.values());
           int lemaMaxFrequency = Collections.max(mapLema.values());
           int codeMaxFrequency = Collections.max(mapCodigos.values());
           int flectionsMaxFrequency = Collections.max(mapLiteral.values());
           
           if((literalMaxFrequency>lemaMaxFrequency && literalMaxFrequency>codeMaxFrequency)
                   || literalMaxFrequency== tamanho){
               solution.add(mapLiteral);
           }else if(lemaMaxFrequency>codeMaxFrequency || lemaMaxFrequency==tamanho){
               solution.add(mapLema);
           }else {
               solution.add(mapCodigos);
           }
        
        }
        for(Map<String,Integer> map: solution){
            map.forEach((chave,valor)->{
                System.out.println("chave: "+chave+" valor: "+valor);
            });
        }
    }
}
