package work;

import model.Illust;
import model.IllustImage;
import org.fusesource.jansi.Ansi;
import respository.ArtworksRespository;
import respository.Constants;
import utils.C;
import utils.DownloadUtils;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IllustWorker {

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    private List<Illust> illusts;
    private String uid;

    private String downloadDir;

    public IllustWorker(String uid, List<Illust> illusts) {
        this.illusts = illusts;
        this.uid = uid;
    }

    public void setDownloadDir(String downloadDir) {
        this.downloadDir = downloadDir;
    }

    public void run() {
        C.println("\t\t开始资源任务采集..", Ansi.Color.YELLOW);

        for (Illust illust : illusts) {
            executorService.submit(() -> {
                // 下载图片
                workLogic(illust);
            });
        }

        executorService.shutdown();

        while (true) {
            if (executorService.isTerminated()) {
                System.out.println("\n" + uid + " 资源任务池已完成！");
                break;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private void workLogic(Illust illust) {

        List<IllustImage> illustImages = ArtworksRespository.getImageUrls(illust.getId());

        String tempUrl = null;
        File tempFile = null;
        for (IllustImage illustImage : illustImages) {
            // 下载均衡的图片
            //illustImage.getUrls().getRegular();
            // 高清原图
            if (Constants.image_type == 1) {
                tempUrl = illustImage.getUrls().getThumb_mini();
            } else if (Constants.image_type == 2) {
                tempUrl = illustImage.getUrls().getSmall();
            } else if (Constants.image_type == 3) {
                tempUrl = illustImage.getUrls().getRegular();
            } else if (Constants.image_type == 4) {
                tempUrl = illustImage.getUrls().getOriginal();
            }

            tempFile = new File(tempUrl);

            File file = new File(downloadDir + "/" + uid + "/" +
                    illust.getId() + "_" + illust.getTitle() + "/" + tempFile.getName());
            DownloadUtils.downloadFile(tempUrl, file);
            System.out.print("#");
        }
    }


}
