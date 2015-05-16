package com.main.cloudapi.utils;

/**
 * Created by mirxak on 15.05.15.
 */
import com.main.cloudapi.entity.User;
import org.springframework.util.DigestUtils;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class UserHashPass {

    private static final int SALT_LENGTH = 20;
    private static final int MIN_PASSWORD_LENGTH=8;

    //<editor-fold desc="hashPass">

    private static final String magicWord1 = "fooMagic1";
    private static final String magicWord2 = "Magic2bar";

    public static String hashPass(String password) {
        try {
            password = magicWord1 + password + magicWord2;
            byte[] bytesOfMessage = password.getBytes("UTF-8");
            return DigestUtils.md5DigestAsHex(bytesOfMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String hashPass(String password, String salt) {
        try {
            password = magicWord1 + password + magicWord2 + salt;
            //   System.out.println("hashPass salt=" + salt);
            byte[] bytesOfMessage = password.getBytes("UTF-8");
            String res = DigestUtils.md5DigestAsHex(bytesOfMessage);
            // logger.debug("s={} h_pass={}", salt, res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static final SecureRandom r = new SecureRandom();

    public static String genSalt() {

        String result = null;
        byte[] crypted = null;
        int i = 0;
        try {
            while (result == null || !isValidUTF8(result.getBytes())) {
                byte[] salt = new byte[SALT_LENGTH];
                r.nextBytes(salt);
                try {
                    // MessageDigestPasswordEncoder encoder=new MessageDigestPasswordEncoder("SHA-1");
                    MessageDigest crypt = MessageDigest.getInstance("SHA-1");

                    crypt.reset();
                    crypt.update(new String(salt, "UTF-8").getBytes());
                    crypted = crypt.digest();
                    result = new String(crypted, "UTF-8");
                    result = result.replace('\u0000', ' ');
                } catch (Exception e) {
                    e.printStackTrace();
                    result = "salt";
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "salt2";
        }

        //logger.debug("salt={} counter={}", result, i);
        return result;

    }
    //</editor-fold>

    public static String genNumPass(int length){
        if(length<3){
            length=3;
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(genNumPart());
        }
        return result.toString();
    }



    public static String genPass(int length) {
        if(length<3){
            length=3;
        }

        StringBuilder result = new StringBuilder();
        boolean consist = false;
        while (!consist) {

            boolean numExist = false;
            boolean upperExist = false;
            boolean lowerExist = false;

            for (int i = 0; i < length; i++) {
                int part = r.nextInt(3);
                //logger.debug("part={}",part);
                switch (part) {
                    case 0:
                        result.append(genNumPart());
                        numExist = true;
                        break;
                    case 1:
                        result.append(genLowerPart());
                        lowerExist = true;
                        break;
                    case 2:
                        result.append(genUpperPart());
                        upperExist = true;
                        break;
                }
                consist = numExist && upperExist && lowerExist;
            }
            if (!consist) {
                result=new StringBuilder();
            }
        }

        return result.toString();
    }


    private static final Pattern PAS_VALID = Pattern.compile("((?=.*\\d)(?=.*[a-zа-я])(?=.*[A-ZА-Я]).{" + MIN_PASSWORD_LENGTH + ",})");

    public static boolean isPassValid(User user) {
        String pas = user.getPass();
        Matcher matcher = PAS_VALID.matcher(pas == null ? "" : pas);

        boolean good = matcher.matches();
        // logger.trace("u pass={} is_pass_valid={}", user.getPassword(),good);
        return good;
    }


    private static String genNumPart() {
        return Integer.toString(r.nextInt(10));
    }


    private static String genLowerPart() {
        return String.valueOf((char)(r.nextInt(26) + 'a'));
    }

    private static String genUpperPart() {
        return genLowerPart().toUpperCase();
    }

    public static void securePass(User user){
        String salt = UserHashPass.genSalt();
        user.setSalt(salt);
        user.setPass(UserHashPass.hashPass(user.getPass(), salt));
    }

    public static boolean isValidUTF8(final byte[] bytes) {

        try {
            Charset.availableCharsets().get("UTF-8").newDecoder().decode(ByteBuffer.wrap(bytes));

        } catch (CharacterCodingException e) {

            return false;
        }

        return true;
    }

}
