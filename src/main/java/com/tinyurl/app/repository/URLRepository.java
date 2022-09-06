package com.tinyurl.app.repository;

import com.tinyurl.app.entity.UrlEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface URLRepository extends CrudRepository<UrlEntity, String> {

}
