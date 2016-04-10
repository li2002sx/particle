package com.htche.particle.util;

import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.dic.Dictionary;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jankie on 16/4/9.
 */
public class FileHelper {

    public static void fileOutputStream(String path, List<String> terms) throws IOException{

        List<String> termList = new ArrayList<String>();
        FileOutputStream fos = new FileOutputStream(path, true); // true代表追加
        for (String item : terms) {
            if (item != null && !item.isEmpty()) {
                fos.write(String.format("%s\r\n",item).getBytes());
                termList.add(item);
            }
        }
        fos.close();

        if(termList.size()>0) {
            Configuration cfg = DefaultConfig.getInstance();
            cfg.setUseSmart(true);
            Dictionary.initial(cfg);

            Dictionary dictionary = Dictionary.getSingleton();
            dictionary.addWords(termList);
        }
    }
}
