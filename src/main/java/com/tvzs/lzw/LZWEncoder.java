package com.tvzs.lzw;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class LZWEncoder {
    private final double UTF_CHAR_SIZE = 16;
    private List<String> codeTable;
    private List<String> output;
    private LinkedList<String> charStream;
    private String charBuffer;
    private String previous;
    private String current;
    private String encodedString;
    private int encodedBitLength;
    private String[] alphabet;
    private int inputLength;

    public LZWEncoder() {
        codeTable = new ArrayList<>();
        output = new LinkedList<>();
    }

    public String getDecodableString() {
        return Integer.toString(encodedBitLength) + " " + encodedString;
    }

    public String[] getAlphabet() {
        return alphabet;
    }

    public double getEfficiency() {
        double inputSize = inputLength * UTF_CHAR_SIZE;
        int outputSize = encodedString.length() * encodedBitLength;
        return (outputSize / inputSize) * 100;
    }

    //This initializes the alphabet from the string
    public void encryptWithoutAlphabet(String input) {
        alphabet = Arrays.stream(input.split(""))
                .sorted()
                .distinct()
                .toArray(String[]::new);
        encryptWithAlphabet(input, alphabet);
    }

    public void encryptWithAlphabet(String input, String... alphabet) {
        if (input.isEmpty() || alphabet.length == 0) {
            System.out.println("Why would you do that?");
            return;
        }
        encrypt(input, alphabet);
    }

    private void encrypt(String input, String[] alphabet) {
        cleanUp();
        initializeFields(input, alphabet);
        while (!charStream.isEmpty()) {
            if (codeTable.contains(current)) {
                readNextCharacterAndUpdateTheOthers();
            } else {
                addStringToTableAndAddThePreviousStringToOutput();
            }
        }
        output.add(getCodeOfString(current));
        printTheOutput();
    }

    private void cleanUp() {
        codeTable.clear();
        output.clear();
    }

    private void initializeFields(String input, String[] alphabet) {
        Collections.addAll(codeTable, alphabet);
        System.out.println("Code table initialized as: " + codeTable);
        charStream = new LinkedList<>(Arrays.asList(input.split("")));
        charBuffer = charStream.pop();
        previous = "";
        current = String.valueOf(charBuffer);
        inputLength = input.length();
        System.out.println("Initial reading done, c is " + charBuffer + "\n" +
                "curr is " + current + "\n" +
                "Now come de LØØP\n");
    }

    private void readNextCharacterAndUpdateTheOthers() {
        charBuffer = charStream.pop();
        previous = current;
        current = previous + charBuffer;
        System.out.println("Code table already contains " + previous + "\n" +
                "Reading new character c: " + charBuffer + "\n" +
                "curr is now " + current + "\n");
    }

    private void addStringToTableAndAddThePreviousStringToOutput() {
        String codeOfPrev = getCodeOfString(previous);
        output.add(codeOfPrev);
        codeTable.add(current);
        System.out.println(previous + " (code: " + codeOfPrev + ") added to output\n" +
                current + " added to code table\n" +
                "curr is now " + charBuffer + "\n" +
                "Code table is: " + codeTable + "\n");
        current = charBuffer;
    }

    private String getCodeOfString(String s) {
        return String.valueOf(codeTable.indexOf(s));
    }

    private void printTheOutput() {
        System.out.println("Code table is: " + codeTable);
        System.out.println("Raw output is: " + output);
        //Due to floating point fuckery this may not always be correct
        encodedBitLength = (int) Math.ceil(Math.log(output.size()) / Math.log(2));
        encodedString = getCodeFromOutput(encodedBitLength);
        System.out.println("Bits needed: " + encodedBitLength + "\n" +
                "Encoded string: " + encodedString + "\n");
    }

    private String getCodeFromOutput(int bitsNeeded) {
        StringBuilder code = new StringBuilder();
        for (String s : output) {
            String binaryNoLeadingZero = Integer.toBinaryString(Integer.parseInt(s));
            code.append(StringUtils.leftPad(binaryNoLeadingZero, bitsNeeded, '0'));
        }
        return code.toString();
    }

}
