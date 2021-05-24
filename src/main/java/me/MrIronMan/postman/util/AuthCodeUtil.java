package me.MrIronMan.postman.util;

import me.MrIronMan.postman.configuration.ConfigData;

import java.util.Random;

public class AuthCodeUtil {

    Random random = new Random();

    public final char[] CHARS = {'0','1', '2', '3', '4', '5', '6', '7', '8',
            '9','a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

    public String getAuthCode() {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < ConfigData.VERIFY_CODE_LENGTH; i++) {
            buffer.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return buffer.toString();
    }

}
