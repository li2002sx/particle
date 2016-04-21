package com.htche.particle.facade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.htche.particle.cache.CacheManager;
import com.htche.particle.model.CityInfo;
import com.htche.particle.util.AppConfigHelper;
import com.htche.particle.util.HttpHelper;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Title: CityFacade
 * @Package: com.htche.particle.facade
 * @Description: (用一句话描述该文件做什么)
 * @author: jankie.li@Mtime.com
 * @date: 2016/4/15 14:56
 * @version: V1.0
 */
@Component
public class SpecFacade {

    public Map<Integer,String> getSpeces() {
        Map<Integer,String> map = new HashMap<Integer,String>();
        map.put(0,"");
        map.put(1,"美规");
        map.put(2,"欧规");
        map.put(3,"中东版");
        map.put(4,"墨西哥版");
        map.put(5,"加版");
        map.put(8,"德版");
        map.put(9,"中规");
        return map;
    }
}