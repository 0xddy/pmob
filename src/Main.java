import db.DBHelper;
import http.Httpc;
import model.Illust;
import org.fusesource.jansi.Ansi;
import respository.Constants;
import respository.PMRespository;
import utils.C;
import work.IllustWorker;
import work.PickUpsWorker;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static String BASE_DIR = DBHelper.getJarDir() + "download";

    private static Scanner scanner = new Scanner(System.in);

    public static void homePanel() {

        File downloadDir = new File(BASE_DIR);
        if (!downloadDir.exists()) {
            downloadDir.mkdirs();
        }

        System.out.println("========== Pixiv Mob ============");
        System.out.println("[1] 单用户采集\t\t[2] 批量采集");
        System.out.println("[3] 扫描采集用户");
        System.out.println("[4] 设置下载路径");
        String imageTypeStr = "高清";
        if (Constants.image_type == 1) {
            imageTypeStr = "迷你";
        } else if (Constants.image_type == 2) {
            imageTypeStr = "小图";
        } else if (Constants.image_type == 3) {
            imageTypeStr = "高清";
        } else if (Constants.image_type == 4) {
            imageTypeStr = "原图";
        }
        System.out.println("[5] 设置下载尺寸 [" + imageTypeStr + "]");
        System.out.print("请选择操作(例 1)：");
        String input = scanner.nextLine();
        if (input.equals("1")) {

            System.out.print("请输入用户id：");
            input = scanner.nextLine();
            showSetting();
            spiderByUser(input);

        } else if (input.equals("2")) {


            System.out.print("请输入用户id区间 [继续 " + DBHelper.getInstance().getMaxPickUpUid() + "]：");
            input = scanner.nextLine();
            showSetting();
            String[] idArr = input.split(":");
            int startId = Integer.parseInt(idArr[0]);
            int endId = Integer.parseInt(idArr[1]);
            for (int i = startId; i <= endId; i++) {
                spiderByUser(String.valueOf(i));
            }
        } else if (input.equals("3")) {

            System.out.print("请输入用户id区间 [继续 " + DBHelper.getInstance().getMaxPickUpUid() + ":50000000]：");
            input = scanner.nextLine();
            showSetting();
            String[] idArr = input.split(":");
            int startId = Integer.parseInt(idArr[0]);
            int endId = Integer.parseInt(idArr[1]);

            PickUpsWorker pickUpsWorker = new PickUpsWorker(startId, endId);
            pickUpsWorker.run();

        } else if (input.equals("4")) {
            setBaseDir();
        } else if (input.equals("5")) {
            setImageType();
        }
    }

    private static void setImageType() {
        C.print("设置下载图片类型 [1]缩略图 [2]小图 [3]高清 [4]原图：", Ansi.Color.YELLOW);
        String imageType = scanner.nextLine();
        switch (imageType) {
            case "1":
            case "2":
            case "3":
            case "4":
                Constants.image_type = Integer.parseInt(imageType);
            default:
                break;
        }

        homePanel();
    }

    public static void showSetting() {
        C.print("是否使用Cookie [y/N]：", Ansi.Color.YELLOW);
        String useCookie = scanner.nextLine();
        if (useCookie.equalsIgnoreCase("y")) {
            C.println("粘贴Cookie至此：", Ansi.Color.YELLOW);
            String cookie = scanner.nextLine();
            Httpc.addHeader("Cookie", cookie);
        } else {
            Httpc.removeHeader("Cookie");
            C.println("当前不登录采集模式", Ansi.Color.YELLOW);
        }
    }

    public static void setBaseDir() {
        C.print("设置下载路径 ：", Ansi.Color.YELLOW);
        String downloadPath = scanner.nextLine();
        if (!downloadPath.equals("")) {
            BASE_DIR = downloadPath;
        } else {
            BASE_DIR = DBHelper.getJarDir();
        }

        File downloadDir = new File(BASE_DIR);
        if (!downloadDir.exists()) {
            downloadDir.mkdirs();
        }

        homePanel();
    }

    public static void main(String[] args) {

        if (args.length == 2) {
            Httpc.useProxy = true;
            Httpc.mProxyIP = args[0];
            Httpc.mProxyPort = Integer.parseInt(args[1]);
        }

        homePanel();


    }

    public static void spiderByUser(String uid) {
        List<Illust> illustList = PMRespository.getIllustByUser(uid);
        // 采集内容
        // 提交给下载队列
        if (illustList != null && illustList.size() > 0) {
            IllustWorker illustWorker = new IllustWorker(uid, illustList);
            illustWorker.setDownloadDir(BASE_DIR);
            illustWorker.run();
        }
    }


}
