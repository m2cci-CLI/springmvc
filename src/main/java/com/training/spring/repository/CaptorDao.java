package com.training.spring.repository;

import com.training.spring.model.Captor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CaptorDao extends JpaRepository<Captor, String> {
    List<Captor> findBySiteId(String siteId);
    void deleteBySiteId(String siteId);
}
