package model;

public class IllustImage {


    /**
     * urls : {"thumb_mini":"https://i.pximg.net/c/128x128/img-master/img/2019/10/12/01/32/36/77235380_p0_square1200.jpg","small":"https://i.pximg.net/c/540x540_70/img-master/img/2019/10/12/01/32/36/77235380_p0_master1200.jpg","regular":"https://i.pximg.net/img-master/img/2019/10/12/01/32/36/77235380_p0_master1200.jpg","original":"https://i.pximg.net/img-original/img/2019/10/12/01/32/36/77235380_p0.jpg"}
     * width : 2726
     * height : 1600
     */

    private UrlsBean urls;
    private int width;
    private int height;

    public UrlsBean getUrls() {
        return urls;
    }

    public void setUrls(UrlsBean urls) {
        this.urls = urls;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public static class UrlsBean {
        /**
         * thumb_mini : https://i.pximg.net/c/128x128/img-master/img/2019/10/12/01/32/36/77235380_p0_square1200.jpg
         * small : https://i.pximg.net/c/540x540_70/img-master/img/2019/10/12/01/32/36/77235380_p0_master1200.jpg
         * regular : https://i.pximg.net/img-master/img/2019/10/12/01/32/36/77235380_p0_master1200.jpg
         * original : https://i.pximg.net/img-original/img/2019/10/12/01/32/36/77235380_p0.jpg
         */

        private String thumb_mini;
        private String small;
        private String regular;
        private String original;

        public String getThumb_mini() {
            return thumb_mini;
        }

        public void setThumb_mini(String thumb_mini) {
            this.thumb_mini = thumb_mini;
        }

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getRegular() {
            return regular;
        }

        public void setRegular(String regular) {
            this.regular = regular;
        }

        public String getOriginal() {
            return original;
        }

        public void setOriginal(String original) {
            this.original = original;
        }
    }
}
