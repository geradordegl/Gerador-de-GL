package dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class AbrirArquivoTexto {
    
    public static String lerArquivo(Path caminho) throws IOException {
        String dados = new String(Files.readAllBytes(caminho));        
        return dados;
    }    
}
