package utils;

import http.Httpc;

import java.io.File;
import java.net.URLConnection;

import static org.apache.commons.io.FileUtils.copyInputStreamToFile;

public class DownloadUtils {

    private static Httpc httpc = new Httpc();

    public static boolean downloadFile(String url, File outfile) {
        return _downloadFile(url, outfile, 0);
    }

    public static boolean _downloadFile(String url, File outfile, int count) {

        try {
            //获取输入流
            URLConnection connection = httpc.buildConnection(url);
            copyInputStreamToFile(connection.getInputStream(), outfile);
        } catch (Exception e) {
            e.printStackTrace();
            if (count < 3) {
                return _downloadFile(url, outfile, count + 1);
            }
        }
        return false;
    }

}
