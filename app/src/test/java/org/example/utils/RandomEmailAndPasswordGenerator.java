package org.example.utils;

public class RandomEmailAndPasswordGenerator {
    public String generateRandomEmail() {
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder email = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int index = (int) (Math.random() * characters.length());
            email.append(characters.charAt(index));
        }
        email.append("@example.com");
        return email.toString();
    }

    public String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            int index = (int) (Math.random() * characters.length());
            password.append(characters.charAt(index));
        }
        return password.toString();
    }
}
