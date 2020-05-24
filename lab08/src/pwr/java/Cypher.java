package pwr.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Cypher {
	private static SecretKeySpec secretKey;
    private static byte[] keyBytes;
    public static String CHARSET = "UTF-8";
    private static String encryptedName = "encrypted.txt";
    private static String decryptedName = "decrypted.txt";
    private static String toEncryptName = "TextToEncrypt.txt";
 
    private static void setKey(String newKey) 
    {
        try {
        	keyBytes = newKey.getBytes(CHARSET);
            keyBytes = Arrays.copyOf(keyBytes, 16); 
            secretKey = new SecretKeySpec(keyBytes, "AES");
        } 
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
 
    public static void encrypt(String key) 
    {
        try
        {
			System.out.println("Encrypting file...");
        	File inputFile = new File(toEncryptName);
        	File outputFile = new File(encryptedName);
        	FileInputStream finput = new FileInputStream(inputFile);
        	FileOutputStream foutput = new FileOutputStream(outputFile);
            setKey(key);
            Cipher cipher = Cipher.getInstance("AES/CTR/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));
            CipherInputStream cipheris = new CipherInputStream(finput, cipher);
            byte[] buffer = new byte[256];
            int bufferSize = cipheris.read(buffer);
            foutput.write(buffer, 0, bufferSize);
            finput.close();
            foutput.close();
            cipheris.close();
			System.out.println("File encrypted successfully as " + encryptedName);
        } 
        catch (Exception e) 
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
    }
 
    public static void decrypt(String key) 
    {
        try
        {	
			System.out.println("Decrypting file...");
        	File inputFile = new File(encryptedName);
        	File outputFile = new File(decryptedName);
        	FileInputStream finput = new FileInputStream(inputFile);
        	FileOutputStream foutput = new FileOutputStream(outputFile);
            setKey(key);
            Cipher cipher = Cipher.getInstance("AES/CTR/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));
            CipherOutputStream cipheros = new CipherOutputStream(foutput, cipher);
            byte[] buffer = new byte[256];
            int bufferSize = finput.read(buffer);
            cipheros.write(buffer, 0, bufferSize);
            finput.close();
            foutput.close();
            cipheros.close();
			System.out.println("File encrypted successfully to " + decryptedName);
        } 
        catch (Exception e) 
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
    }
}
