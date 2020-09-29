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
        int first = 0;
        if(codeword.get(0))
            first++;
        if(codeword.get(2))
            first++;
        if(codeword.get(1))
            first++;

        if(first%2 == 0 || first == 0){
            //stays false
        } else{
            codeword.set(4);
        }

        //2nd hamming code
        int second = 0;
        if(codeword.get(1))
            second++;
        if(codeword.get(2))
            second++;
        if(codeword.get(3))
            second++;

        if(second%2 == 0 || second == 0){
            //stays false
        } else{
            codeword.set(5);
        }

        //3rd hamming code
        int third = 0;
        if(codeword.get(0))
            third++;
        if(codeword.get(2))
            third++;
        if(codeword.get(3))
            third++;

        if(third%2 == 0 || third == 0){
            //stays false
        } else{
            codeword.set(6);
        }

        System.out.println("codeword na saída");
        for(int i=0; i<8; i++) {
            System.out.println(codeword.get(i));
        }

        byte coded = !codeword.isEmpty() ? codeword.toByteArray()[0] : 0;
        System.out.println("byte na saída: "+coded);
        return coded;
    }

}
