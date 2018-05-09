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
public class TelaServidor extends JPanel implements Runnable {

    private JLabel lblStatus;
    private JLabel background;
    private JTextField tfNome;
    private JButton btnIniciar;
    private boolean status;
    private Thread t;

    public TelaServidor() {
        background = new JLabel(obterImagem("fundo-inicio"));
        add(background);
        setOpaque(false);
        status = false;

        tfNome = new JTextField("Digite seu nome!");
        Config.configurarHintText(tfNome, "Digite seu nome!");
        tfNome.setBounds(75, 50, 170, 30);
        tfNome.setFont(obterFonte("Ubuntu", Font.BOLD, 15f));
        tfNome.setBackground(new Color(220, 149, 44));
        tfNome.setBorder(new LineBorder(new Color(114, 72, 13), 2));
        tfNome.setForeground(Color.BLACK);

        btnIniciar = new JButton("Iniciar");
        btnIniciar.setName("btnIniciarTelaServidor");
        btnIniciar.setBounds(250, 50, 90, 31);
        btnIniciar.setFont(obterFonte("Ubuntu", Font.BOLD, 15f));
        btnIniciar.setBackground(new Color(114, 72, 13));
        btnIniciar.setFocusPainted(false);
        btnIniciar.setForeground(new Color(254, 233, 171));

        background.add(tfNome);
        background.add(btnIniciar);
        tfNome.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnIniciar.doClick();
                }
            }
        });

    }

    public void alterarTexto(String texto) {
        status = false;
        t.interrupt();
        lblStatus.setBounds(50, 45, 330, 36);
        lblStatus.setText(texto);
    }

    public void exibirStatus() {
        tfNome.setVisible(false);
        btnIniciar.setVisible(false);
        background.removeAll();
        status = true;

        lblStatus = new JLabel("Aguardando Conexão...");
        lblStatus.setBounds(110, 45, 220, 36);
        lblStatus.setFont(obterFonte("Ubuntu", Font.BOLD, 18f));
        lblStatus.setForeground(new Color(239, 191, 111));

        background.add(lblStatus);
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        while (isVisible() && status) {
            try {
                for (int i = 0; i < 5; i++) {
                    lblStatus.setBounds(110, 45, 220, 36);
                    lblStatus.setText("Aguardando Conexão...");
                    Thread.sleep(400);
                    lblStatus.setText("Aguardando Conexão..");
                    Thread.sleep(400);
                    lblStatus.setText("Aguardando Conexão.");
                    Thread.sleep(400);
                    lblStatus.setText("Aguardando Conexão");
                    Thread.sleep(400);
                    lblStatus.setText("Aguardando Conexão.");
                    Thread.sleep(400);
                    lblStatus.setText("Aguardando Conexão..");
                    Thread.sleep(400);
                    repaint();
                }
                lblStatus.setBounds(50, 45, 330, 36);
                lblStatus.setText("Para cancelar pressione a tecla 'Esc'");
                Thread.sleep(3500);

            } catch (InterruptedException ex) {
                ;
            }
        }
    }

    public String getNome() {
        return tfNome.getText();
    }

    public JButton getBtnIniciar() {
        return btnIniciar;
    }
}
