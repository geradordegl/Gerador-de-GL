package presenter;

import dao.AbrirArquivoTexto;
import java.io.IOException;
import view.SeletorArquivoView;


public class SeletorArquivoPresenter {
    
    private SeletorArquivoView seletorView;
    

    public SeletorArquivoPresenter(TelaPrincipalPresenter principalView) throws IOException {
        
        seletorView = new SeletorArquivoView();
        
        seletorView.setLocationRelativeTo(null);
        selecionarArquivo(principalView);
        
    }
    
    public void selecionarArquivo(TelaPrincipalPresenter principalView) throws IOException {
        
        seletorView.getSelecionadorDeArquivo().setFileFilter(seletorView.getFiltro());
        
        int returnVal = seletorView.getSelecionadorDeArquivo().showOpenDialog(seletorView);
        if(returnVal == seletorView.getSelecionadorDeArquivo().APPROVE_OPTION) { 
           principalView.escreverTextoNaCaixa(
                   new AbrirArquivoTexto().lerArquivo(seletorView
                           .getSelecionadorDeArquivo().getSelectedFile().toPath()));
           
        }
    }
    
}
