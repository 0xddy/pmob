package respository;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import http.Httpc;
import model.Illust;
import model.PickUp;
import org.fusesource.jansi.Ansi;
import org.jsoup.nodes.Document;
import utils.C;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 用户提供者
 */
public class PMRespository {

    public static final String URL_MEMBER = "https://www.pixiv.net/member.php?id=";
    private static final String URL_MEMBER_INFO = "https://www.pixiv.net/ajax/user/$id/profile/top";

    private static final String URL_ALL_Illusts = "https://www.pixiv.net/ajax/user/$id/profile/all";

    private static final String URL_ILLUST_LIST = "https://www.pixiv.net/ajax/user/$id/profile/illusts?";

    private static final String URL_ILLUSTS = "https://www.pixiv.net/ajax/user/$id/profile/illusts";


    private static final int SIZE = 100;

    private static Httpc httpc = new Httpc();



    /**
     * 该接口不能使用cookie
     *
     * @param startId
     * @param endId
     * @return
     */
    public static List<PickUp> getUserList(int startId, int endId) {
        if (startId < 0 || endId < 0) {
            throw new RuntimeException("开始和结束id不能小于0");
        }
        if (startId > endId) {
            throw new RuntimeException("开始id不能小于结束id");
        }
        List<PickUp> users = new ArrayList<>();

        String tempMemberUrl = null;
        Document tempDocument;
        for (int i = startId; i <= endId; i++) {
            tempMemberUrl = URL_MEMBER + i;
            //System.out.println("loadding " + tempMemberUrl);
            tempDocument = httpc.getUser(tempMemberUrl);
            //判断是否存在用户
            if (!checkUser(tempDocument)) {
                System.out.println("id -> " + i + " 用户不存在");
            } else {
//                System.out.println(tempDocument.html());
                String userNick = tempDocument.select("h1[class='name']").text();
                String picks = tempDocument.select(".count-container .count").eq(0).select("a").text();
                String following = tempDocument.select(".count-container .count").eq(2).select("a").text();
                String userImageUrl = tempDocument.select("a[class='usericon'] img[class='user-image']").attr("src");
                // 过滤较差的用户
                if (Constants.filter_mini_follow != -1) {
                    int intFollowing = Integer.parseInt(following);
                    if (Constants.filter_mini_follow > intFollowing) {
                        C.println("id -> " + i + "用户关注人数小于设定阈值", Ansi.Color.RED);
                        continue;
                    }
                }
                if (Constants.filter_mini_picks != -1) {
                    int intPicks = Integer.parseInt(picks);
                    if (Constants.filter_mini_picks > intPicks) {
                        C.println("id -> " + i + "用户作品数小于设定阈值", Ansi.Color.RED);
                        continue;
                    }
                }
                System.out.println("Add PickUp " + userNick);
                users.add(new PickUp(String.valueOf(i), userNick, following, userImageUrl, picks));
            }
        }

        return users;
    }

    public static List<Illust> getIllustByUser(String uid) {
        List<Illust> illustList = new ArrayList<>();

        List<PickUp> pickUps = getUserList(Integer.parseInt(uid), Integer.parseInt(uid));
        if (pickUps.size() == 0) {
            return illustList;
        }

        String url = URL_ALL_Illusts.replaceAll("\\$id", uid);
        String jsonBody = httpc.get2str(url);
        if (jsonBody == null) {
            return null;
        }

        LinkedTreeMap objectMap = new Gson().fromJson(jsonBody, LinkedTreeMap.class);
        boolean error = (boolean) objectMap.get("error");
        String message = (String) objectMap.get("message");
        if (error) {
            System.out.println(message);
            return illustList;
        }

        LinkedTreeMap bodyMap = (LinkedTreeMap) objectMap.get("body");

        if (bodyMap.get("illusts") instanceof ArrayList) {
            System.out.println("\n当前uid：" + uid + "用户暂无作品。");
            return illustList;
        }

        LinkedTreeMap illustsMap = (LinkedTreeMap) bodyMap.get("illusts");

        // ArrayList upArr = (ArrayList) bodyMap.get("pickup");
        int intUid = Integer.parseInt(uid);
        List<PickUp> users = getUserList(intUid, intUid);
        System.out.println("\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
        PickUp pickUp = null;
        if (users != null && users.size() > 0) {
            pickUp = users.get(0);
            System.out.println("■ 作者：" + pickUp.getUserNick() + " \tuid：" + uid);
            System.out.println("■ 关注：" + pickUp.getFollowing());
            System.out.println("■ avatar：" + pickUp.getUserImageUrl());
        } else {
            System.out.println("获取Up信息失败！");
        }

        Set keySet = illustsMap.keySet();
        Iterator iterator = keySet.iterator();
        List ids = new ArrayList();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            ids.add(key);
        }

        url = URL_ILLUST_LIST.replaceAll("\\$id", uid);

        int size = ids.size();
        int page = size / SIZE;
        int y = size % SIZE;

        // 过滤数量不达标的Up
        if (Constants.filter_mini_size != -1) {
            if (Constants.filter_mini_size > size) {
                C.println("id -> " + uid + "用户作品数小于设定阈值\n", Ansi.Color.RED);
                return illustList;
            }
        }

        if (page > 0) {
            int pageCount = page;
            if (y > 0) {
                pageCount++;
            }
            System.out.println("■ PageCount：" + pageCount + "  Total：" + size + "\t\tlimit：" + SIZE);
        } else {
            System.out.println("■ Total：" + size + "\t\tlimit：" + SIZE);
        }

        System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");

        for (int p = 0; p < page; p++) {
            C.println("loading 第 " + (p + 1) + " 页...", Ansi.Color.YELLOW);
            StringBuilder idSb = new StringBuilder();
            for (int k = p * SIZE; k < (p + 1) * SIZE; k++) {
                idSb.append("ids[]=" + ids.get(k) + "&");
            }
            idSb.append("&work_category=illust&is_first_page=0");
            String pageUrl = url + idSb.toString();
            List<Illust> illusts = loadPages(pageUrl);
            if (illusts != null) {
                illustList.addAll(illusts);
            }
        }
        if (y > 0) {
            C.println("loading 第 " + (page + 1) + " 页...", Ansi.Color.YELLOW);
            StringBuilder idSb = new StringBuilder();
            for (int k = page * SIZE; k < (page * SIZE) + y; k++) {
                idSb.append("ids[]=" + ids.get(k) + "&");
            }
            idSb.append("&work_category=illust&is_first_page=0");
            String pageUrl = url + idSb.toString();
            List<Illust> illusts = loadPages(pageUrl);
            if (illusts != null) {
                illustList.addAll(illusts);
            }
        }

        if (pickUp != null) {
            C.println("当前用户【" + pickUp.getUserNick() + "】列表采集完毕！" + illustList.size(), Ansi.Color.RED);
        }

        return illustList;
    }

    private static List<Illust> loadPages(String url) {
        List<Illust> illusts = new ArrayList<>();
        String body = httpc.get2str(url);
        if (body != null) {
            LinkedTreeMap objectMap = new Gson().fromJson(body, LinkedTreeMap.class);
            boolean error = (boolean) objectMap.get("error");
            if (!error) {
                LinkedTreeMap bodyMap = (LinkedTreeMap) objectMap.get("body");
                LinkedTreeMap worksMap = (LinkedTreeMap) bodyMap.get("works");

                Set keySet = worksMap.keySet();

                Iterator iterator = keySet.iterator();
                int i = 0;
                Illust illust;
                LinkedTreeMap valMap;
                while (iterator.hasNext()) {
                    illust = new Illust();

                    String key = (String) iterator.next();
                    valMap = (LinkedTreeMap) worksMap.get(key);

                    illust.setIllustId((String) valMap.get("illustId"));
                    illust.setIllustTitle((String) valMap.get("illustTitle"));
                    illust.setId((String) valMap.get("id"));
                    illust.setTitle((String) valMap.get("title"));
                    illust.setIllustType((Double) valMap.get("illustType"));
                    illust.setUrl((String) valMap.get("url"));
                    illust.setDescription((String) valMap.get("description"));
                    ArrayList<String> tags = (ArrayList<String>) valMap.get("tags");
                    List<String> newTags = (List<String>) tags.clone();
                    illust.setTags(newTags);

                    illust.setWidth(((Double) valMap.get("width")).intValue());
                    illust.setHeight(((Double) valMap.get("height")).intValue());
                    illust.setPageCount(((Double) valMap.get("pageCount")).intValue());
                    illust.setIsAdContainer((Boolean) valMap.get("isAdContainer"));

                    System.out.print("\t\t" + illust.getTitle());
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.print("\r");
                    illusts.add(illust);
                    i++;
                }
            }
        }

        return illusts;
    }

    private static boolean checkUser(Document document) {
        if (document != null) {
            boolean ret = document.select("h2[class='error-title']").hasText();
            return !ret;
        }
        return false;
    }

}
