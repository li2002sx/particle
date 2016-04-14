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
public class AnalyzerTestInfo {

    private String keywords;

    private int totalHits;

    private List<String> results;
}
