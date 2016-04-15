package com.htche.particle.model;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by jankie on 16/4/9.
 */
@Setter
@Getter
public class CityInfo {

    @JSONField(name="id")
    private String cityId;

    @JSONField(name="area_name")
    private String cityName;
}
