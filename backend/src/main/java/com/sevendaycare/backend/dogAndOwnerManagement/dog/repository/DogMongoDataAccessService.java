package com.sevendaycare.backend.dogAndOwnerManagement.dog.repository;


import com.sevendaycare.backend.dogAndOwnerManagement.dog.model.Dog;
import com.sevendaycare.backend.dogAndOwnerManagement.owner.model.Owner;
import com.sevendaycare.backend.dogAndOwnerManagement.owner.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository("dogMongoData")
public class DogMongoDataAccessService implements DogDAO{
    public final DogRepository dogRepository;

    public final OwnerRepository ownerRepository;

    public DogMongoDataAccessService(DogRepository dogRepository, OwnerRepository ownerRepository){
        this.dogRepository = dogRepository;
        this.ownerRepository = ownerRepository;
    }

    @Override
    public void insertDog(Dog dog) {
        dogRepository.save(dog);
    }

    @Override
    public void insertDogs(List<Dog> dogs){
        dogRepository.saveAll(dogs);
    }

    @Override
    public Optional<Dog> findDogByDogId(String id) {
        return dogRepository.findDogByDogId(id);
    }

    @Override
    public List<Dog> findAllDogs(){
        return dogRepository.findAllByDogNameIsNotEmpty();
    }
    @Override
    public List<Dog> findDogByName(String name) {
        return dogRepository.findByName(name);
    }

    @Override
    public List<Dog> findByNameAndBreed(String name, String breed) {
        return dogRepository.findByNameAndBreed(name, breed);
    }

    @Override
    public void updateDog(Dog dog) {
        dogRepository.save(dog);
    }

    @Override
    public void updateDogs(List<Dog> dogs) {
        dogRepository.saveAll(dogs);
    }

    @Override
    public void deleteDog(Dog dog) {
        dogRepository.delete(dog);
    }


    @Override
    public List<Dog> findByGender(String gender){
        return dogRepository.findByGender(gender);
    }

    @Override
    public List<Dog> findByDesexed(Boolean desexed){
        return dogRepository.findByDesexed(desexed);
    }

    @Override
    public List<Dog> findByOwnerPhone(String phone) {
        return dogRepository.findByOwnerPhone(phone);
    }

    @Override
    public List<Dog> findByOwnerEmail(String email) {
        return dogRepository.findByOwnerEmail(email);
    }

    @Override
    public Boolean existsDogByNameAndOwnerPhone(String name, String ownerPhone){
        List<Dog> dogs= dogRepository.findByName(name);
        List<String> phoneFromQuery = dogs.stream()
                .map(Dog::getOwnerPhone)
                .filter(p-> Objects.equals(p,ownerPhone))
                .toList();
        return !phoneFromQuery.isEmpty();
    }

    @Override
    public Boolean existsDogByNameAndOwnerEmail(String name, String ownerEmail){
        List<Dog> dogs= dogRepository.findByName(name);
        List<String> phoneFromQuery = dogs.stream()
                .map(Dog::getOwnerEmail)
                .filter(p-> Objects.equals(p,ownerEmail))
                .toList();
        return !phoneFromQuery.isEmpty();
    }
}
