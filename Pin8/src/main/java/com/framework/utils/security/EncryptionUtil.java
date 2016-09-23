package com.framework.utils.security;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

/**
 * @author JavaDigest
 * 
 */
public class EncryptionUtil {

	private static final String RSA = "RSA";
	private static final String AES = "AES";

	private static final String separator = java.io.File.separator.indexOf("/") != -1 ? "/": "\\";
	private static final String RSA_PRIVATE_KEY_FILE = separator+"security"+separator+"pkcs8_private_key.pem";
	private static final String RSA_PUBLIC_KEY_FILE = separator+"security"+separator+"public_key.der";
	private static final String AES_KEY_FILE = separator+"security"+separator+"aes_key.pem";
	
	private static PublicKey RSAPublicKey = null;
	private static PrivateKey RSAPrivateKey = null;
	private static SecretKey AESKey = null;
	
	private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

	private static final int SALT_BYTE_SIZE = 24;
	private static final int HASH_BYTE_SIZE = 24;
	private static final int PBKDF2_ITERATIONS = 1000;
    
	private static void generateKey() {
		Base64 base = new Base64();
		try {

			final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(RSA);
			keyGen.initialize(1024);
			final KeyPair key = keyGen.generateKeyPair();
			RSAPublicKey = key.getPublic();
			RSAPrivateKey = key.getPrivate();

			File privateKeyFile = new File(RSA_PRIVATE_KEY_FILE);
			File publicKeyFile = new File(RSA_PUBLIC_KEY_FILE);

			// Create files to store public and private key
			if (privateKeyFile.getParentFile() != null) {
				privateKeyFile.getParentFile().mkdirs();
			}
			privateKeyFile.createNewFile();

			if (publicKeyFile.getParentFile() != null) {
				publicKeyFile.getParentFile().mkdirs();
			}
			publicKeyFile.createNewFile();

			// Saving key in a file
			FileUtils.writeStringToFile(publicKeyFile, base.encodeToString(RSAPublicKey.getEncoded()));
			FileUtils.writeStringToFile(privateKeyFile, base.encodeToString(RSAPrivateKey.getEncoded()));
			
			
            final KeyGenerator kgen = KeyGenerator.getInstance("AES");  
            kgen.init(128, new SecureRandom()); 
            AESKey = kgen.generateKey();
         // Saving the Private key in a file
			File aesKeyFile = new File(AES_KEY_FILE);
			if (aesKeyFile.getParentFile() != null) {
				aesKeyFile.getParentFile().mkdirs();
			}
			aesKeyFile.createNewFile();
			FileUtils.writeStringToFile(aesKeyFile, base.encodeToString(AESKey.getEncoded()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private static boolean loadkey() throws InvalidKeyException {
		
		if(RSAPublicKey != null && RSAPrivateKey != null && AESKey != null){
			return true;
		}
		File privateKey;
		File publicKey;
		File aesKeyFile;
		try {
			privateKey = new ClassPathResource(RSA_PRIVATE_KEY_FILE).getFile();
			publicKey = new ClassPathResource(RSA_PUBLIC_KEY_FILE).getFile();
			aesKeyFile = new ClassPathResource(AES_KEY_FILE).getFile();
		} catch (IOException e1) {		
			throw new InvalidKeyException("No key file exists");
		}
		
		try {
			String keyStr = FileUtils.readFileToString(publicKey);
			Base64 decoder = new Base64();
			byte[] buffer = decoder.decode(keyStr);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			RSAPublicKey = keyFactory.generatePublic(keySpec);
							
			keyStr = FileUtils.readFileToString(privateKey);
			buffer = decoder.decode(keyStr);
			PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(buffer);
			RSAPrivateKey = keyFactory.generatePrivate(privateKeySpec);
			
			
			keyStr = FileUtils.readFileToString(aesKeyFile);
			buffer = decoder.decode(keyStr);
			AESKey = new SecretKeySpec(buffer,"AES");
		} catch (Exception e) {
			e.printStackTrace();
			throw new InvalidKeyException("Can't load keys",e);	
		}
        
		return true;
	}
	
	/**
	 * Encrypt the plain text using public key.
	 * 
	 * @param text
	 *            : original plain text
	 * @return Encrypted text
	 * @throws GeneralSecurityException 
	 * @throws java.lang.Exception
	 */
	public static String encryptByAES(String text) throws GeneralSecurityException {
		
		byte[] cipherText = null;
		try {			
			loadkey();
			final Cipher cipher = Cipher.getInstance(AES);
			cipher.init(Cipher.ENCRYPT_MODE, AESKey);
			cipherText = cipher.doFinal(text.getBytes());
		} catch (GeneralSecurityException e) {
			throw e;
		}
		return new Base64().encodeToString(cipherText);
	}

	/**
	 * Decrypt text using private key.
	 * 
	 * @param text
	 *            :encrypted text
	 * @return plain text
	 * @throws GeneralSecurityException 
	 * @throws java.lang.Exception
	 */
	public static String decryptByAES(byte[] text) throws GeneralSecurityException {
		byte[] dectyptedText = null;
		try {
			loadkey();
			final Cipher cipher = Cipher.getInstance(AES);
			cipher.init(Cipher.DECRYPT_MODE, AESKey);
			dectyptedText = cipher.doFinal(text);

		} catch (GeneralSecurityException e) {
			throw e;
		}

		return new String(dectyptedText);
	}
	

	public static String decryptByAES(String encrytedText) throws GeneralSecurityException {
		return decryptByAES(new Base64().decode(encrytedText));
	}

	/**
	 * Encrypt the plain text using public key.
	 * 
	 * @param text
	 *            : original plain text
	 * @return Encrypted text
	 * @throws GeneralSecurityException 
	 * @throws java.lang.Exception
	 */
	public static String encryptByRSA(String text) throws GeneralSecurityException {
		byte[] cipherText = null;
		byte[] rawText = text.getBytes();
		StringBuffer sb = new StringBuffer();
		try {
			loadkey();
			final Cipher cipher = Cipher.getInstance(RSA);			
			cipher.init(Cipher.ENCRYPT_MODE, RSAPublicKey);
			Base64 base = new Base64();
			
			int BLOCK_SIZE = 128 - 11;
            for (int i = 0; i < rawText.length; i += BLOCK_SIZE) {
                int leftBytes = rawText.length - i;
                int length = (leftBytes <= BLOCK_SIZE) ? leftBytes : BLOCK_SIZE;
                cipherText = cipher.doFinal(rawText, i, length);
                sb.append(base.encodeToString(cipherText));
            }
		} catch (GeneralSecurityException e) {
			throw e;
		}
		return sb.toString();
	}

	/**
	 * Decrypt text using private key.
	 * 
	 * @param text
	 *            :encrypted text
	 * @return plain text
	 * @throws java.lang.Exception
	 */
	public static String decryptByRSA(byte[] rawText) throws GeneralSecurityException {		

		byte[] cipherText = null;
		StringBuffer sb = new StringBuffer();
			try {
				loadkey();
				final Cipher cipher = Cipher.getInstance(RSA);			
				cipher.init(Cipher.DECRYPT_MODE, RSAPrivateKey);
				
				int BLOCK_SIZE = 128 - 11;
				for (int i = 0; i < rawText.length; i += BLOCK_SIZE) {
				    int leftBytes = rawText.length - i;
				    int length = (leftBytes <= BLOCK_SIZE) ? leftBytes : BLOCK_SIZE;
				    cipherText = cipher.doFinal(rawText, i, length);
				    sb.append(new String(cipherText));
				}
			} catch (GeneralSecurityException e) {
				throw e;
			}
		return sb.toString();
	}

	public static String decryptByRSA(String encrytedText) throws GeneralSecurityException {
	
		return decryptByRSA(new Base64().decode(encrytedText));
	}
	
	public static String decryptByRSAwithSalt(String encrytedText) throws GeneralSecurityException {
		String plainText = decryptByRSA(new Base64().decode(encrytedText));
		String[] strings = plainText.split(",");
		return strings[0];
	}
	
	public static String hashPassword(String password,String salt) throws GeneralSecurityException
	{
		byte[] dectyptedText = null;
		
		try {
			PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), PBKDF2_ITERATIONS, HASH_BYTE_SIZE * 8);
			SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
			dectyptedText = skf.generateSecret(spec).getEncoded();
		} catch (GeneralSecurityException e) {
			throw e;
		}
		return new Base64().encodeToString(dectyptedText);
	}
	
	public static String generateSalt()
	{
		SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);
        
        return new Base64().encodeToString(salt);
	}
	
	public static String generateInvitationCode() throws GeneralSecurityException
	{
		byte[] dectyptedText = null;
		
		try {
			String time = Long.toString(System.currentTimeMillis());
			PBEKeySpec spec = new PBEKeySpec(time.toCharArray(),"12345678".getBytes(), PBKDF2_ITERATIONS, HASH_BYTE_SIZE *2);
			SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
			dectyptedText = skf.generateSecret(spec).getEncoded();
		} catch (GeneralSecurityException e) {
			throw e;
		}
		return new Base64().encodeToString(dectyptedText);
	}

	/**
	 * Test the EncryptionUtil
	 */
	public static void main(String[] args) {

		try {

			final String originalText = "654321,salt5555";
			
			String encrytedByRSA = encryptByRSA(originalText);
			String decrytedByRSA = decryptByRSA(encrytedByRSA);
			System.out.println("Original=" + originalText);
			System.out.println("EncryptedByRSA=" + encrytedByRSA);
			System.out.println("DecryptedByRSA=" + decrytedByRSA);
			

			String encrytedByAES = encryptByAES(originalText);
			String decrytedByAES = decryptByAES(encrytedByAES);
			System.out.println("Original=" + originalText);
			System.out.println("EncryptedByAES=" + encrytedByAES);
			System.out.println("DecryptedByAES=" + decrytedByAES);

			String salt = generateSalt();
			String hash1 = hashPassword(originalText,salt);
			String hash2 = hashPassword(originalText,salt);

			System.out.println("Original=" + originalText);
			System.out.println("Salt=" + salt);
			System.out.println("hashPassword1=" + hash1);
			System.out.println("hashPassword1=" + hash2);
			
			String password = "KjPczPxREAvL5DQG6YiXgI63Tsa714Ie9jv3PoajpSYSk9tc5U386dM9Rsr0HGeCYSLe/YGqAaq1FGNt5dORLQ==";
			//String password = "CRhpyCzu0Y7PtaQic67rUy4LBAtGH8OITKnyENnsq1i9MMr0A+glt3SMNVmz0DQ0jrklqNpFcOy7EaZYu/rI6yqm+QDlJaJpc2iicu5IVM5ZH6KbsE/ZWCNVjMCBEw==?skZHyovK2sVRx74oma6skQ5f2goPp5LhPDrtaogTYHQ=";
			String passwordTest = decryptByRSA(password);
			System.out.println("password=" + password);
			System.out.println("passwordTest=" + passwordTest);
			
			for(int i=0;i<10;i++){
				long time = System.currentTimeMillis();
				System.out.println("Invitation Code ="+time +":" + generateInvitationCode());
			}
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}