package com.occydaboss.skyblock.util;

public class TextCase {
    public static String setCase(String message) {
        // stores each characters to a char array
        char[] charArray = message.toLowerCase().toCharArray();
        boolean foundSpace = true;

        for (int i = 0; i < charArray.length; i++) {

            // if the array element is a letter
            if (Character.isLetter(charArray[i])) {

                // check space is present before the letter
                if (foundSpace) {

                    // change the letter into uppercase
                    charArray[i] = Character.toUpperCase(charArray[i]);
                    foundSpace = false;
                }
            } else {
                // if the new character is not character
                foundSpace = true;
            }
        }

        return String.valueOf(charArray);
    }
}
