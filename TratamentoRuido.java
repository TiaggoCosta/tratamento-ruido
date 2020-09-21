import crc.CRC8;
import hamming.Hamming;

import java.util.ArrayList;

public class TratamentoRuido {

    public byte[] addNoiseTreatment(byte[] data){
        ArrayList<Byte> resultBytes = new ArrayList<>();
        resultBytes.add(data[0]);
        resultBytes.add(data[1]);

        byte calculatedCrc = CRC8.calc(getDataForCrc(data));
        resultBytes.add(calculatedCrc);

//        ArrayList<Byte> hammingResult = Hamming.encode(data);
//        resultBytes.addAll(3, hammingResult);

        for(int i = 2; i < data.length; i++){
            resultBytes.add(data[i]);
        }

        byte[] result = new byte[resultBytes.size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = resultBytes.get(i);
        }
        return result;
    }

    public void removeNoiseTreatment(){
        //TBD
        //this method shall check CRC is equal - if not then stop process
        //this method shall check hamming codes and try to fix error if possible
    }

    private byte[] getDataForCrc(byte[] data){
        byte [] dataForCrc = new byte[2];
        for(int i = 0; i < 2; i++){
            dataForCrc[i] = data[i];
        }
        return dataForCrc;
    }

}
