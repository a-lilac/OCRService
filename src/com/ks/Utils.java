package com.ks;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2017/3/18.
 */
public class Utils {

    public static void initConfig(String filename) {
        Properties p = new Properties();
        InputStream in = null;
        try {
            in = new FileInputStream(filename);
            p.load(in);
            in.close();
            if (p.containsKey("driver")) {
                Config.driver = p.getProperty("driver");
            }
            if (p.containsKey("url")) {
                Config.url = p.getProperty("url");
            }
            if (p.containsKey("username")) {
                Config.username = p.getProperty("username");
            }
            if (p.containsKey("password")) {
                Config.password = p.getProperty("password");
            }
            if (p.containsKey("exePath")) {
                Config.exePath = p.getProperty("exePath");
            }
            if (p.containsKey("rootImg")) {
                Config.rootImg = p.getProperty("rootImg");
            }
            if (p.containsKey("lang")) {
                Config.lang = p.getProperty("lang");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
