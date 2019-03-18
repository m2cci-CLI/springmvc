package com.training.spring.repository;

import com.training.spring.model.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SiteDao extends JpaRepository<Site, String> {
}
