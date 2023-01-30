/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter;

import dao.AbrirArquivoTexto;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import view.DirectorySelectorView;


/**
 *
 * @author thiago
 */
public class DirectorySelectorPresenter {
     private DirectorySelectorView view;

    public DirectorySelectorPresenter() throws IOException{
        this.view = new DirectorySelectorView();
        this.view.setLocationRelativeTo(null);
        
    }
    public File selectFolder() throws IOException{
        //this.view.getSelecionadorDeArquivo().setFileFilter(this.view.getFiltro());
        
        int returnVal = this.view.getSelecionadorDeArquivo().showSaveDialog(view);
        //if(returnVal == this.view.getSelecionadorDeArquivo().APPROVE_OPTION) {            
           
                  //return this.view.getSelecionadorDeArquivo().getSelectedFile().toPath();
                  return this.view.getSelecionadorDeArquivo().getSelectedFile();
        //}
    }
    public void dispose(){
        this.view.setVisible(false);
        this.view.dispose();
    }
    
}
