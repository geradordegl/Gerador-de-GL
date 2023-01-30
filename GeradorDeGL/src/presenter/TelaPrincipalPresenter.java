package presenter;

import collections.ExemplosCollection;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import view.TelaPrincipalView;

public class TelaPrincipalPresenter {
    
    private TelaPrincipalView view;
    private ExemplosCollection exemplosCollection;
    private String textPath;
    
    public static void main(String args[]) {
        System.setProperty("java.library.path", System.getProperty("user.dir"));
        System.out.println(System.getProperty("java.library.path"));
        //System.loadLibrary("UnitexJni");
        System.load(System.getProperty("user.dir")+"/src/tccjni/"+"libUnitexJni.so");
        new TelaPrincipalPresenter();
        
    }
    
    public TelaPrincipalPresenter() {
        init();
    }
    
    private void init() {
        
        view = new TelaPrincipalView();
        exemplosCollection = new ExemplosCollection();
        
        
        view.getCarregarTextoOp().addActionListener((e) -> {
            lerTexto();
        });
        
        view.getSelecionarExemplosOp().addActionListener((e) -> {
            salvarExemplo();
        });
        
        view.getExibirExemplosOp().addActionListener((e) -> {
            exibirExemplos();
        });
        
        view.getLimparExemplosOption().addActionListener((e) -> {
            limparExemplos();
        });
        
        view.getGerarGramaticaOp().addActionListener((e) -> {
            gerarGramatica();
        });
        
        view.getFontSpinner().addChangeListener((ChangeEvent arg0) -> {
            view.getCaixaTexto().setFont(new Font(view.getCaixaTexto().getFont().getFamily(),
                    Font.PLAIN, (int) view.getFontSpinner().getValue()));
        });
        
        view.getSearchBtn().addActionListener((e) -> {
            view.hightLight(view.getSearchTxtField().getText());
        });
        
        view.getSearchTxtField().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent arg0) {
               if((arg0.getKeyChar() == '\b' || arg0.getKeyChar() == '\u007f') && view.getSearchTxtField().getText().length() == 0) {
                   view.getSearchBtn().setEnabled(false);
               } else {
                   view.getSearchBtn().setEnabled(true);
               }
            }
            
        });
        
        view.setLocationRelativeTo(null);
        view.setVisible(true);
        
    }
    
    public void lerTexto() {
        
        exemplosCollection.limparExemplos();
        
        try {
           new SeletorArquivoPresenter(this);
            
       } catch (IOException ex) {
           Logger.getLogger(TelaPrincipalPresenter.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
    public void escreverTextoNaCaixa(String textPath) {
        
        this.textPath = textPath;
        
        StyleContext sc = new StyleContext();
        Style style = sc.addStyle("style", null);
        Font font = new Font("Arial", Font.PLAIN, 14);       
        StyleConstants.setForeground(style, Color.BLACK);
        StyleConstants.setFontFamily(style, font.getFamily());
        StyleConstants.setBold(style, false);
        try {
            view.getCaixaTexto().getDocument().remove(0, view.getCaixaTexto().getText().length());
            view.getCaixaTexto().getDocument().insertString(0, textPath, style);
        } catch (BadLocationException ex) {
            Logger.getLogger(TelaPrincipalPresenter.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
    
    public void salvarExemplo() {
       
        String selected = view.getCaixaTexto().getSelectedText();
        if(selected != null && !selected.isEmpty()) {
            exemplosCollection.salvarExemplo(selected);
            Document doc = view.getCaixaTexto().getDocument();
            StyleContext sc = new StyleContext();
            Style style = sc.addStyle("yourStyle", null);

            Font font = new Font("Arial", Font.BOLD, 18);

            StyleConstants.setForeground(style, Color.RED);
            StyleConstants.setFontFamily(style, font.getFamily());
            StyleConstants.setBold(style, true);        
            
            int start = view.getCaixaTexto().getSelectionStart();
            int end = view.getCaixaTexto().getSelectionEnd();
            String startSubString = view.getCaixaTexto().getText().substring(0, start);
            String endSubString = view.getCaixaTexto().getText().substring(end, view.getCaixaTexto().getText().length());
            
            try {
                    Style s1 = view.getCaixaTexto().getStyle(startSubString);
                    Style s2 = view.getCaixaTexto().getStyle(endSubString);
                    
                    doc.remove(startSubString.length(), selected.length());                
                    doc.insertString(start, selected, style);
                } catch (BadLocationException ex) {
                    Logger.getLogger(TelaPrincipalPresenter.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }
    
    public void exibirExemplos() {
        new ExemplosPresenter(exemplosCollection);
    }
    
    public void limparExemplos() {
        
        exemplosCollection.limparExemplos();
        
        if(textPath != null) {
            escreverTextoNaCaixa(textPath);
        }
    }
    
    public void gerarGramatica() {
        GrafosPresenter grafosPresenter = new GrafosPresenter();
        try {
            grafosPresenter.constructGraph();
        } catch (Exception ex) {
           JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}
