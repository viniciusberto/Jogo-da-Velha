package JogodaVelha.Model;

/**
 *
 * @author vinicius
 */
public class Jogada {
    private String xo;
    private int localizacao;

    public Jogada(String xo, int localizacao) {
        this.xo = xo;
        this.localizacao = localizacao;
    }

    public Jogada() {
    }
    

    public String getXo() {
        return xo;
    }

    public void setXo(String xo) {
        this.xo = xo;
    }

    public int getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(int localizacao) {
        this.localizacao = localizacao;
    }
    
    
    
}
