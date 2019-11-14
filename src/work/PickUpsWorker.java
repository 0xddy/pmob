package work;

import db.DBHelper;
import model.PickUp;
import respository.PMRespository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PickUpsWorker extends Thread {

    private ExecutorService executorService = Executors.newFixedThreadPool(20);

    private int startId;
    private int endId;

    public PickUpsWorker(int startId, int endId) {
        this.startId = startId;
        this.endId = endId;
    }

    public void run() {
        int ct = 0;
        for (int i = startId; i <= endId; i++) {
            int finalI = i;
            executorService.execute(() -> {
                List<PickUp> pickUps = PMRespository.getUserList(finalI, finalI);
                DBHelper.getInstance().addPickUp(pickUps);
            });
            ct++;
            if (ct % 1000 == 0) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        executorService.shutdown();
        while (true) {
            if (executorService.isTerminated()) {
                System.out.println("\n采集用户任务池已完成！");
                break;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
