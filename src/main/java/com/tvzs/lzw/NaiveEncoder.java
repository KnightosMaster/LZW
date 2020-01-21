package com.tvzs.lzw;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.tvzs.lzw.Common.getCodeFromOutput;

public class NaiveEncoder extends Encoder {
    private final Map<String, Integer> characterCodeMap;
    private List<String> characters;

    public NaiveEncoder() {
        characterCodeMap = new HashMap<>();
    }

    @Override
    public String getDecodableString() {
        return encodedString;
    }

    @Override
    protected void cleanUp() {
        characterCodeMap.clear();
    }

    @Override
    protected void initializeFields(String input) {
        for (int i = 0; i < alphabet.length; i++) {
            String s = alphabet[i];
            characterCodeMap.put(s, i);
        }
        inputLength = input.length();
        encodedBitLength = Common.getBitLengthFromSize(alphabet.length);
        characters = Arrays.asList(input.split(""));

    }

    @Override
    protected void doTheEncryption() {
        List<Integer> outputIntegerList =
                characters.stream()
                .map(this::getCodeOfCharacter)
                .collect(Collectors.toList());
        encodedString = getCodeFromOutput(outputIntegerList, encodedBitLength);
    }

    @Override
    protected void printTheOutput() {
        System.out.println("Naive compression complete\n" +
                "Bits needed per character: " + encodedBitLength + "\n" +
                "Encoded string: " + encodedString + "\n");
    }

    private Integer getCodeOfCharacter(String s) {
        return characterCodeMap.get(s);
    }
}
