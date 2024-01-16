package com.likelion.memoapp.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class EncodeUtil {
    public static String encodeJwtBearerToken(String token) {
        return URLEncoder.encode("Bearer " + token,
                StandardCharsets.UTF_8).replaceAll("\\+", "%20");
    }

    public static String decodeJwtBearerToken(String token) {
        return URLDecoder.decode(token, StandardCharsets.UTF_8).split(" ")[1];
    }
}
