package id.gits.gitsmvvmkotlin.util;

import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import id.co.gits.gitsdriver.utils.GitsHelper;

public class StringEncryptionTools {

    private SecretKey key;
    private Cipher cipher;

    public StringEncryptionTools() throws Exception {
        init();
    }

    public void init() throws Exception {
        KeyGenerator keygenerator = KeyGenerator.getInstance(GitsHelper.Const.ENCRYPTION_ALGORITHM_NAME);
        SecretKey secretkey = keygenerator.generateKey();

        key = new SecretKeySpec(secretkey.getEncoded(), GitsHelper.Const.ENCRYPTION_ALGORITHM_NAME);
        cipher = Cipher.getInstance(GitsHelper.Const.ENCRYPTION_ALGORITHM_NAME);
    }

    public String encryptText(String message) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(message.getBytes());
        String encryptedString = bytesToHex(encrypted);

        return encryptedString;
    }

    public String decryptText(String encryptedString) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decrypted = cipher.doFinal(hexToBytes(encryptedString));
        String decryptedString = bytesToHex(decrypted);

        return decryptedString;
    }

    public static byte[] hexToBytes(String str) {
        if (str == null) {
            return null;
        } else if (str.length() < 2) {
            return null;
        } else {
            int len = str.length() / 2;
            byte[] buffer = new byte[len];
            for (int i = 0; i < len; i++) {
                buffer[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
            }
            return buffer;
        }
    }

    public static String bytesToHex(byte[] data) {
        if (data == null) {
            return null;
        } else {
            int len = data.length;
            String str = "";
            for (int i = 0; i < len; i++) {
                if ((data[i] & 0xFF) < 16)
                    str = str + "0" + Integer.toHexString(data[i] & 0xFF);
                else
                    str = str + Integer.toHexString(data[i] & 0xFF);
            }
            return str.toUpperCase();
        }
    }

}
