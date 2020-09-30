import crc.CRC8;
import crc.InvalidCRC;
import hamming.Hamming;

import java.util.ArrayList;

public class TratamentoRuido {

    public byte[] addNoiseTreatment(byte[] data){
        ArrayList<Byte> resultBytes = new ArrayList<>();
        resultBytes.add(data[0]);
        resultBytes.add(data[1]);

        byte calculatedCrc = CRC8.calc(getDataForCrc(data));
        resultBytes.add(calculatedCrc);

        ArrayList<Byte> hammingResult = Hamming.encode(data);
        resultBytes.addAll(3, hammingResult);

        for(int i = 2; i < data.length; i++){
            resultBytes.add(data[i]);
        }

        byte[] result = new byte[resultBytes.size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = resultBytes.get(i);
        }
        return result;
    }

    public byte[] checkNoiseTreatment(byte[] data) throws InvalidCRC {
        ArrayList<Byte> resultBytes = new ArrayList<>();
        resultBytes.add(data[0]);
        resultBytes.add(data[1]);

        byte calculatedCrc = CRC8.calc(getDataForCrc(data));
        if(calculatedCrc != data[2]) {
            throw new InvalidCRC("O arquivo está corrompido!");
        }

        ArrayList<Byte> hammingResult = Hamming.decode(data);
        resultBytes.addAll(3, hammingResult);

        //começa no 3 pra pular o crc
        for(int i = 3; i < data.length; i++){
            resultBytes.add(data[i]);
        }

        byte[] result = new byte[resultBytes.size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = resultBytes.get(i);
        }
        return result;
    }

    private byte[] getDataForCrc(byte[] data){
        byte [] dataForCrc = new byte[2];
        System.arraycopy(data, 0, dataForCrc, 0, 2);
        return dataForCrc;
    }

}
