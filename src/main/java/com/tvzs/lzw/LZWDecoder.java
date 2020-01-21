package com.tvzs.lzw;

import com.google.common.base.Splitter;

import java.util.*;
import java.util.stream.Collectors;

public class LZWDecoder {
    private List<String> codeTable;
    private List<String> output;
    private LinkedList<Integer> codes;
    private String input;

    public LZWDecoder() {
        codeTable = new ArrayList<>();
        output = new LinkedList<>();
    }

    public void decode(String input, String[] alphabet) {
        cleanUp();
        this.input = input;
        initialize(alphabet);
        System.out.println("Decoder cleaned up and initialized\n");
        actualDecoding();
        System.out.println("Final code table: " + codeTable.toString() + "\n" +
                "Final output: " + output.toString() + "\n");
    }

    private void cleanUp() {
        codeTable.clear();
        output.clear();
    }

    private void initialize(String[] alphabet) {
        Collections.addAll(codeTable, alphabet);
        getBitLengthAndCodes();
    }

    private void getBitLengthAndCodes() {
        String[] lengthAndData = input.split(" ");
        int length = Integer.parseInt(lengthAndData[0]);
        Splitter splitter = Splitter.fixedLength(length);
        List<String> bitcodeList = splitter.splitToList(lengthAndData[1]);
        codes = bitcodeList.stream()
                .map(this::getIndexFromBitcode)
                .collect(Collectors.toCollection(LinkedList::new));
        System.out.println("Length is " + length + "\n" +
                "List of bitcodes: " + bitcodeList.toString() + "\n" +
                "List of codes: " + codes.toString() + "\n");
    }

    private int getIndexFromBitcode(String code) {
        return Integer.parseInt(code, 2);
    }

    private void actualDecoding() {
        Integer charBuffer = codes.pop();
        String current = codeTable.get(charBuffer);
        output.add(current);
        System.out.println("First code is " + charBuffer + " which corresponds to " + current);
        charBuffer = codes.pop();
        System.out.println("Second code is " + charBuffer);
        boolean shouldRun = true;
        while (shouldRun) {
            String next;
            if (charBuffer < codeTable.size()) {
                next = codeTable.get(charBuffer);
                System.out.print("charBuffer is in codeTable, next is ");
            } else {
                next = current + current.charAt(0);
                System.out.print("charBuffer is NOT in codeTable, next is ");
            }
            System.out.println(next);
            output.add(next);
            String stringDebt = current + next.charAt(0);
            codeTable.add(stringDebt);
            current = next;
            System.out.println(stringDebt + " added to code table\n" +
                    "current is now: " + current + "\n");
            if (codes.isEmpty()) {
                shouldRun = false;
            } else {
                charBuffer = codes.pop();
                System.out.println("Reading next character, charBuffer is: " + charBuffer);
            }
        }
    }

}
