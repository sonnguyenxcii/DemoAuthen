package vn.nsn.app.ocb.util;

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
//        hashString += sessionCode;
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
}
