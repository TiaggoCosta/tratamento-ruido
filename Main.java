import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
                }
            }

            // seleção de arquivo
            final JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(false);
            fileChooser.setCurrentDirectory(new java.io.File("./arquivos"));
            if (op == 1) {
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.cod", "cod");
                fileChooser.setFileFilter(filter);
                fileChooser.addChoosableFileFilter(filter);
            }
            File selectedFile = null;
            int retVal = fileChooser.showOpenDialog(null);
            if (retVal == JFileChooser.APPROVE_OPTION) {
                if (op == 1) {
                    while (retVal == JFileChooser.APPROVE_OPTION && !fileChooser.getSelectedFile().getName().endsWith(".cod")) {
                        JOptionPane.showMessageDialog(null, "O arquivo "
                                        + fileChooser.getSelectedFile().getName() + " não é um arquivo codificado!",
                                "Erro de compatibilidade", JOptionPane.ERROR_MESSAGE);
                        retVal = fileChooser.showOpenDialog(null);
                    }
                }
                selectedFile = fileChooser.getSelectedFile();
                JOptionPane.showMessageDialog(null, selectedFile.getName());
            }
        }
    }
}