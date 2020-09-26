import crc.InvalidCRC;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

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
            FileNameExtensionFilter filter;
            if (op == 0) {
                filter = new FileNameExtensionFilter("*.cod", "cod");
            } else {
                filter = new FileNameExtensionFilter("*.ecc", "ecc");

            }
            fileChooser.setFileFilter(filter);
            fileChooser.addChoosableFileFilter(filter);

            File selectedFile = null;
            int retVal = fileChooser.showOpenDialog(null);
            if (retVal == JFileChooser.APPROVE_OPTION) {
                if (op == 1) {
                    while (retVal == JFileChooser.APPROVE_OPTION && !fileChooser.getSelectedFile().getName().endsWith(".ecc")) {
                        JOptionPane.showMessageDialog(null, "O arquivo "
                                        + fileChooser.getSelectedFile().getName() + " não é um arquivo codificado!",
                                "Erro de compatibilidade", JOptionPane.ERROR_MESSAGE);
                        retVal = fileChooser.showOpenDialog(null);
                    }
                }
                selectedFile = fileChooser.getSelectedFile();
                JOptionPane.showMessageDialog(null, selectedFile.getName());
            }

            if (retVal == 1) {
                System.out.println("Cancel/close: " + retVal);
                continue;
            } else if (retVal == 0) {
                System.out.println("Open: " + retVal);
            }

            TratamentoRuido noiseTreatment = new TratamentoRuido();

            if (op == 1) {
                try {
                    byte[] data = Files.readAllBytes(selectedFile.toPath());
                    byte[] result = noiseTreatment.checkNoiseTreatment(data);
                    final String ext = ".cod";
                    String filePath = selectedFile.getPath();
                    int extIndex = filePath.lastIndexOf(".");
                    String newPath = (extIndex > -1 ? filePath.substring(0, extIndex) : filePath) + ext;
                    Files.write(Paths.get(newPath), result);
                    JOptionPane.showMessageDialog(null, "Decodificação concluída com sucesso");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InvalidCRC e) {
                    JOptionPane.showMessageDialog(null, "O arquivo escolhido foi corrompido, não é possível decodificá-lo",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                try {
                    byte[] data = Files.readAllBytes(selectedFile.toPath());
                    byte[] result = noiseTreatment.addNoiseTreatment(data);
                    final String ext = ".ecc";
                    String filePath = selectedFile.getPath();
                    int extIndex = filePath.lastIndexOf(".");
                    String newPath = (extIndex > -1 ? filePath.substring(0, extIndex) : filePath) + ext;
                    System.out.println("resultado: " + Arrays.toString(result));
                    Files.write(Paths.get(newPath), result);
                    JOptionPane.showMessageDialog(null, "Codificação concluída com sucesso");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.exit(0);
    }
}