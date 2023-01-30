package generalization;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;


public class Graph {
    
    private String caminho = System.getProperty("user.dir") + "/src/generalization/grfTemplate.grf";    
    private Path path = (Path)Paths.get("grfTemplate.grf");
    private String graph;
    
    

    public String getGraph() {
        return graph;
    }
    
    public void constructGraph(ArrayList<Map<String,Integer>> solution) throws FileNotFoundException, IOException {
        
        int solutionSize = solution.size();

        // AbrirArquivoTexto.lerArquivo(path);
        //System.out.println("imprindo arquivo gtf: "+ AbrirArquivoTexto.lerArquivo(path));
        
        File fileToBeModified = new File(caminho);
        String oldContent = "";
        BufferedReader reader = new BufferedReader(new FileReader(caminho));
        String line = reader.readLine();
        
        while(line != null) {
            //System.out.println(line);
            oldContent = oldContent + line + System.lineSeparator();
            line = reader.readLine();
        }
        reader.close();
        
        int numeroDeNos = 0;
        for(Map<String,Integer> map : solution) {
            numeroDeNos = numeroDeNos + map.size();
        }
        
        oldContent = oldContent + (numeroDeNos + 2) + System.lineSeparator();//set number of nodes on the graph
        int f = 0;
        //começa com 2 estados o inicial 0 e o final 1
        int currentStateNumber = 1;       
        //palavraReconhecida posicaoX posicaoY numTransicoesSaída nosAlcançaveis

        //initial state
        oldContent = oldContent + "\"<E>\"" + " 20" + " 300 " + solution.get(0).size();
        for(String chaves : solution.get(0).keySet()){
            oldContent = oldContent + " " + (currentStateNumber + 1);
            currentStateNumber++;
        }
        oldContent = oldContent + " " + System.lineSeparator();
        
        currentStateNumber = 2;
        //final node
        oldContent = oldContent + "\" \" " + "1000 " + "300 " + "0 " + System.lineSeparator();
        int xAxle = 50;
        for(int i = 0; i < solutionSize; i++) {
            int yAxle = 30;
            if(i < solutionSize - 1) {
                String estadosFilhos = "";
                int localStateNumber = currentStateNumber + solution.get(i).size();
                for(String chave : solution.get(i + 1).keySet()) {
                    estadosFilhos = estadosFilhos + " " + localStateNumber;
                    localStateNumber++;
                }
                
                for(String chave : solution.get(i).keySet()) {
                    oldContent = oldContent + "\"" + chave + "\" " + xAxle +" " + yAxle + " " + 
                            solution.get(i+1).size() + estadosFilhos + " " + System.lineSeparator();
                    currentStateNumber++;
                    yAxle = yAxle + 50;
                }
                
           } else {
                for(String chave : solution.get(i).keySet()) {
                    oldContent = oldContent + "\"" + chave + "\" " + xAxle + " " + yAxle + " " + 
                            1 + " " + 1 + " " + System.lineSeparator();
                    yAxle = yAxle + 50;
                }
            }
            
            xAxle = xAxle + 50;
        }
        System.out.println(oldContent);
        this.graph = oldContent;
    }
}
