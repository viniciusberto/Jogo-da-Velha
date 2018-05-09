package JogodaVelha.View;

import static JogodaVelha.Controller.Config.obterFonte;
import static JogodaVelha.Controller.Config.obterImagem;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TelaInicio extends JPanel {

    private JButton btnCliente;
    private JButton btnServidor;
    private JLabel background;

    public TelaInicio() {
        background = new JLabel(obterImagem("fundo-inicio"));
        add(background);
        setOpaque(false);

        btnCliente = new JButton(obterImagem("botao-conectar"));
        btnCliente.setName("btnClienteTelaInicio");
        btnCliente.setRolloverEnabled(true);
        btnCliente.setRolloverIcon(obterImagem("botao-conectar-hover"));
        btnCliente.setBorder(null);
        btnCliente.setContentAreaFilled(false);
        btnCliente.setBounds(90, 10, 77, 86);
        JLabel lblConectar = new JLabel("Conectar");
        lblConectar.setBounds(95, 100, 70, 15);
        lblConectar.setFont(obterFonte("Ubuntu", Font.BOLD, 15f));
        lblConectar.setForeground(new Color(239, 191, 111));

        btnServidor = new JButton(obterImagem("botao-servidor"));
        btnServidor.setName("btnServidorTelaInicio");
        btnServidor.setRolloverEnabled(true);
        btnServidor.setRolloverIcon(obterImagem("botao-servidor-hover"));
        btnServidor.setBorder(null);
        btnServidor.setContentAreaFilled(false);
        btnServidor.setBounds(250, 10, 77, 86);
        JLabel lblServidor = new JLabel("Iniciar Servidor");
        lblServidor.setBounds(234, 100, 110, 15);
        lblServidor.setFont(obterFonte("Ubuntu", Font.BOLD, 15f));
        lblServidor.setForeground(new Color(239, 191, 111));

        background.add(lblConectar);
        background.add(lblServidor);
        background.add(btnCliente);
        background.add(btnServidor);

    }

    public JButton getBtnCliente() {
        return btnCliente;
    }

    public JButton getBtnServidor() {
        return btnServidor;
    }
}
