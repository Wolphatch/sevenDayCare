package com.sevendaycare.backend.dogAndOwnerManagement.owner.repository;

import com.sevendaycare.backend.dogAndOwnerManagement.owner.model.Owner;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("ownerMongoData")
public class OwnerMongoDataAccessService implements OwnerDAO {

    private OwnerRepository ownerRepository;

     public OwnerMongoDataAccessService(OwnerRepository ownerRepository){
         this.ownerRepository = ownerRepository;
     }

    @Override
    public Optional<Owner> findOwnerById(String id) {
        return ownerRepository.findOwnerById(id);
    }

    @Override
    public List<Owner> findOwnerByName(String name) {
        return ownerRepository.findOwnerByName(name);
    }

    @Override
    public Optional<Owner> findOwnerByEmail(String email) {
        return ownerRepository.findOwnerByEmail(email);
    }

    @Override
    public Optional<Owner> findOwnerByPhone(String phone){
         return ownerRepository.findOwnerByPhone(phone);
    }

     @Override
    public Optional<Owner> findOwnerByRedBookName(String redBookName){
         return  ownerRepository.findOwnerByRedBookName(redBookName);
     }

    @Override
    public Optional<Owner> findByDogId(String dogId) {
        return ownerRepository.findOwnerByDogId(dogId);
    }
}
