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
                    System.out.println("bit position: "+bitPosition);
                    // calcula o hamming
                    resultBytes.add(reverseBitsOfByte(calcHamming(codeword)));
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
            // calcula apenas os 4 ultimos
            if(index == data.length-1) {
                codeword.clear();
                for (int i = 3; i >= 0; i--) {
                    if(bits.get(i)) {
                        codeword.set(3-i);
                    }
                }
                resultBytes.add(reverseBitsOfByte(calcHamming(codeword)));
            }
        }

        System.out.println("resultado hamming: " + resultBytes.toString());

        return resultBytes;
    }

    public static ArrayList<Byte> decode(byte[] data) {
        ArrayList<Byte> resultBytes = new ArrayList<>();
        BitSet codeword = new BitSet();
        int bitPosition = 0;
        BitSet bitsIniciais, bitsFinais, bitsDecoded = BitSet.valueOf( new long[] {0});
            
        for(byte b : data) {
            // b = valor inteiro do char
            System.out.println("valor char = " + b);
            System.out.println("byte = " + Integer.toBinaryString(b));
        }
        
        // para cada bit, exceto o cabeçalho
        for(int index = 3; index < data.length; index++) {
            BitSet bits = BitSet.valueOf(new long[] { data[index] });
            
            System.out.println("index: "+index);
            System.out.println("bit position: "+bitPosition);
            System.out.println("bits: "+bits.toString());
            // até 4 bits, vai registrando os bits no codeword
            for(int i = 7; i >= 0; i--) {
                if(bits.get(i)) {
                    codeword.set(bitPosition);
                }
                System.out.println("i: "+i+" codeword: "+codeword.toString());
                bitPosition++;
            } 

            if(bitPosition == 8) {
                // salva o resultado no resultBytes, e recomeça
                if(index%2==1) { // 4 bits iniciais
                    bitsIniciais = checkHamming(codeword);
                    for(int j=0; j<4; j++) {
                        if(bitsIniciais.get(j)) {
                            bitsDecoded.set(j);
                        }
                    }
                    System.out.println("bits decoded: "+bitsDecoded.toString());
                    System.out.println("bits iniciais: "+bitsIniciais.toString());
                    bitsIniciais.clear();
                } else { // 4 bits finais
                    bitsFinais = checkHamming(codeword);
                    for(int j=0; j<4; j++) {
                        if(bitsFinais.get(j)) {
                            bitsDecoded.set(j+4);
                        }
                    }
                    System.out.println("bits decoded: "+bitsDecoded.toString());
                    System.out.println("bits finais: "+bitsFinais.toString());
                    byte decoded = !bitsDecoded.isEmpty() ? bitsDecoded.toByteArray()[0] : 0;
                    System.out.println("decoded: "+decoded);
                    System.out.println("decoded invertido: "+reverseBitsOfByte(decoded));
                    System.out.println("---------------------------------------");
                    resultBytes.add(reverseBitsOfByte(decoded));
                    bitsFinais.clear();
                    bitsDecoded.clear();
                }
                bitPosition = 0;
                codeword.clear();
            }
        }

        System.out.println("resultado hamming: " + resultBytes.toString());

        return resultBytes;
    }

    private static BitSet checkHamming(BitSet codeword) {
        BitSet result = BitSet.valueOf( new long[] {0});
        // forçar erro
        //codeword.flip(0); // no 1o
        //codeword.flip(1); // no 2o
        codeword.flip(2); // no 3o
        //codeword.flip(3); // no 4o
        // calcula o hamming para o codeword, detecta erros
        for(int i=0; i<4; i++) {
            if(codeword.get(i)) {
                result.set(i);
            }
        }
        
        System.out.println(">> result: "+result.toString());

        //1st hamming code
        setHammingCode(result, 0, 2, 1, 4);

        //2nd hamming code
        setHammingCode(result, 1, 2, 3, 5);

        //3rd hamming code
        setHammingCode(result, 0, 2, 3, 6);

        System.out.println(">>> result: "+result.toString());

        if(result.get(4) != codeword.get(4) && result.get(5) != codeword.get(5) && result.get(6) != codeword.get(6)) {
            result.flip(2);
            System.out.println("Ruído corrigido no 3o bit!");
        } 

        else if(result.get(4) != codeword.get(4) && result.get(6) != codeword.get(6)) {
            result.flip(0);
            System.out.println("Ruído corrigido no 1o bit!");
        }

        else if(result.get(4) != codeword.get(4) && result.get(5) != codeword.get(5)) {
            result.flip(1);
            System.out.println("Ruído corrigido no 2o bit!");
        }

        else if(result.get(5) != codeword.get(5) && result.get(6) != codeword.get(6)) {
            result.flip(3);
            System.out.println("Ruído corrigido no 4o bit!");
        }

        System.out.println(">>>> result: "+result.toString());

        return result;
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

    private static byte reverseBitsOfByte(byte inByte) {
        int intSize = 8;
        byte outByte = 0;
        for (int position=intSize-1; position > 0; position--) {
            outByte += ((inByte&1)<<position);
            inByte >>= 1;
        }
        return outByte;
    }
}
