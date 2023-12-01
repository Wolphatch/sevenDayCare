package com.sevendaycare.backend.dogAndOwnerManagement.owner.repository;

import com.sevendaycare.backend.dogAndOwnerManagement.owner.model.Owner;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OwnerRepository extends MongoRepository<Owner,String> {
    @Query("{ 'ownerId': '?0'}")
    Optional<Owner> findOwnerById(String id);

    @Query("{ 'name': {$regex: '.*?0.*'}}")
    List<Owner> findOwnerByName(String name);

    @Query("{ 'email' : '?0'}")
    Optional<Owner> findOwnerByEmail(String email);


    Optional<Owner> findOwnerByPhone(String phone);

    Optional<Owner> findOwnerByRedBookName(String redBookName);

    @Query("{ 'dogs': {'$in': [?0]}}")
    Optional<Owner> findOwnerByDogId(String dogId);

}
