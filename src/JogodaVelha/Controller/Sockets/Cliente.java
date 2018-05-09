package JogodaVelha.Controller.Sockets;

import JogodaVelha.Controller.Mensagem;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Vinícius Berto
 */
public class Cliente {

    private Socket socket;
    public static final int TIPO_NOME = 1;
    public static final int TIPO_JOGADA = 2;
    public static final int TIPO_INICIO = 3;
    public static final int TIPO_CHAT = 4;
    public boolean conectado;

    public Cliente() {
        conectado = false;
    }
    
    public void conectar(String ip, int porta) {
        try {
             socket = new Socket(ip, porta);
             conectado = true;
        } catch (IOException ex) {
            conectado = false;
            JOptionPane.showMessageDialog(null, "Não foi possível conectar com o servidor\nErro: "+ex.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void enviarMensagem(String msg, int tipo) {
        try {
            if (socket != null) {
                if (socket.isConnected()) {
                    PrintStream ps;

                    ps = new PrintStream(socket.getOutputStream());

                    switch (tipo) {
                        case TIPO_NOME:
                            msg = "[Nome] {" + msg + "};";
                            break;
                        case TIPO_JOGADA:
                            msg = "[Jogada] {" + msg + "};";
                            break;
                        case TIPO_INICIO:
                            msg = "[Inicio] {" + msg + "};";
                            break;
                        case TIPO_CHAT:
                            msg = "[Chat] {" + msg + "};";
                            break;
                    }
                    ps.println(Mensagem.criptografar(msg));
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sair() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
