package hamming;

import java.util.ArrayList;
import java.util.BitSet;

public class Hamming {

    public static byte[] encode(byte[] data) {
        ArrayList<Byte> decoded = new ArrayList<>();
        BitSet codeword = new BitSet();
        int bitPosition = 0;
        
        // para cada bit, exceto o cabeçalho
        for(int index = 2; index < data.length; index++) {
            BitSet bits = BitSet.valueOf(new long[] { data[index] });
            
            System.out.println("bits: "+Integer.toBinaryString(bits.cardinality()));
            // até 4 bits, vai registrando os bits no codeword
            if(bitPosition < 4) {
                if(bits.get(index)) {
                    codeword.flip(bitPosition);
                }
                System.out.println("i: "+bitPosition+" codeword: "+codeword.toString());
                bitPosition++;
            } else {
                // calcula o hamming
                decoded.add(calcHamming(codeword));
                // salva o resultado no decoded, e recomeça
                bitPosition = 0;
            }
        }

        System.out.println("array: "+decoded.toString());
        return null;
    }

    private static byte calcHamming(BitSet codeword) {
        // para cada bit, soma bit(i) com bit(i+1)
        // se soma = impar(1), res=1
        // senao, res=0
        return 0;
    }

}
