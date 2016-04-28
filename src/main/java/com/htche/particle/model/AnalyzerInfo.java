package com.htche.particle.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * Created by jankie on 16/4/13.
 */
@Setter
@Getter
public class AnalyzerInfo {

    private Map<String,String> carTypeMap;

    private Integer spec;

    private String specName;

    private String outColor;

    private String inColor;

    private String price;

    private String mobile;

    private Integer status;

    private String carFrame;

    private CityInfo city;

    private String keywords;

    private String ikwords;
}
