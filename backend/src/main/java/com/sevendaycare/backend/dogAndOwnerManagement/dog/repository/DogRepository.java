package com.sevendaycare.backend.dogAndOwnerManagement.dog.repository;

import com.sevendaycare.backend.dogAndOwnerManagement.dog.model.Dog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DogRepository extends MongoRepository<Dog, String> {

    @Query("{}")
    List<Dog> findAllByDogNameIsNotEmpty();

    Optional<Dog> findDogByDogId(String id);
    List<Dog> findByNameAndBreed(String name, String breed);

    List<Dog> findByName(String name);

    List<Dog> findByGender(String gender);

    List<Dog> findByDesexed(Boolean desexed);

    List<Dog> findByOwnerEmail(String email);

    List<Dog> findByOwnerPhone(String phone);

}
