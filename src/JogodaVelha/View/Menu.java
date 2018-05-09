package JogodaVelha.View;

import JogodaVelha.Controller.Config;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Esta classe é responsável por desenhar o menu superior da aplicação.
 *
 * @author vinicius
 */
public class Menu extends JPanel {

    JButton btnSair;

    public static final int JOGADOR_LOCAL = 0;
    public static final int JOGADOR_EXT = 1;

    private JLabel lblJ1;
    private JLabel lblJ2;

    private JLabel background;

    public Menu() {
        setPreferredSize(new Dimension(0, 60));
        setBorder(null);
        setLayout(new BorderLayout());
        setOpaque(false);
        setVisible(true);

        background = new JLabel(new ImageIcon(getClass().getResource(Config.CAMINHO_IMAGENS + "fundo-menu.png")));
        add(background);
        desenhar();
    }

    public void desenhar() {
        lblJ1 = new JLabel();
        lblJ1.setBounds(10, 13, 200, 40);
        lblJ1.setFont(Config.obterFonte("Ubuntu", Font.BOLD, 18f));

        lblJ2 = new JLabel();
        lblJ2.setBounds(215, 13, 200, 40);
        lblJ2.setFont(Config.obterFonte("Ubuntu", Font.BOLD, 18f));

        background.add(lblJ1);
        background.add(lblJ2);

    }

    public void alterarTexto(String jogadorLocal, String jogadorExterno, int ativar) {
        if (ativar == JOGADOR_LOCAL) {
            lblJ1.setForeground(new Color(170, 0, 0));
            lblJ2.setForeground(Color.BLACK);
        } else {
            lblJ2.setForeground(new Color(170, 0, 0));
            lblJ1.setForeground(Color.BLACK);
        }
        
        lblJ1.setText(jogadorLocal);
        lblJ2.setText(jogadorExterno);
    }

}
