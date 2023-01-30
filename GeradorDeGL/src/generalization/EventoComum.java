package generalization;


public class EventoComum {
    
    private String sequencia;
    private Episode episodio1;
    private Episode episodio2;
    private int posicao;
    private int tamanho;
    
    

    public EventoComum(String sequencia, Episode episodio1, Episode episodio2, int posicao, int tamanho) {
        this.sequencia = sequencia;
        this.episodio1 = episodio1;
        this.episodio2 = episodio2;
        this.posicao = posicao;
        this.tamanho = tamanho;
    }
    

    public String getSequencia() {
        return sequencia;
    }

    public void setSequencia(String sequencia) {
        this.sequencia = sequencia;
    }
    
    public Episode getEpisodio1() {
        return episodio1;
    }

    public void setEpisodio1(Episode episodio1) {
        this.episodio1 = episodio1;
    }
    
    public Episode getEpisodio2() {
        return episodio2;
    }

    public void setEpisodio2(Episode episodio2) {
        this.episodio2 = episodio2;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }
    
    
    
}
