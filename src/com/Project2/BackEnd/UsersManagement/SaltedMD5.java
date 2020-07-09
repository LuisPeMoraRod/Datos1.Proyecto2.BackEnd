package com.Project2.BackEnd.UsersManagement;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class SaltedMD5 
{
	/**
	 * Public class. Creates encrypted password with MD5 algorithm
	 * @author Luis Pedro Morales Rodriguez
	 * @version 8/7/2020
	 */
	private String password;
	public SaltedMD5(String password) {
		this.password = password;
	}
	
	/**
	 * Hash password using MD5 algorithm with salt added for security
	 * @return securePassword : String
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 */
    public String getMD5() throws NoSuchAlgorithmException, NoSuchProviderException 
    {
        byte[] salt = getSalt();
       String securePassword = getSecurePassword(password, salt);
       return securePassword;
    }
     
    /**
     * Generates new encrypted password
     * @param passwordToHash : String
     * @param salt : byte
     * @return generatedPassword
     */
    private  String getSecurePassword(String passwordToHash, byte[] salt)
    {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(salt);
            //Get the hash's bytes 
            byte[] bytes = md.digest(passwordToHash.getBytes());
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
     
   /**
    * Add salt to hash password
    * @return salt : byte
    * @throws NoSuchAlgorithmException
    * @throws NoSuchProviderException
    */
    private  byte[] getSalt() throws NoSuchAlgorithmException, NoSuchProviderException
    {
        //Always use a SecureRandom generator
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        //Create array for salt
        byte[] salt = new byte[16];
        //Get a random salt
        sr.nextBytes(salt);
        //return salt
        return salt;
    }
}