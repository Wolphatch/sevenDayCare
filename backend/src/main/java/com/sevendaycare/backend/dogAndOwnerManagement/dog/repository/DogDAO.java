package com.sevendaycare.backend.dogAndOwnerManagement.dog.repository;

import com.sevendaycare.backend.dogAndOwnerManagement.dog.model.Dog;

import java.util.List;
import java.util.Optional;

public interface DogDAO {
    /*
   CRUD
     */

    void insertDog(Dog dog);

    void insertDogs(List<Dog> dogs);

    List<Dog> findAllDogs();

    Optional<Dog> findDogByDogId(String id);

    List<Dog> findDogByName(String name);

    List<Dog> findByNameAndBreed(String name, String breed);

    void updateDog(Dog dog);

    void updateDogs(List<Dog> dogs);

    void deleteDog(Dog dog);

    List<Dog> findByGender(String gender);

    List<Dog> findByDesexed(Boolean desexed);

    List<Dog> findByOwnerPhone(String phone);

    List<Dog> findByOwnerEmail(String email);

    Boolean existsDogByNameAndOwnerPhone(String name, String ownerName);

    Boolean existsDogByNameAndOwnerEmail(String name, String ownerEmail);

}
