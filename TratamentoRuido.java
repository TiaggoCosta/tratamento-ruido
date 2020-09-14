import crc.CRC;
import hamming.Hamming;

public class TratamentoRuido {

    public byte[] addNoiseTreatment(byte[] data){
        CRC.checksum(getDataForCrc(data));
        Hamming.encode(data);
        return null;
    }

    private byte[] getDataForCrc(byte[] data){
        byte [] dataForCrc = new byte[0];
        for(int i = 0; i < 2; i++){
            dataForCrc[i] = data[i];
        }
        dataForCrc[2] = (byte)0x00;
        return dataForCrc;
    }

}
