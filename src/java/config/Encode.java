package config;

import java.security.MessageDigest;
import org.apache.tomcat.util.codec.binary.Base64;

public class Encode {

    public static String toSHA1(String str) {
        String salt = "asjrlkmcoe&^%$^%%fsd&sfddsfsdfsdsd';das;f;fasq';eqw1jurVn";
        String salt1 = "gfdfvxcvbcndfb$%^&*fssdfewqw1jurVn";
        String result = null;

        str = str + salt + salt1;
        try {
            byte[] dataBytes = str.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            result = Base64.encodeBase64String(md.digest(dataBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
