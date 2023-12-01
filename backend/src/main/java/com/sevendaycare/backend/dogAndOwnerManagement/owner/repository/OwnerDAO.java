package com.sevendaycare.backend.dogAndOwnerManagement.owner.repository;

import com.sevendaycare.backend.dogAndOwnerManagement.owner.model.Owner;

import java.util.List;
import java.util.Optional;

public interface OwnerDAO {

    Optional<Owner> findOwnerById(String id);



    List<Owner> findOwnerByName(String name);


    Optional<Owner> findOwnerByEmail(String email);

    Optional<Owner> findByDogId(String dogId);

    Optional<Owner> findOwnerByPhone(String phone);

    Optional<Owner> findOwnerByRedBookName(String redBookName);
}
