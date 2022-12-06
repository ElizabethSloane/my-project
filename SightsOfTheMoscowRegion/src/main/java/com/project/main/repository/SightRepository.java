package com.project.main.repository;

import com.project.main.model.Sight;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SightRepository extends CrudRepository<Sight, Integer> {
}