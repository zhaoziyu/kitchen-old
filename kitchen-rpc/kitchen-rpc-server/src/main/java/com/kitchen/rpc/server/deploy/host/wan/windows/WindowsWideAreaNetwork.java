package com.kitchen.rpc.server.deploy.host.wan.windows;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Windows系统下，获取公网（WAN）IP地址的工具
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-04-20
 */
public class WindowsWideAreaNetwork {
    /**
     * 在Windows平台中获取广域网IP地址
     *
     * @return
     */
    public static String getWanIp(String ipProviderUrl) throws IOException {
        URL url = null;
        try {
            url = new URL(ipProviderUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        String htmlContent = fetchProviderHtml(url);
        return parseIp(htmlContent);
    }

    private static String parseIp(String html) {
        String ip = null;
        Pattern pattern = Pattern.compile("(\\d{1,3})[.](\\d{1,3})[.](\\d{1,3})[.](\\d{1,3})", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            ip = matcher.group(0);
        }
        return ip;
    }

    private static String fetchProviderHtml(URL url) throws IOException {
        String htmlContent = null;
        InputStream in = null;
        HttpURLConnection httpConn = null;

        try {
            httpConn = (HttpURLConnection) url.openConnection();

            HttpURLConnection.setFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows)"); // TODO

            in = httpConn.getInputStream();
            byte[] bytes = new byte[1024];

            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length && (numRead = in.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }
            htmlContent = new String(bytes, "UTF-8");
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                in.close();
                httpConn.disconnect();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return htmlContent;
    }
}
