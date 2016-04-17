package com.htche.particle.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.htche.particle.cache.CacheManager;
import com.htche.particle.facade.CarFacade;
import com.htche.particle.facade.CityFacade;
import com.htche.particle.lucene.LuceneIndex;
import com.htche.particle.model.CarModel;
import com.htche.particle.model.CityInfo;
import com.htche.particle.model.InvokeResult;
import com.htche.particle.solr.CarModelRepository;
import com.htche.particle.util.AppConfigHelper;
import com.htche.particle.util.HttpHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @Title: SearchIndexController
 * @Package: com.htche.particle.controller
 * @Description: (用一句话描述该文件做什么)
 * @author: jankie.li@Mtime.com
 * @date: 2016/4/12 10:43
 * @version: V1.0
 */
@Controller
@RequestMapping("/")
public class SearchIndexController {


    @Autowired
    private CarModelRepository repository;
    @Autowired
    private CityFacade cityFacade;

    private final String _CARMODELURL = String.format("%s%s",AppConfigHelper.nodeMap.get("url"),AppConfigHelper.nodeMap.get("car_url"));

    @RequestMapping("createIndex")
    public
    @ResponseBody
    InvokeResult createIndex() {
        InvokeResult result = new InvokeResult();
        try {
            File indexDir = new File(AppConfigHelper.nodeMap.get("index_path"));
            indexDir.delete();
            String carModelJson = HttpHelper.HttpRequest(_CARMODELURL);
            List<CarModel> carModels = JSON.parseArray(carModelJson, CarModel.class);
            for (CarModel model : carModels) {
//            this.repository.save(model);
                LuceneIndex.indexPost(model.getId(), model.getName());
                System.out.println(String.format("%s---%s", model.getId(), model.getName()));
            }
            result.setSuccess(true);
            // fetch a single product
//        System.out.println("CarModel found with findByNameStartingWith('加版'):");
//        System.out.println("--------------------------------");
//        for (CarModel model : this.repository.findByNameStartingWith("加版")) {
//            System.out.println(String.format("%s---%s",model.getId(),model.getName()));
//        }
        } catch (Exception ex) {
            result.setError(ex.getMessage());
            throw new RuntimeException(ex);
        }
        return result;
    }

    @RequestMapping("city")
    public
    @ResponseBody
    InvokeResult city() {
        InvokeResult result = new InvokeResult();
        try {
            List<CityInfo> cityInfos = cityFacade.getCities();
            for (CityInfo cityInfo : cityInfos) {
                System.out.println(cityInfo.getCityName());
            }
            result.setSuccess(true);
        } catch (Exception ex) {
            result.setError(ex.getMessage());
            throw new RuntimeException(ex);
        }
        return result;
    }
}
