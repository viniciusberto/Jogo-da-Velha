package JogodaVelha.Controller;

import static JogodaVelha.Controller.Config.PORTA_SERVIDOR;
import JogodaVelha.Controller.Sockets.Cliente;
import JogodaVelha.Controller.Sockets.ThreadServidor;
import static JogodaVelha.Controller.Sockets.ThreadServidor.FINALIZAR;
import JogodaVelha.Model.Jogada;
import JogodaVelha.View.Botao;
import JogodaVelha.View.Tabuleiro;
import JogodaVelha.View.TelaCliente;
import JogodaVelha.View.TelaInicio;
import JogodaVelha.View.TelaServidor;
import JogodaVelha.View.Telas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import static JogodaVelha.Controller.Config.PORTA_SERVIDOR_CLIENTE;
import JogodaVelha.View.Menu;
import java.util.List;

/**
 *
 * @author IFMS
 */
public final class Controller extends KeyAdapter implements ActionListener {

    private Telas telas;

    private String nomeLocal;
    private String nomeExt;
    private int pontosLocal;
    private int pontosExt;

    private ThreadServidor servidor;
    private boolean conectado = false;
    private String XO;
    private int quantidadePartida = 0;
    private boolean autorizacao = false;
    private Cliente cliente;
    private int jogadas = 0;

    public Controller() {
        carregarGraficos();
        cliente = new Cliente();
    }

    private void carregarGraficos() {
        telas = new Telas();
        telas.setVisible(true);
        configTelas();
        telas.desenharTelaInicio();
        configTelaInicio();
    }

    public void configTelas() {
        telas.getBtnVoltar().addActionListener(this);
    }

    public void configTelaInicio() {
        TelaInicio tInicio = telas.getTelaInicio();
        tInicio.getBtnCliente().addActionListener(this);
        tInicio.getBtnServidor().addActionListener(this);
    }

    public void configTelaCliente() {
        TelaCliente tCliente = telas.getTelaCliente();
        tCliente.getBtnConectar().addActionListener(this);
    }

    public void configTelaServidor() {
        TelaServidor tServidor = telas.getTelaServidor();
        tServidor.getBtnIniciar().addActionListener(this);
    }

    public void configTabuleiro() {
        Tabuleiro tabuleiro = telas.getTabuleiro();
        for (Botao btn : tabuleiro.getBotoes()) {
            btn.addActionListener(this);
        }
        alterarStatusBotaoJogar();
    }

    public void enviarJogada(String xo, int botao) {
        String msg = xo + "," + botao;
        cliente.enviarMensagem(msg, Cliente.TIPO_JOGADA);
        autorizacao = false;
        jogadas++;
        verificarFim();
        alterarStatusMenu(Menu.JOGADOR_EXT);
    }

    private void alterarStatusMenu(int ativo) {
        String local;
        String ext;
        local = "'" + XO + "'" + nomeLocal + ": " + pontosLocal;
        if (XO.equals("X")) {
            ext = "'O' " + nomeExt + ": " + pontosExt;
        } else {
            ext = "'X' " + nomeExt + ": " + pontosExt;
        }
        telas.getMenu().alterarTexto(local, ext, ativo);
    }

    public void realizarJogadaExterna(Jogada jogada) {
        autorizacao = true;
        alterarStatusBotaoJogar();
        telas.getTabuleiro().getBotoes().get(jogada.getLocalizacao()).setText(jogada.getXo());
        jogadas++;
        verificarFim();
        alterarStatusMenu(Menu.JOGADOR_LOCAL);
    }

    private boolean verificarNome(String nome) {
        boolean resp = false;
        if (nome.length() > 4) {
            if (!nome.equals("Digite seu nome!")) {
                resp = true;
            } else {
                JOptionPane.showMessageDialog(telas, "Para iniciar digite seu nome!", "Nome", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(telas, "Seu nome deve conter no mínimo 5 caracteres!", "Nome", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public void iniciarPartidaServidor(String nome, String ip) {
        nomeExt = nome;
        telas.getTelaServidor().alterarTexto("Conexão com  " + nome + " estabelecida!");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        cliente.conectar(ip, PORTA_SERVIDOR_CLIENTE);
        cliente.enviarMensagem(nomeLocal, Cliente.TIPO_INICIO);

        telas.desenharTabuleiro();
        configTabuleiro();
        XO = "O";
        autorizacao = false;
        alterarStatusBotaoJogar();
        alterarStatusMenu(Menu.JOGADOR_EXT);
    }

    public void comecar(String nome) {
        nomeExt = nome;
        XO = "X";
        autorizacao = true;
        alterarStatusBotaoJogar();
        alterarStatusMenu(Menu.JOGADOR_LOCAL);
    }

    public void finalizarPartida(String xo) {
        if (xo.equals("empate")) {
            JOptionPane.showMessageDialog(telas, "Não houve vencedores!");
        }
        if (XO.equals("X")) {
            if (xo.equals("X")) {
                JOptionPane.showMessageDialog(telas, "O jogador 'X' " + nomeLocal + " venceu!");
                pontosLocal++;
            } else if (xo.equals("O")) {
                JOptionPane.showMessageDialog(telas, "O jogador 'O' " + nomeExt + " venceu!");
                pontosExt++;
            }
            restaurarTextoBotaoJogar();
            jogadas = 0;
        } else {
            if (xo.equals("O")) {
                JOptionPane.showMessageDialog(telas, "O jogador 'O' " + nomeLocal + " venceu!");
                pontosLocal++;
            } else if (xo.equals("X")) {
                JOptionPane.showMessageDialog(telas, "O jogador 'X' " + nomeExt + " venceu!");
                pontosExt++;
            }
            restaurarTextoBotaoJogar();
            jogadas = 0;
        }
    }

    public void verificarFim() {
        List<Botao> btns = telas.getTabuleiro().getBotoes();
        //Verificar verticais
        if (btns.get(0).getText().equals("X") && btns.get(3).getText().equals("X") && btns.get(6).getText().equals("X")) {
            finalizarPartida("X");
        } else if (btns.get(0).getText().equals("O") && btns.get(3).getText().equals("O") && btns.get(6).getText().equals("O")) {
            finalizarPartida("O");
        } else if (btns.get(1).getText().equals("X") && btns.get(4).getText().equals("X") && btns.get(7).getText().equals("X")) {
            finalizarPartida("X");
        } else if (btns.get(1).getText().equals("O") && btns.get(4).getText().equals("O") && btns.get(7).getText().equals("O")) {
            finalizarPartida("O");
        } else if (btns.get(2).getText().equals("X") && btns.get(5).getText().equals("X") && btns.get(8).getText().equals("X")) {
            finalizarPartida("X");
        } else if (btns.get(2).getText().equals("O") && btns.get(5).getText().equals("O") && btns.get(8).getText().equals("O")) {
            finalizarPartida("O");
        }

        //Verificar Horizontais
        if (btns.get(0).getText().equals("X") && btns.get(1).getText().equals("X") && btns.get(2).getText().equals("X")) {
            finalizarPartida("X");
        } else if (btns.get(0).getText().equals("O") && btns.get(1).getText().equals("O") && btns.get(2).getText().equals("O")) {
            finalizarPartida("O");
        } else if (btns.get(3).getText().equals("X") && btns.get(4).getText().equals("X") && btns.get(5).getText().equals("X")) {
            finalizarPartida("X");
        } else if (btns.get(3).getText().equals("O") && btns.get(4).getText().equals("O") && btns.get(5).getText().equals("O")) {
            finalizarPartida("O");
        } else if (btns.get(6).getText().equals("X") && btns.get(7).getText().equals("X") && btns.get(8).getText().equals("X")) {
            finalizarPartida("X");
        } else if (btns.get(6).getText().equals("O") && btns.get(7).getText().equals("O") && btns.get(8).getText().equals("O")) {
            finalizarPartida("O");
        }

        //Diagonais
        if (btns.get(0).getText().equals("X") && btns.get(4).getText().equals("X") && btns.get(8).getText().equals("X")) {
            finalizarPartida("X");
        } else if (btns.get(0).getText().equals("O") && btns.get(4).getText().equals("O") && btns.get(8).getText().equals("O")) {
            finalizarPartida("O");
        } else if (btns.get(2).getText().equals("X") && btns.get(4).getText().equals("X") && btns.get(6).getText().equals("X")) {
            finalizarPartida("X");
        } else if (btns.get(2).getText().equals("O") && btns.get(4).getText().equals("O") && btns.get(6).getText().equals("O")) {
            finalizarPartida("O");
        }

        //Empate
        if (jogadas == 9) {
            finalizarPartida("empate");
        }
    }

    public void iniciarPartidaCliente() {
        nomeLocal = telas.getTelaCliente().getNome();
        conectarComServidor();
        if (cliente.conectado) {
            XO = "X";
            autorizacao = true;
            iniciarServidorCliente();
            telas.desenharTabuleiro();
            configTabuleiro();
            alterarStatusBotaoJogar();
        }
    }

    private void iniciarServidorCliente() {
        if (servidor != null) {
            servidor.encerrar(FINALIZAR);
        }
        servidor = new ThreadServidor(this, PORTA_SERVIDOR_CLIENTE);
        servidor.start();
    }

    public void conectarComServidor() {
        cliente.conectar(telas.getTelaCliente().getIp(), PORTA_SERVIDOR);
        cliente.enviarMensagem(nomeLocal, Cliente.TIPO_NOME);
    }

    private void iniciarServidor() {
        if (servidor != null) {
            servidor.encerrar(FINALIZAR);
        }
        servidor = new ThreadServidor(this, PORTA_SERVIDOR);
        servidor.start();
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (servidor.isAtivo()) {
            telas.getTelaServidor().exibirStatus();
        }
    }

    public void alterarStatusBotaoJogar() {
        for (Botao btn : telas.getTabuleiro().getBotoes()) {
            btn.setEnabled(autorizacao);
        }
    }

    public void restaurarTextoBotaoJogar() {
        for (Botao btn : telas.getTabuleiro().getBotoes()) {
            btn.setText("");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        String btnNome = btn.getName();

        switch (btnNome) {

            case "btnServidorTelaInicio":
                telas.desenharTelaServidor();
                configTelaServidor();
                break;

            case "btnClienteTelaInicio":
                telas.desenharTelaCliente();
                configTelaCliente();
                break;

            case "btnVoltarTelas":
                if (servidor != null) {
                    if (servidor.isAtivo()) {
                        servidor.encerrar(FINALIZAR);
                    }
                }
                telas.desenharTelaInicio();
                configTelaInicio();
                break;

            case "btnConectarTelaCliente":
                if (telas.getTelaCliente().getNome() != null && telas.getTelaCliente().getIp() != null
                        && !telas.getTelaCliente().getNome().equals("Digite seu nome!") && !telas.getTelaCliente().getIp().equals("Digite o IP do servidor")) {
                    iniciarPartidaCliente();
                } else {
                    JOptionPane.showMessageDialog(telas, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
                break;

            case "btnIniciarTelaServidor":
                if (verificarNome(telas.getTelaServidor().getNome())) {
                    nomeLocal = telas.getTelaServidor().getNome();
                    iniciarServidor();
                }
                break;

            case "btnJogarBotao":
                Botao btnJogar = (Botao) btn;
                if (btnJogar.getText() == "") {
                    autorizacao = false;
                    alterarStatusBotaoJogar();
                    btn.setText(XO);
                    enviarJogada(XO, btnJogar.getPosicao());
                    verificarFim();
                }
                break;

        }

    }

    public String getNomeLocal() {
        return nomeLocal;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
