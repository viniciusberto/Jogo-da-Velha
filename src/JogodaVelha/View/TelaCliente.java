package JogodaVelha.View;

import JogodaVelha.Controller.Config;
import static JogodaVelha.Controller.Config.obterFonte;
import static JogodaVelha.Controller.Config.obterImagem;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 *
 * @author IFMS
 */
public class TelaCliente extends JPanel {

    private JLabel background;

    private JLabel logo;

    private JTextField tfIP;

    private JTextField tfNome;

    private JButton btnConectar;

    public TelaCliente() {
        background = new JLabel(obterImagem("fundo-cliente"));
        add(background);
        setOpaque(false);

        logo = new JLabel(obterImagem("logo"));
        logo.setBounds(135, 65, 96, 96);

        tfIP = new JTextField("Digite o IP do servidor");
        Config.configurarHintText(tfIP, "Digite o IP do servidor");
        tfIP.setBounds(100, 190, 170, 30);
        tfIP.setFont(obterFonte("Ubuntu", Font.BOLD, 15f));
        tfIP.setBackground(new Color(220, 149, 44));
        tfIP.setBorder(new LineBorder(new Color(114, 72, 13), 2));
        tfIP.setForeground(Color.BLACK);

        tfNome = new JTextField("Digite seu nome!");
        Config.configurarHintText(tfNome, "Digite seu nome!");
        tfNome.setBounds(tfIP.getX(), tfIP.getY() + tfIP.getHeight() + 20, 170, 30);
        tfNome.setFont(obterFonte("Ubuntu", Font.BOLD, 15f));
        tfNome.setBackground(new Color(220, 149, 44));
        tfNome.setBorder(new LineBorder(new Color(114, 72, 13), 2));
        tfNome.setForeground(Color.BLACK);

        btnConectar = new JButton("Conectar");
        btnConectar.setName("btnConectarTelaCliente");
        btnConectar.setBounds(tfNome.getX() + ((tfNome.getWidth() - 110) / 2), tfNome.getY() + tfNome.getHeight() + 20, 110, 35);
        btnConectar.setFont(obterFonte("Ubuntu", Font.BOLD, 15f));
        btnConectar.setBackground(new Color(114, 72, 13));
        btnConectar.setFocusPainted(false);
        btnConectar.setForeground(new Color(254, 233, 171));

        background.add(logo);
        background.add(tfIP);
        background.add(tfNome);
        background.add(btnConectar);

        tfIP.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    tfNome.requestFocus();
                }
            }

        });
        tfNome.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnConectar.doClick();
                }
            }

        });
    }

    public JButton getBtnConectar() {
        return btnConectar;
    }

    public String getIp() {
        return tfIP.getText();
    }

    public String getNome() {
        return tfNome.getText();
    }

}
