package com.babayan.service.currency.repository;

import com.babayan.service.currency.entity.RateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author by artbabayan
 */
@Repository
public interface RateRepository extends JpaRepository<RateEntity, Long> {

}
