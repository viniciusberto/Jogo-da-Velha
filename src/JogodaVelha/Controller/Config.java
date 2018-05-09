package JogodaVelha.Controller;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

/**
 *
 * @author IFMS
 */
public class Config {

    private static Config cnf = new Config();

   public static final int PORTA_SERVIDOR_CLIENTE = 2017;
   public static final int PORTA_SERVIDOR = 2018;
    
    public static final String CAMINHO_IMAGENS = "/JogodaVelha/View/Imagens/";
    public static final String CAMINHO_FONTES = "/JogodaVelha/View/Fontes/";

    public static ImageIcon obterImagem(String nome) {
        ImageIcon img = new ImageIcon(cnf.getClass().getResource(CAMINHO_IMAGENS + nome + ".png"));
        return img;
    }

    /**
     * Obtem uma fonte dos recursos da aplicação
     *
     * @param nome Nome do arquivo de fonte
     * @param estilo Estilo da fonte pode ser Font.BOLD, Font.ITALIC, Font.PLAIN
     * etc...
     * @param tamanho Tamanho da fonte
     * @return Uma fonte
     */
    public static Font obterFonte(String nome, int estilo, float tamanho) {
        Font fnt = new Font("Arial", Font.PLAIN, 15);
        InputStream is = cnf.getClass().getResourceAsStream(CAMINHO_FONTES + nome + ".ttf");

        try {
            fnt = Font.createFont(Font.TRUETYPE_FONT, is);
            is.close();
        } catch (FontFormatException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fnt.deriveFont(estilo, tamanho);
    }

    public static void configurarHintText(JTextField tf, String hint) {
        tf.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (tf.getText().equals(hint)) {
                    tf.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (tf.getText().equals("")) {
                    tf.setText(hint);
                }
            }
        });
    }

}
