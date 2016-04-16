package com.htche.particle.facade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.htche.particle.cache.CacheManager;
import com.htche.particle.model.CityInfo;
import com.htche.particle.util.AppConfigHelper;
import com.htche.particle.util.HttpHelper;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @Title: CityFacade
 * @Package: com.htche.particle.facade
 * @Description: (用一句话描述该文件做什么)
 * @author: jankie.li@Mtime.com
 * @date: 2016/4/15 14:56
 * @version: V1.0
 */
@Component
public class CityFacade {

//    private final String _CITYURL = "http://www.topcars.cn/interface/index.php?c=tesst&m=get_city";

    public List<CityInfo> getCities() {
        List<CityInfo> cityInfos = null;
        if (CacheManager.getCache("cities") == null) {
            cityInfos = new ArrayList<CityInfo>();
            String cityUrl = String.format("%s%s",AppConfigHelper.nodeMap.get("url"),AppConfigHelper.nodeMap.get("city_url"));
            String cityJson = HttpHelper.HttpRequest(cityUrl);
            JSONObject object = JSON.parseObject(cityJson);
            Set<String> set = object.keySet();
            Iterator<String> iterator = set.iterator();
            while (iterator.hasNext()) {
                CityInfo cityInfo = JSON.parseObject(object.getString(iterator.next()), CityInfo.class);
                String cityName = cityInfo.getCityName();
                cityName = cityName.replaceAll("市", "").replaceAll("区", "");
                cityInfo.setCityName(cityName);
                cityInfos.add(cityInfo);
            }
            CacheManager.addCache("cities", cityInfos);
        } else {
            cityInfos = (List<CityInfo>) CacheManager.getCache("cities");
        }
        return cityInfos;
    }
}