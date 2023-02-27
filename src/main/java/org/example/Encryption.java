package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import javax.crypto.*;

public class Encryption {
    public void encryptor() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter a word to encrypt: ");
            String word = scanner.nextLine();

            Cipher cipher = Cipher.getInstance("AES");

            // Generate a random secret key
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128);
            SecretKey key = kgen.generateKey();

            // Encrypt the word using the cipher and the secret key
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(word.getBytes(StandardCharsets.UTF_8));

            // Write the cipher text to a file as a sequence of numbers
            File dir = new File("C:\\Game\\Penis");
            dir.mkdirs();
            File file = new File(dir, "cipher.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (byte b : encryptedBytes) {
                writer.write(Integer.toString(b & 0xFF));
                writer.write(" ");
            }
            writer.close();

            // Read the cipher text from the file and decrypt it
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String[] cipherTextStrings = reader.readLine().split(" ");
            byte[] cipherText = new byte[cipherTextStrings.length];
            for (int i = 0; i < cipherTextStrings.length; i++) {
                cipherText[i] = (byte) Integer.parseInt(cipherTextStrings[i]);
            }
            reader.close();

            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = cipher.doFinal(cipherText);
            String decryptedString = new String(decryptedBytes, StandardCharsets.UTF_8);

            // Print the decrypted text
            System.out.println("Decrypted word: " + decryptedString);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }
}