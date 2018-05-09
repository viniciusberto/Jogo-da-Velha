package JogodaVelha.View;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Tabuleiro extends JPanel {

    private JPanel pnlBotoes;
    private List<Botao> lista;
    private JLabel background;

    public Tabuleiro() {
        setOpaque(false);
        setVisible(true);

        background = new JLabel();
        background.setIcon(new ImageIcon(getClass().getResource("/JogodaVelha/View/Imagens/fundo-tabuleiro.png")));
        background.setBounds(0, 0, 418, 398);
        background.setLayout(new BorderLayout(5, 5));
        add(background);

        pnlBotoes = new JPanel();
        pnlBotoes.setOpaque(false);
        pnlBotoes.setLayout(new GridLayout(3, 3, 5, 5));
        pnlBotoes.add(new Botao(0));
        pnlBotoes.add(new Botao(1));
        pnlBotoes.add(new Botao(2));
        pnlBotoes.add(new Botao(3));
        pnlBotoes.add(new Botao(4));
        pnlBotoes.add(new Botao(5));
        pnlBotoes.add(new Botao(6));
        pnlBotoes.add(new Botao(7));
        pnlBotoes.add(new Botao(8));

        lista = new ArrayList<>();
        for (Component cmp : pnlBotoes.getComponents()) {
            lista.add((Botao) cmp);
        }

        background.add(new LblVazio(),BorderLayout.NORTH);
        background.add(new LblVazio(),BorderLayout.SOUTH);
        background.add(new LblVazio(),BorderLayout.EAST);
        background.add(new LblVazio(),BorderLayout.WEST);
        background.add(pnlBotoes, BorderLayout.CENTER);
    }

    public List<Botao> getBotoes() {
        return lista;
    }

}

class LblVazio extends JLabel{

    public LblVazio() {
        setPreferredSize(new Dimension(30,30));
    }

}
