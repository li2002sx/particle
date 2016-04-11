package com.htche.particle.solr;

import com.htche.particle.model.CarModel;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;

/**
 * Created by jankie on 16/4/11.
 */
public interface CarModelRepository extends SolrCrudRepository<CarModel, String> {

    List<CarModel> findByNameStartingWith(String name);
}
