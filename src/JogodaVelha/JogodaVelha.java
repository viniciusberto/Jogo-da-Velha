package JogodaVelha;

import JogodaVelha.Controller.Controller;

/**
 *
 * @author IFMS
 */
public class JogodaVelha {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Controller();
            }
        });
    }
}