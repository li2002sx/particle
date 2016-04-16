package com.htche.particle.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

/**
 * Created by jankie on 16/4/11.
 */
@Setter
@Getter
@SolrDocument(solrCoreName = "collection1")
public class CarModel {

    @Id
    @Field
    private String id;

    @Field
    private String name;
}
