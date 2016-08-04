package security;

import java.util.Random;


public class MapleSecurity {

	private static final byte key[] = {0x13, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00, 0x06, 0x00, 0x00, 0x00, (byte) 0xB4, 0x00, 0x00, 0x00, 0x1B, 0x00, 0x00, 0x00, 0x0F, 0x00, 0x00, 0x00, 0x33, 0x00, 0x00, 0x00, 0x52, 0x00, 0x00, 0x00};
    //private static final byte ivRecv[] = {70, 114, 122, 82};
    //private static final byte ivSend[] = {82, 48, 120, 115};

    
    public static byte[] getAESKey(){
    	return key;
    }
    
    public static String getRandomMAC(){
    	Random rand = new Random();
        byte[] macAddr = new byte[6];
        rand.nextBytes(macAddr);

        macAddr[0] = (byte)(macAddr[0] & (byte)254);  //zeroing last 2 bytes to make it unicast and locally adminstrated

        StringBuilder sb = new StringBuilder(18);
        for(byte b : macAddr){

            if(sb.length() > 0)
                sb.append(":");

            sb.append(String.format("%02x", b));
        }


        return sb.toString();
    }

	public static String getRandomHWID() {
		Random r = new Random();
		StringBuffer buf = new StringBuffer();
		buf.append("FUCK_");
		for(int i = 0;i < 8;i++){
			buf.append(r.nextInt(10));
		}
		buf.append("_THE_RULES");
		return buf.toString();
	}
	
}
