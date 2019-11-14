package db;

import model.PickUp;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHelper {

    private static Connection connection = null;

    private static DBHelper dbHelper;

    public static DBHelper getInstance() {
        if (dbHelper == null) {
            dbHelper = new DBHelper();
        }
        return dbHelper;
    }

    static {
        try {
            File runDir = new File(DBHelper.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            if (runDir.isFile()) {
                int lastIndex = runDir.getPath().lastIndexOf(File.separator) + 1;
                String path = runDir.getPath().substring(0, lastIndex);
                runDir = new File(path);
            }
            connection = DriverManager.getConnection("jdbc:sqlite:" + runDir.getPath() + "/local.db");
            initTable();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getJarDir() {
        File runDir = new File(DBHelper.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        if (runDir.isFile()) {
            int lastIndex = runDir.getPath().lastIndexOf(File.separator) + 1;
            String path = runDir.getPath().substring(0, lastIndex);
            runDir = new File(path);
        }
        return runDir.getPath() + File.separator;
    }

    public static void initTable() {
        String sql = "create table if not exists pickup(uid integer,userNick string,pickNum integer,following integer,userImageUrl string)";
        getInstance().executeUpdate(sql);
    }

    public int executeUpdate(String sql) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            int ret = statement.executeUpdate(sql);
            //ResultSet rs = statement.executeQuery("select * from person");
            statement.close();
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (Exception e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return 0;
    }

    public List<Map<String, Object>> executeQuery(String sql) {
        Statement statement = null;
        List<Map<String, Object>> mapList = new ArrayList<>();
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            ResultSet rs = statement.executeQuery(sql);

            ResultSetMetaData rsmd = rs.getMetaData();
            int columCount = rsmd.getColumnCount();    //获取结果中列的个数

            while (rs.next()) {
                Map<String, Object> rowData = null;
                for (int i = 1; i <= columCount; i++) {
                    rowData = new HashMap<>();
                    String columName = rsmd.getColumnName(i);//获取列名
                    rowData.put(columName, rs.getObject(columName));
                }
                if (rowData != null)
                    mapList.add(rowData);
            }
            return mapList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return mapList;
    }

    public String getMaxPickUpUid() {
        try {
            List list = executeQuery("SELECT max(uid) as c1 FROM pickup");
            Map<String, Object> map = (Map<String, Object>) list.get(0);
            Integer maxUid = (Integer) map.get("c1");
            return maxUid + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    public void addPickUp(List<PickUp> pickUps) {
        String sql = null;
        int count = 0;
        for (PickUp pickUp : pickUps) {

            count = executeQuery("SELECT uid FROM pickup where uid=\"" + pickUp.getUid() + "\"").size();

            if (count == 0) {
                sql = "INSERT INTO pickup (\"uid\", \"userNick\", \"following\", " +
                        "\"userImageUrl\", \"pickNum\") VALUES (\"" + pickUp.getUid() + "\", \"" + pickUp.getUserNick() + "\"" +
                        ", \"" + pickUp.getFollowing() + "\", \"" + pickUp.getUserImageUrl() + "\", \"" + pickUp.getPicks() + "\");";
                executeUpdate(sql);
            } else {
                // 更新资料
                sql = "UPDATE \"pickup\" SET \"userNick\"='" + pickUp.getUserNick() + "'," +
                        " \"following\"=" + pickUp.getFollowing() + ", " +
                        "\"userImageUrl\"='" + pickUp.getUserImageUrl() + "'," +
                        " \"pickNum\"=" + pickUp.getPicks() + " where \"uid\"='" + pickUp.getUid() + "';";
                executeUpdate(sql);
            }

        }
    }


}
