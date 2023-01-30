package collections;

import java.util.ArrayList;


public class ExemplosCollection {
    
    private static ArrayList<String> listaExemplos;
    
    
    public ExemplosCollection(){
        listaExemplos = new ArrayList<>();
    }
    
    public void salvarExemplo(String exemplo){
        listaExemplos.add(exemplo);
    }

    public static ArrayList<String> getListaExemplos() {
        return listaExemplos;
    }
    
    public static int getQuantidadeExemplos(){
        return listaExemplos.size();
    }
    
    public void limparExemplos(){
        listaExemplos.clear();
    }
    
}
