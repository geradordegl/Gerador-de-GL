package generalization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class DiffLengthGeneralizer {
    
    
    private ArrayList<Map<String,Integer>> solution;
    private Map<String,Integer> mapaCodigosGenericos;
    
    
    public DiffLengthGeneralizer() {
        
        solution = new ArrayList<>();
        mapaCodigosGenericos = new HashMap<>();
        
    }
    
    
    public ArrayList<Episode> generalize(ArrayList<Episode> episodios) {
        
        ArrayList<ArrayList<String>> episodiosEmCodigos = new ArrayList<>();
        
        for(Episode episodio : episodios) {
            episodiosEmCodigos.add(transformarEpisodiosEmCodigos(episodio));
        }
        
        ObterFrequenciaEmEpisodios(episodiosEmCodigos);
        
        return GerarEpisodiosMesmoTamanho(episodiosEmCodigos, episodios);
        
    }
    
    
    private ArrayList<Episode> GerarEpisodiosMesmoTamanho(ArrayList<ArrayList<String>> episodiosEmCodigos, ArrayList<Episode> episodios) {
        
        ArrayList<Episode> episodiosMesmoTamanho = new ArrayList<>();
        
        String[] sequenciaGenerica = ObterSequenciaGenerica();
        
        int pos = 0;
        boolean sequencia;
        boolean encontrado;
        
        for(int i = 0; i < episodiosEmCodigos.size(); i++) {
            
            sequencia = false;
            encontrado = false;
            String codigo = sequenciaGenerica[0];
            
            for(int j = 0, k = 0; j < episodiosEmCodigos.get(i).size() && !encontrado; j++) {
                
                if(episodiosEmCodigos.get(i).get(j).equals(codigo)) {
                    
                    if(!sequencia) {
                        sequencia = true;
                        pos = j;
                    }
                    
                    k++;
                    
                    if(k < sequenciaGenerica.length) {
                        codigo = sequenciaGenerica[k];
                    } else {
                        encontrado = true;                            
                    }
                    
                } else if(sequencia) {
                    sequencia = false;
                    k = 0;
                    codigo = sequenciaGenerica[0];
                    j--;
                }
            }
            
            if(encontrado) {
                
                Episode episodio = new Episode();
                
                episodio.setTamananho(sequenciaGenerica.length);
                
                for(int j = pos; j < pos + episodio.getTamananho(); j++) {
                    episodio.addSequenceEvents(episodios.get(i).getSequenceEvents().get(j));
                }
                
                episodiosMesmoTamanho.add(episodio);
                
            }
        }
        
        return episodiosMesmoTamanho;
    }
    
    
    private String[] ObterSequenciaGenerica() {
        
        
        int frequenciaMax = Collections.max(mapaCodigosGenericos.values());
        ArrayList<String[]> sequenciasGenericas = new ArrayList<>();
        
        
        mapaCodigosGenericos.forEach((chaveCodigo, valorCodigo)-> {
            if(valorCodigo == frequenciaMax) {
                sequenciasGenericas.add(chaveCodigo.split("\\|"));
            }
        });
        
        
        String[] sequenciaGenerica = null;
        
        int tamMax = 0;
        
        for(String[] sequencia : sequenciasGenericas) {
            
            int tam = sequencia.length;
            
            if(tam > tamMax) {
                tamMax = tam;
                sequenciaGenerica = sequencia;
            }
        }
        
        return sequenciaGenerica;
        
    }
    
    
    private void ObterFrequenciaEmEpisodios(ArrayList<ArrayList<String>> episodiosEmCodigos) {
        
        int js = 0;
        int p;
        
        StringBuilder codigoGenerico = new StringBuilder();
        
        for(int i = 0; i < episodiosEmCodigos.size() - 1; i++) {
            for(int j = 0; j < episodiosEmCodigos.get(i).size(); j++) {
                
                String codigo = episodiosEmCodigos.get(i).get(j);
                
                boolean sequencia = false;
                int tam = 0;
                p = i + 1;
                
                
                for(int k = 0; episodiosEmCodigos.get(p) != null && k < episodiosEmCodigos.get(p).size(); k++) {
                    
                    if(codigo.equals(episodiosEmCodigos.get(p).get(k))) {
                        
                        tam++;
                        
                        if(!sequencia) {
                            sequencia = true;
                            js = j;
                            codigoGenerico = new StringBuilder();
                            codigoGenerico.append(codigo);
                        } else {
                            codigoGenerico.append("|").append(codigo);
                        }
                        
                        js++;
                        
                        if(js < episodiosEmCodigos.get(i).size()) {
                            codigo = episodiosEmCodigos.get(i).get(js);
                        }
                        
                    } else if(sequencia) {
                        sequencia = false;
                        k--;
                        js = j;
                        codigo = episodiosEmCodigos.get(i).get(js);
                        
                        if(tam > 1) {
                            calcularFrequencia(mapaCodigosGenericos, codigoGenerico.toString());
                        }
                        
                        tam = 0;
                        
                    }
                    
                }
                
                if(tam > 1) {
                    calcularFrequencia(mapaCodigosGenericos, codigoGenerico.toString());
                }
                
            }
            
        }
        
    }
    
    
    private ArrayList<String> transformarEpisodiosEmCodigos(Episode episodio) {
        
        ArrayList<String> eventosEmCodigos = new ArrayList<>();
        
        
        for(int i = 0; i < episodio.getTamananho(); i++) {
            eventosEmCodigos.add(generalizarCodigoEvento(episodio.getSequenceEvents().get(i)));
        }
        
        return eventosEmCodigos;

    }
    
    
    private String generalizarCodigoEvento(Event evento) {
        
        if(evento.getGrammaticalCodes() == null) {
            return null;
        }
        
        String[] codigosEvento = evento.getGrammaticalCodes().split("\\|");
        
        if(codigosEvento.length > 1) {
            
            Map<String,Integer> mapa = new HashMap<>();
            
            for(String codigo : codigosEvento) {
                calcularFrequencia(mapa, codigo);                
            }
            
            return Collections.max(mapa.entrySet(), Map.Entry.comparingByValue()).getKey();
        }
        
        return codigosEvento[0];
        
    }
    
    
    private void calcularFrequencia(Map<String,Integer> mapa, String codigo) {
        
        if(mapa.containsKey(codigo)) {
            int frequencia = mapa.get(codigo);
            frequencia++;
            mapa.put(codigo, frequencia);
        } else {
            mapa.put(codigo, 1);
        }
    
    }
    
}
