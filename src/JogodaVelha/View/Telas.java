package JogodaVelha.View;

import static JogodaVelha.Controller.Config.obterImagem;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author IFMS
 */

public final class Telas extends JFrame {

    private TelaInicio telaInicio;
    private TelaServidor telaServidor;
    private TelaCliente telaCliente;

    private Tabuleiro tabuleiro;
    private Menu menu;

    private Point point = new Point();
    private JLabel background;

    private JButton btnVoltar;
    private JButton btnSair;

    public Telas() {
        configurarFrame();
    }

    public void desenharTelaInicio() {
        if (telaCliente != null) {
            if (telaCliente.isVisible()) {
                telaCliente.setVisible(false);
            }
        }
        if (telaServidor != null) {
            if (telaServidor.isVisible()) {
                telaServidor.setVisible(false);
            }
        }
        if (background.getComponentCount() > 2) {
            background.remove(2);
        }

        btnVoltar.setVisible(false);
        btnSair.setVisible(true);
        telaInicio = new TelaInicio();
        telaInicio.setBounds(56, 190, 425, 130);
        background.add(telaInicio);
    }

    public void desenharTelaServidor() {
        telaInicio.setVisible(false);
        btnVoltar.setVisible(true);
        btnSair.setVisible(false);
        if (background.getComponentCount() > 2) {
            background.remove(2);
        }
        telaServidor = new TelaServidor();
        telaServidor.setBounds(56, 190, 425, 130);
        background.add(telaServidor);
    }

    public void desenharTelaCliente() {
        btnVoltar.setVisible(true);
        telaInicio.setVisible(false);
        btnSair.setVisible(false);
        if (background.getComponentCount() > 2) {
            background.remove(2);
        }

        telaCliente = new TelaCliente();
        telaCliente.setBounds(75, 43, 380, 440);
        background.add(telaCliente);
    }

    public void desenharTabuleiro() {
        if (background.getComponentCount() > 2) {
            background.getComponent(2).setVisible(false);
            background.remove(2);
        }

        tabuleiro = new Tabuleiro();
        tabuleiro.setBounds(58, 79, 425, 405);
        background.add(tabuleiro);

        menu = new Menu();
        menu.setBounds(58, 17, 425, 67);
        background.add(menu);

        btnVoltar.setVisible(false);
        btnSair.setVisible(true);
    }

    private void configurarFrame() {
        setTitle("Jogo da Velha");
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBackground(new Color(0, 0, 0, 0));
        setBounds(0, 0, 540, 512);
        setLocationRelativeTo(null);
        setResizable(false);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                point.x = e.getX();
                point.y = e.getY();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point p = getLocation();
                setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
            }
        });

        background = new JLabel(obterImagem("fundo-tela"));
        add(background);

        btnSair = new JButton();
        btnSair.setBounds(490, 15, 32, 32);
        btnSair.setIcon(obterImagem("sair"));
        btnSair.setContentAreaFilled(false);
        btnSair.setBorder(null);
        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Deseja realmente sair?",
                        "Sair", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });
        background.add(btnSair);

        btnVoltar = new JButton(obterImagem("voltar"));
        btnVoltar.setName("btnVoltarTelas");
        btnVoltar.setBounds(15, 15, 32, 32);
        btnVoltar.setFocusPainted(false);
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setBorder(null);

        background.add(btnVoltar);
        repaint();
    }

    public TelaInicio getTelaInicio() {
        return telaInicio;
    }

    public JButton getBtnVoltar() {
        return btnVoltar;
    }

    public TelaCliente getTelaCliente() {
        return telaCliente;
    }

    public TelaServidor getTelaServidor() {
        return telaServidor;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public Menu getMenu() {
        return menu;
    }
    
}
