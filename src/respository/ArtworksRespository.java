package respository;

import com.google.gson.Gson;
import http.Httpc;
import model.IllustImage;
import model.ImageResp;

import java.util.ArrayList;
import java.util.List;

public class ArtworksRespository {

    private static final String URL_ARTWORK = "https://www.pixiv.net/ajax/illust/$id/pages";

    private static Httpc httpc = new Httpc();

    public static List<IllustImage> getImageUrls(String illustId) {
        List<IllustImage> illustImages = new ArrayList<>();
        try {
            String url = URL_ARTWORK.replaceAll("\\$id", illustId);
            String html = httpc.get2str(url);
            if (html != null) {
                ImageResp imageResp = new Gson().fromJson(html, ImageResp.class);
                if (!imageResp.isError()) {
                    illustImages.addAll(imageResp.getBody());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return illustImages;
    }

}
