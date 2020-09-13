import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        boolean isOn = true;

        while (isOn) {
            // escolher função (0-codificar 1-decodificar)
            Object[] functions = {"Codificar", "Decodificar"};
            int op = JOptionPane.showOptionDialog(null, "Escolha a função desejada: (Para encerrar feche a janela!)", "Função",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, functions, functions[0]);

            if (op == -1) {
                System.out.println("Finalizar: " + op);
                Object[] options = {"Sim, finalizar programa", "Não, desejo recomeçar"};
                int end = JOptionPane.showOptionDialog(null, "Deseja encerrar o programa?", "Finalizar",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                if (end == 0) {
                    isOn = false;
                    break;
                } else {
                    continue;
                }
            }
        }
    }
}