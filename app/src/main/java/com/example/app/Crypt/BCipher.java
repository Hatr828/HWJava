package com.example.app.Crypt;

public class BCipher implements ICipher {

    @Override
    public String encode(String input) {
        StringBuilder encoded = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                encoded.append((char) ('Z' - c + 'A'));
            } else if (c >= 'a' && c <= 'z') {
                encoded.append((char) ('z' - c + 'a'));
            } else {
                encoded.append(c);
            }
        }
        return encoded.toString();
    }

    @Override
    public String decode(String input) {
        StringBuilder decoded = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                decoded.append((char) ('Z' - c + 'A'));
            } else if (c >= 'a' && c <= 'z') {
                decoded.append((char) ('z' - c + 'a'));
            } else {
                decoded.append(c);
            }
        }
        return decoded.toString();
    }
}
