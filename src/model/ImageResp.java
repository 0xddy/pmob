package model;

import java.util.List;

public class ImageResp {


    /**
     * error : false
     * message :
     * body : [{"urls":{"thumb_mini":"https://i.pximg.net/c/128x128/img-master/img/2019/10/12/01/32/36/77235380_p0_square1200.jpg","small":"https://i.pximg.net/c/540x540_70/img-master/img/2019/10/12/01/32/36/77235380_p0_master1200.jpg","regular":"https://i.pximg.net/img-master/img/2019/10/12/01/32/36/77235380_p0_master1200.jpg","original":"https://i.pximg.net/img-original/img/2019/10/12/01/32/36/77235380_p0.jpg"},"width":2726,"height":1600},{"urls":{"thumb_mini":"https://i.pximg.net/c/128x128/img-master/img/2019/10/12/01/32/36/77235380_p1_square1200.jpg","small":"https://i.pximg.net/c/540x540_70/img-master/img/2019/10/12/01/32/36/77235380_p1_master1200.jpg","regular":"https://i.pximg.net/img-master/img/2019/10/12/01/32/36/77235380_p1_master1200.jpg","original":"https://i.pximg.net/img-original/img/2019/10/12/01/32/36/77235380_p1.jpg"},"width":2378,"height":1500},{"urls":{"thumb_mini":"https://i.pximg.net/c/128x128/img-master/img/2019/10/12/01/32/36/77235380_p2_square1200.jpg","small":"https://i.pximg.net/c/540x540_70/img-master/img/2019/10/12/01/32/36/77235380_p2_master1200.jpg","regular":"https://i.pximg.net/img-master/img/2019/10/12/01/32/36/77235380_p2_master1200.jpg","original":"https://i.pximg.net/img-original/img/2019/10/12/01/32/36/77235380_p2.jpg"},"width":2490,"height":1600},{"urls":{"thumb_mini":"https://i.pximg.net/c/128x128/img-master/img/2019/10/12/01/32/36/77235380_p3_square1200.jpg","small":"https://i.pximg.net/c/540x540_70/img-master/img/2019/10/12/01/32/36/77235380_p3_master1200.jpg","regular":"https://i.pximg.net/img-master/img/2019/10/12/01/32/36/77235380_p3_master1200.jpg","original":"https://i.pximg.net/img-original/img/2019/10/12/01/32/36/77235380_p3.jpg"},"width":2800,"height":1600},{"urls":{"thumb_mini":"https://i.pximg.net/c/128x128/img-master/img/2019/10/12/01/32/36/77235380_p4_square1200.jpg","small":"https://i.pximg.net/c/540x540_70/img-master/img/2019/10/12/01/32/36/77235380_p4_master1200.jpg","regular":"https://i.pximg.net/img-master/img/2019/10/12/01/32/36/77235380_p4_master1200.jpg","original":"https://i.pximg.net/img-original/img/2019/10/12/01/32/36/77235380_p4.jpg"},"width":2000,"height":1200},{"urls":{"thumb_mini":"https://i.pximg.net/c/128x128/img-master/img/2019/10/12/01/32/36/77235380_p5_square1200.jpg","small":"https://i.pximg.net/c/540x540_70/img-master/img/2019/10/12/01/32/36/77235380_p5_master1200.jpg","regular":"https://i.pximg.net/img-master/img/2019/10/12/01/32/36/77235380_p5_master1200.jpg","original":"https://i.pximg.net/img-original/img/2019/10/12/01/32/36/77235380_p5.jpg"},"width":2552,"height":1600},{"urls":{"thumb_mini":"https://i.pximg.net/c/128x128/img-master/img/2019/10/12/01/32/36/77235380_p6_square1200.jpg","small":"https://i.pximg.net/c/540x540_70/img-master/img/2019/10/12/01/32/36/77235380_p6_master1200.jpg","regular":"https://i.pximg.net/img-master/img/2019/10/12/01/32/36/77235380_p6_master1200.jpg","original":"https://i.pximg.net/img-original/img/2019/10/12/01/32/36/77235380_p6.jpg"},"width":1722,"height":1080},{"urls":{"thumb_mini":"https://i.pximg.net/c/128x128/img-master/img/2019/10/12/01/32/36/77235380_p7_square1200.jpg","small":"https://i.pximg.net/c/540x540_70/img-master/img/2019/10/12/01/32/36/77235380_p7_master1200.jpg","regular":"https://i.pximg.net/img-master/img/2019/10/12/01/32/36/77235380_p7_master1200.jpg","original":"https://i.pximg.net/img-original/img/2019/10/12/01/32/36/77235380_p7.jpg"},"width":2500,"height":1600},{"urls":{"thumb_mini":"https://i.pximg.net/c/128x128/img-master/img/2019/10/12/01/32/36/77235380_p8_square1200.jpg","small":"https://i.pximg.net/c/540x540_70/img-master/img/2019/10/12/01/32/36/77235380_p8_master1200.jpg","regular":"https://i.pximg.net/img-master/img/2019/10/12/01/32/36/77235380_p8_master1200.jpg","original":"https://i.pximg.net/img-original/img/2019/10/12/01/32/36/77235380_p8.jpg"},"width":1600,"height":2400}]
     */

    private boolean error;
    private String message;
    private List<IllustImage> body;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<IllustImage> getBody() {
        return body;
    }

    public void setBody(List<IllustImage> body) {
        this.body = body;
    }

}
