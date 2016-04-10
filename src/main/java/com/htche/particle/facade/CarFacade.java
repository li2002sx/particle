package com.htche.particle.facade;

import com.htche.particle.model.CarInfo;
import com.htche.particle.model.ParticleResult;
import com.htche.particle.util.StringHelper;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jankie on 16/4/10.
 */
@Component
public class CarFacade {

    private final String[] _COLOARR = new String[]{"白", "黑", "棕"};
    private final String[] _SOURCESTATUS = new String[]{"现车", "现货", "期货", "打税放"};

    public CarInfo analyzeUGC(List<ParticleResult> particleResults) {
        List<String> colors = Arrays.asList(_COLOARR);
        List<String> status = Arrays.asList(_SOURCESTATUS);

        CarInfo carInfo = new CarInfo();
        String remark = "";
        for (ParticleResult item : particleResults) {
            String term = item.getTerm();
            if (term.indexOf("款") > -1) {
                carInfo.setYear(term);
            } else if (term.indexOf("规") > -1) {
                carInfo.setRule(term);
            } else if (term.indexOf("版") > -1) {
                carInfo.setBrand(term);
            } else if (StringHelper.isMobile(term)) {
                carInfo.setMobile(term);
            } else if (StringHelper.isMoney(term)) {
                carInfo.setPrice(term);
            } else if (colors.contains(term)) {
                if (carInfo.getOutColor() != null && !carInfo.getOutColor().isEmpty()) {
                    carInfo.setInColor(term);
                } else {
                    carInfo.setOutColor(term);
                }
            } else if (status.contains(term)) {
                carInfo.setSourceStatus(term);
            } else {
                remark += term + " ";
            }
        }
        carInfo.setRemark(remark);

        return carInfo;
    }

}
