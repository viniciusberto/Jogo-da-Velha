package JogodaVelha.View;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;

/**
 *
 * @author IFMS
 */
public class Botao extends JButton {

    private int posicao;

    public Botao(int posicao) {
        this.posicao = posicao;
        setName("btnJogarBotao");
        setBackground(new Color(240, 152, 0));
        setFont(new Font("Calibri", Font.BOLD, 90));
        setEnabled(false);
    }
    
    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        if (text.equals("O")) {
            setForeground(new Color(0, 127, 14));
        } else {
            setForeground(new Color(127, 0, 14));
        }
    }

}
