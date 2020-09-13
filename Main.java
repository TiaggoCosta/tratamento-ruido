import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        boolean isOn = true;

        while (isOn) {
            // escolher função (0-codificar 1-decodificar)
            Object[] functions = {"Codificar", "Decodificar"};
            int op = JOptionPane.showOptionDialog(null, "Escolha a função desejada: (Para encerrar feche a janela!)", "Função",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, functions, functions[0]);

            if (op == 0) {
                System.out.println("Codificar: " + op);
            } else if (op == 1) {
                System.out.println("Decodificar: " + op);
            }
        }
    }
}