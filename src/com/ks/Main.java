package com.ks;

import java.io.*;
import java.util.List;

public class Main {
    private static AttachDao dao = new AttachDao();
    private static Process process;

    public static void main(String[] args) {
        try {
            // write your code here
            String filename = (args == null || args.length == 0) ? "ocr.properties" : args[0];
            if(new File(filename).exists()) {
                Utils.initConfig(filename);
            }
            System.out.println("图像识别程序开始运行...");
            List<Attachment> attachments = null;
            while (true) {
                attachments = dao.getAttachList(10);
                if (attachments == null || attachments.size() == 0) {
                    System.out.println("暂无可识别图片，程序休眠10s...");
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    String cmd = "";
                    for (Attachment m : attachments
                            ) {
                        cmd = Config.exePath + " " + Config.rootImg + "\\" + m.getAttach_path().replace("/", "\\") +
                                " " + m.getAttach_id() + " " + Config.lang;
                        System.out.println("图像识别开始");
                        System.out.println("附件id:" + m.getAttach_id() + " 图片名称:" +
                                m.getAttach_path().substring(m.getAttach_path().lastIndexOf("/") + 1));
                        execCmd(cmd, m.getAttach_id());
                        System.out.println("结束识别附件id:" + m.getAttach_id());
                    }
                    attachments.clear();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void execCmd(String cmd, String attachid) {
        Runtime runtime = Runtime.getRuntime();
        InputStream ins = null;
        try {
            process = runtime.exec(cmd);
            ins = process.getInputStream();  // 获取执行cmd命令后的信息
            BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);  // 输出
            }
            int exitValue = process.waitFor();
            System.out.println("返回值：" + exitValue);
            process.getOutputStream().close();  // 不要忘记了一定要关
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        File f = new File(attachid + ".txt");
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), "utf-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            dao.setAttach(sb.toString(), attachid);
            f.delete();
        }
    }
}
