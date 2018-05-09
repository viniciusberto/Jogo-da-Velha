package JogodaVelha.Controller.Sockets;

import JogodaVelha.Controller.Controller;
import JogodaVelha.Controller.Mensagem;
import static JogodaVelha.Controller.Mensagem.descriptografar;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Vinícius Berto
 */
public class ThreadServidor extends Thread {

    private Controller ctrl;
    private boolean ativo;
    private String ipCliente;

    public static int RECUSAR_CONEXAO = 2;
    public static int FINALIZAR = 1;

    private int porta;

    private ServerSocket serverSocket;
    private Socket socket;
    private Scanner sc;

    public ThreadServidor(Controller ctrl, int porta) {
        this.ctrl = ctrl;
        this.ativo = false;
        this.porta = porta;
    }

    private void escutar() throws IOException, BindException {
        serverSocket = new ServerSocket(porta);
        
        socket = serverSocket.accept();

        ipCliente = socket.getInetAddress().getHostAddress();

        Scanner sc;

        String msg = "";

        do {
            sc = new Scanner(socket.getInputStream());
            if (sc.hasNext()) {
                msg = sc.next();
            }
            msg = descriptografar(msg);
            if (msg != null) {
                chamarController(msg);
                msg = null;
            } else {
                encerrar(RECUSAR_CONEXAO);
                return;
            }
        } while (true);
    }

    @Override
    public void run() {
        this.ativo = true;
        while (ativo) {
            try {
                escutar();
            } catch (IOException ex) {
                break;
            }
        }
    }
  

    private void chamarController(String msg) {
        if (msg.contains("[Jogada]")) {
            ctrl.realizarJogadaExterna(Mensagem.lerJogada(msg));
        } else if (msg.contains("[Nome]")) {
            ctrl.iniciarPartidaServidor(Mensagem.lerNome(msg), ipCliente);
        } else if (msg.contains("[Inicio]")) {
            ctrl.comecar(Mensagem.lerInicio(msg));
        }

    }

    public void encerrar(int tipo) {
        try {
            if (sc != null) {
                sc.close();
            }
            if (socket != null) {
                socket.close();
            }

            serverSocket.close();

            if (tipo == 1) {
                ativo = false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean isAtivo() {
        return ativo;
    }

}
