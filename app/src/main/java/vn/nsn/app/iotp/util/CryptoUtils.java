package vn.nsn.app.iotp.util;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by MyPC on 27/11/2018.
 */

public class CryptoUtils {
    private static byte[] hash(String stringToHash) throws NoSuchAlgorithmException {

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        return messageDigest.digest(stringToHash.getBytes());
    }

    public static String getChallengeCode(String detailBase64, String sessionCode) throws NoSuchAlgorithmException {
//        String hashString = Hex.encodeHexString(CryptoUtils.hash(detailBase64)).replaceAll("(\r|\n)", "").trim();
        String hashString = new String(Hex.encodeHex(CryptoUtils.hash(detailBase64)));
        hashString = sessionCode + hashString;
        hashString = hashString.replaceAll("(\r|\n)", "").trim();
//        char[] hasStringChars = hashString.toCharArray();
//        StringBuilder result = new StringBuilder("");
//        for (char hashChar : hasStringChars) {
//            if (hashChar >= '0' || hashChar <= '9') {
//                result.append(hashChar);
//            }
//        }
        String result = hashString.replaceAll("[^0-9]", "");
        return result.trim();
    }

    public static int parseOtpMaxLength(String suite) {
        String[] spliter;


        spliter = suite.split(":", -1);

        if (spliter.length < 3)
            return 0;


        int questionLengthIndex = spliter[2].indexOf("QN");


        int questionLength = 0;
        try {
            questionLength = Integer.parseInt(spliter[2].substring(questionLengthIndex + 2, questionLengthIndex + 4));
        } catch (NumberFormatException ex) {
        }

        return questionLength;
    }


}
