package edu.froliak.tddlabworks.repository;

/*
  @author eugen
  @project tdd-labworks
  @class ItemRepository
  @version 1.0.0
  @since 3/27/2026 - 10.49
*/


import edu.froliak.tddlabworks.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
    public boolean existsByCode(String code);
}