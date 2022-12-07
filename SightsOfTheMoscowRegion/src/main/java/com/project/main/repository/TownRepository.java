package com.project.main.repository;

import com.project.main.model.Town;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TownRepository extends CrudRepository<Town, Integer> {
}