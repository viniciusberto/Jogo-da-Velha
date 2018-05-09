package JogodaVelha.Controller;

import JogodaVelha.Model.Jogada;
import java.util.Base64;
import java.util.Calendar;

/**
 *
 * @author IFMS
 */
public class Mensagem {

    public static String criptografar(String msg) {
        msg = gerarChave() + "|" + msg;
        byte[] bytes = Base64.getEncoder().encode(msg.getBytes());

        return new String(bytes);
    }

    private static String gerarChave() {
        Calendar cl = Calendar.getInstance();
        String str = "vh$-b54s@" + cl.get(Calendar.MINUTE) + "#2i5F%M$";
        return str;
    }

    public static String descriptografar(String msg) {
        String str;
        try {
            byte[] bytes = Base64.getDecoder().decode(msg.getBytes());
            str = new String(bytes);
            String chave = gerarChave();
            if (str.length() > chave.length()) {
                if (str.charAt(chave.length()) == '|' && str.substring(0, chave.length()).equals(chave)) {
                    str = str.substring(chave.length() + 1, str.length());
                } else {
                    str = null;
                }
            } else {
                str = null;
            }
        } catch (IllegalArgumentException | NullPointerException ie) {
            str = null;
        }

        return str;
    }

    public static Jogada lerJogada(String msg) {
        Jogada j = new Jogada();
        msg = msg.replace("[Jogada] {", "");
        j.setXo(msg.substring(0, 1));
        msg = msg.replace(msg.substring(0, 2), "");
        j.setLocalizacao(Integer.parseInt(msg.substring(0, 1)));

        return j;
    }

    public static String lerNome(String msg) {
        msg = msg.replace("[Nome] {", "");
        msg = (msg.substring(0, msg.length() - 2));
        return msg;
    }

    public static String lerInicio(String msg) {
        msg = msg.replace("[Inicio] {", "");
        msg = (msg.substring(0, msg.length() - 2));
        return msg;
    }
}
