import crc.CRC8;
import hamming.Hamming;

public class TratamentoRuido {

    public byte[] addNoiseTreatment(byte[] data){
        //CRC.checksum(getDataForCrc(data));
        Hamming.encode(data);
        return null;
    }

    private byte[] getDataForCrc(byte[] data){
        byte [] dataForCrc = new byte[0];
        for(int i = 0; i < 2; i++){
            dataForCrc[i] = data[i];
        }
        return dataForCrc;
    }

}
