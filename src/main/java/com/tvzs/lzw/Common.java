package com.tvzs.lzw;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class Common {

    public static int getBitLengthFromSize(int size) {
        return (int) Math.ceil(Math.log(size) / Math.log(2));
    }

    public static String getCodeFromOutput(List<Integer> output, int bitsNeeded) {
        StringBuilder code = new StringBuilder();
        for (Integer s : output) {
            String binaryNoLeadingZero = Integer.toBinaryString(s);
            code.append(StringUtils.leftPad(binaryNoLeadingZero, bitsNeeded, '0'));
        }
        return code.toString();
    }

    public static int getIndexFromBitcode(String code) {
        return Integer.parseInt(code, 2);
    }
}
