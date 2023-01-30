package presenter;

import collections.ExemplosCollection;
import javax.swing.table.DefaultTableModel;
import view.ExemplosView;


public class ExemplosPresenter {
    
    private ExemplosView view;
    
    
    public ExemplosPresenter(ExemplosCollection exemplosCollection) { 
        
        view = new ExemplosView();
        exibirExemplos(exemplosCollection);
        
        view.getBtnEditarExemplos().addActionListener((e) -> {
            editarExemplos(exemplosCollection);
        });
        
        view.getBtnSair().addActionListener((e) -> {
            sair();
        });
        
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }
    
    public void exibirExemplos(ExemplosCollection exemplosCollection) {
        
        DefaultTableModel model = (DefaultTableModel) view.getTabelaExemplos().getModel();
        model.setNumRows(0);
        for(String exemplo: exemplosCollection.getListaExemplos()) {
           model.addRow(new Object[] {
               exemplo, false
           });
        }
    }
    
    public void editarExemplos(ExemplosCollection exemplosCollection) {
        
        for(int linha = 0; linha < view.getTabelaExemplos().getRowCount(); linha++) {
            if((boolean)view.getTabelaExemplos().getValueAt(linha, 1)) {
                exemplosCollection.getListaExemplos().set(linha, (String)view.getTabelaExemplos().getValueAt(linha, 0));
                view.getTabelaExemplos().setValueAt(false, linha, 1);
            }
        }
    }
    
    public void sair() {
        view.dispose();
    }
    
    
}