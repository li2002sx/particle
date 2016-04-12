package com.htche.particle.controller;

import com.alibaba.fastjson.JSON;
import com.htche.particle.lucene.LuceneIndex;
import com.htche.particle.model.CarModel;
import com.htche.particle.model.InvokeResult;
import com.htche.particle.solr.CarModelRepository;
import com.htche.particle.util.HttpHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
    private final String _CARMODELURL = "http://www.topcars.cn/interface/index.php?c=tesst&m=all_models";

    @RequestMapping("createIndex")
    public
    @ResponseBody
    InvokeResult createIndex() {
        InvokeResult result = new InvokeResult();
        try {
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
}
