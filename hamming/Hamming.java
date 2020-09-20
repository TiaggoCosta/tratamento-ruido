package hamming;

import java.util.ArrayList;
import java.util.BitSet;

public class Hamming {

    public static byte[] encode(byte[] data) {
        ArrayList<Byte> resultBytes = new ArrayList<>();
        BitSet codeword = new BitSet();
        int bitPosition = 0;

        for(byte b : data) {
            // b = valor inteiro do char
            System.out.println("valor char = " + b);
            System.out.println("byte = " + Integer.toBinaryString(b));
        }
        
        // para cada bit, exceto o cabeçalho
        for(int index = 0; index < data.length; index++) {
            BitSet bits = BitSet.valueOf(new long[] { data[index] });
            
            System.out.println("index: "+index);
            System.out.println("bits: "+bits.toString());
            // até 4 bits, vai registrando os bits no codeword
            for(int i = 7; i >= 0; i--) {
                if(bitPosition == 4) {
                    // calcula o hamming
                    resultBytes.add(calcHamming(codeword));
                    // salva o resultado no resultBytes, e recomeça
                    bitPosition = 0;
                    codeword.clear();
                }
                if(bits.get(i)) {
                    codeword.set(bitPosition);
                }
                System.out.println("i: "+i+" codeword: "+codeword.toString());
                bitPosition++;
            } 
            resultBytes.add(calcHamming(codeword));
        }

        System.out.println("array: "+resultBytes.toString());
        byte[] result = new byte[resultBytes.size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = resultBytes.get(i);
        }
        return result;
    }

    private static byte calcHamming(BitSet codeword) {
        System.out.println("codeword na entrada");
        for(int i=0; i<8; i++) {
            System.out.println(codeword.get(i));
        }
        // para cada bit, soma bit(i) com bit(i+1)
        for(int i=0; i<3; i++) {
            // se soma = impar(1), res=1
            // senao, res=0
            if(codeword.get(i) && !codeword.get(i+1) || !codeword.get(i) && codeword.get(i+1)) {
                codeword.set(i+4);
            }
        }

        System.out.println("codeword na saída");
        for(int i=0; i<8; i++) {
            System.out.println(codeword.get(i));
        }
        byte[] coded = codeword.toByteArray();
        
        return coded[0];
    }

}
