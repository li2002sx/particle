package com.htche.particle.util;

import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.dic.Dictionary;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jankie on 16/4/9.
 */
public class FileHelper {

    public static void fileOutputStream(String path, List<String> terms) throws IOException {

        List<String> termList = new ArrayList<String>();
        FileOutputStream fos = new FileOutputStream(path, true); // true代表追加
        for (String item : terms) {
            if (item != null && !item.isEmpty()) {
                fos.write(String.format("%s\r\n", item).getBytes());
                termList.add(item);
            }
        }
        fos.close();

        if (termList.size() > 0) {
            Configuration cfg = DefaultConfig.getInstance();
            cfg.setUseSmart(true);
            Dictionary.initial(cfg);

            Dictionary dictionary = Dictionary.getSingleton();
            dictionary.addWords(termList);
        }
    }

    /**
     * 读取文件
     */
    public static String readFile(String path) {
        String readTxt = "";
        try {
            File file = new File(path);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");
                BufferedReader reader = new BufferedReader(read);
                String line;
                while ((line = reader.readLine()) != null) {
                    readTxt += line + "\r\n";
                }
                read.close();
            } else {
                throw new FileNotFoundException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readTxt;
    }

    /**
     * 写入文件
     */
    public static void writeFile(String path, String writeTxt) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            BufferedWriter writer = new BufferedWriter(write);
            writer.write(writeTxt);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
