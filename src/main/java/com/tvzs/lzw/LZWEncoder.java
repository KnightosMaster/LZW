package com.tvzs.lzw;

import java.util.*;
import java.util.stream.Collectors;

import static com.tvzs.lzw.Common.getBitLengthFromSize;
import static com.tvzs.lzw.Common.getCodeFromOutput;

public class LZWEncoder extends Encoder {
    private List<String> codeTable;
    private List<String> outputList;
    private LinkedList<String> charStream;
    private String charBuffer;
    private String previous;
    private String current;

    public LZWEncoder() {
        codeTable = new ArrayList<>();
        outputList = new LinkedList<>();
    }

    @Override
    public String getDecodableString() {
        return encodedBitLength + " " + encodedString;
    }

    @Override
    protected void cleanUp() {
        codeTable.clear();
        outputList.clear();
    }

    @Override
    protected void initializeFields(String input) {
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

    @Override
    protected void doTheEncryption() {
        while (!charStream.isEmpty()) {
            if (codeTable.contains(current)) {
                readNextCharacterAndUpdateTheOthers();
            } else {
                addStringToTableAndAddThePreviousStringToOutput();
            }
        }
        outputList.add(getCodeOfString(current));
    }

    @Override
    protected void printTheOutput() {
        System.out.println("Code table is: " + codeTable);
        System.out.println("Raw output is: " + outputList);
        //Due to floating point fuckery this may not always be correct
        encodedBitLength = getBitLengthFromSize(outputList.size());
        List<Integer> outputIntegerList = outputList.stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        encodedString = getCodeFromOutput(outputIntegerList, encodedBitLength);
        System.out.println("Bits needed: " + encodedBitLength + "\n" +
                "Encoded string: " + encodedString + "\n");
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
        outputList.add(codeOfPrev);
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

}
