package crc;

public class CRC {

    public static byte checksum(byte[] data) {
        short register = 0;
        short bitMask = 0;
        short polynomial = 0;
        register = data[0];

        for (int i = 1; i < data.length; i++) {
            register = (short)((register << 8) | (data[i] & 0x00ff));
            polynomial = (short)(0x0107 << 7);
            bitMask = (short)0x8000;

            while (bitMask != 0x0080) {
                if ((register & bitMask) != 0) {
                    register ^= polynomial;
                }
                polynomial = (short)((polynomial&0x0000ffff) >>> 1);
                bitMask = (short)((bitMask&0x0000ffff) >>> 1);
            }
        }

        return (byte)register;
    }

}
