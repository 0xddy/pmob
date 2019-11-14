package http;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Httpc {
    public static boolean useProxy = false;
    public static  String mProxyIP = "192.168.123.179";
    public static  int mProxyPort = 1080;

    private static Map<String, String> mHeader = new HashMap<>();

    {
        mHeader.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1");
        mHeader.put("X-Real-IP", "220.181.38.148");
        mHeader.put("accept-language", "zh-CN,zh;q=0.9");
        mHeader.put("referer", "https://www.pixiv.net");
        mHeader.put("x-user-id", "39491470");
    }

    public static void addHeader(String key, String val) {
        mHeader.put(key, val);
    }

    public static void removeHeader(String key) {
        mHeader.remove(key);
    }

    public Document get(String url) {
        Document htmlDocument = null;
        try {
            String body = get2str(url);
            htmlDocument = Jsoup.parse(body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return htmlDocument;
    }

    public Document getUser(String url) {
        Document htmlDocument = null;
        try {
            String body = get2strNoCookie(url);
            htmlDocument = Jsoup.parse(body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return htmlDocument;
    }

    public String get2str(String url) {
        String body = null;
        try {
            Connection connection = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .method(Connection.Method.GET)
                    .headers(mHeader);
            if (useProxy) {
                connection.proxy(mProxyIP, mProxyPort);
            }
            body = connection.execute().body();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;
    }

    public String get2strNoCookie(String url) {
        String body = null;
        try {
            Map<String, String> header = new HashMap<>(mHeader);
            header.remove("Cookie");
            Connection connection = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .method(Connection.Method.GET)
                    .headers(header);
            if (useProxy) {
                connection.proxy(mProxyIP, mProxyPort);
            }
            body = connection.execute().body();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;
    }

    public String post2str(String url, String data) {
        String body = null;
        try {
            Connection connection = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .method(Connection.Method.POST);
            connection.data(data);
            connection.headers(mHeader);
            if (useProxy) {
                connection.proxy(mProxyIP, mProxyPort);
            }
            body = connection.execute().body();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;
    }

    public URLConnection buildConnection(String url) {

        URLConnection connection = null;
        try {
            if (useProxy) {
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(mProxyIP, mProxyPort));
                connection = new URL(url).openConnection(proxy);
            } else {
                connection = new URL(url).openConnection();
            }
            for (Iterator iterator = mHeader.entrySet().iterator(); iterator.hasNext(); ) {
                Map.Entry entry = (Map.Entry) iterator.next();
                connection.setRequestProperty((String) entry.getKey(), (String) entry.getValue());
            }
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
