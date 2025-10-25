package com.example.app.Crypt;

public class ACipher implements ICipher {

    @Override
    public String encode(String input) {
        StringBuilder encoded = new StringBuilder();
        for (char c : input.toCharArray()) {
            encoded.append((char) (c + 1));
        }
        return encoded.toString();
    }

    @Override
    public String decode(String input) {
        StringBuilder decoded = new StringBuilder();
        for (char c : input.toCharArray()) {
            decoded.append((char) (c - 1));
        }
        return decoded.toString();
    }

}
