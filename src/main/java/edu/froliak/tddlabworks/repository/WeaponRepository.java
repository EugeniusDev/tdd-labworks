package edu.froliak.tddlabworks.repository;

/*
  @author eugen
  @project tdd-labworks
  @class WeaponRepository
  @version 1.0.0
  @since 3/27/2026 - 10.49
*/


import edu.froliak.tddlabworks.model.Weapon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeaponRepository extends MongoRepository<Weapon, String> {
    public boolean existsByCode(String code);
}