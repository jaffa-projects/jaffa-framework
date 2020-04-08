package org.jaffa.util;

import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class CasExclusionFilterTest {
    @Test
    public void testEncodingPathInfo() {
        String pathInfo = "/api/";
        try {
            String encoded = URLEncoder.encode(pathInfo, StandardCharsets.UTF_8.toString());
            String decoded = URLDecoder.decode(encoded, StandardCharsets.UTF_8.toString());
            Assert.assertEquals(pathInfo, decoded);

            pathInfo = "/api/ddx/requestPendingSummary/MLM1";
            encoded = URLEncoder.encode(pathInfo, StandardCharsets.UTF_8.toString());
            decoded = URLDecoder.decode(encoded, StandardCharsets.UTF_8.toString());
            Assert.assertEquals(pathInfo, decoded);

            pathInfo = "/api/gct/metadata?_wadl";
            encoded = URLEncoder.encode(pathInfo, StandardCharsets.UTF_8.toString());
            decoded = URLDecoder.decode(encoded, StandardCharsets.UTF_8.toString());
            Assert.assertEquals(pathInfo, decoded);

            pathInfo = "/api/gct/test abc.pdf";
            encoded = URLEncoder.encode(pathInfo, StandardCharsets.UTF_8.toString());
            decoded = URLDecoder.decode(encoded, StandardCharsets.UTF_8.toString());
            Assert.assertEquals(pathInfo, decoded);

        } catch (UnsupportedEncodingException e) {
            Assert.fail(e.getMessage());
        }
    }
}
