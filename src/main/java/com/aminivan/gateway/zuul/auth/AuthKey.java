package com.aminivan.gateway.zuul.auth;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import static java.rmi.server.LogStream.log;

public class AuthKey {
    public static String getKey() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        Calendar calendar = Calendar.getInstance();
        String current = formatter.format(calendar.getTime());
        String header = "com.kelola.mantra" + current;
        log("getkey "+md5(md5(header) + header).toString());
        return md5(md5(header) + header);
    }

    private static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
