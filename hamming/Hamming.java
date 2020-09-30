package hamming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;

public class Hamming {

    public static ArrayList<Byte> encode(byte[] data) {
        ArrayList<Byte> resultBytes = new ArrayList<>();
        BitSet codeword = new BitSet();
        int bitPosition = 0;

        for(byte b : data) {
            // b = valor inteiro do char
            System.out.println("valor char = " + b);
            System.out.println("byte = " + Integer.toBinaryString(b));
        }
        
        // para cada bit, exceto o cabeçalho
        for(int index = 2; index < data.length; index++) {
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

        byte[] result = new byte[resultBytes.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = resultBytes.get(i);
        }

        System.out.println("resultado: " + Arrays.toString(result));

        return resultBytes;
    }

    private static byte calcHamming(BitSet codeword) {
        System.out.println("codeword na entrada");
        for(int i=0; i<8; i++) {
            System.out.println(codeword.get(i));
        }

        //1st hamming code
        setHammingCode(codeword, 0, 2, 1, 4);

        //2nd hamming code
        setHammingCode(codeword, 1, 2, 3, 5);

        //3rd hamming code
        setHammingCode(codeword, 0, 2, 3, 6);

        System.out.println("codeword na saída");
        for(int i=0; i<8; i++) {
            System.out.println(codeword.get(i));
        }

        byte coded = !codeword.isEmpty() ? codeword.toByteArray()[0] : 0;
        System.out.println("byte na saída: "+coded);
        return coded;
    }

    private static void setHammingCode(BitSet codeword, int index1, int index2, int index3, int indexToSet){
        int count = 0;
        if(codeword.get(index1))
            count++;
        if(codeword.get(index2))
            count++;
        if(codeword.get(index3))
            count++;

        if(count%2 != 0){
            codeword.set(indexToSet);
        }
    }

}
