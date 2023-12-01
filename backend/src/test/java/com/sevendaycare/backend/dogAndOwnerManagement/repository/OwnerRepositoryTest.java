package com.sevendaycare.backend.dogAndOwnerManagement.repository;

import com.mongodb.client.MongoClient;
import com.sevendaycare.backend.dogAndOwnerManagement.dog.model.Dog;
import com.sevendaycare.backend.dogAndOwnerManagement.dog.repository.DogRepository;
import com.sevendaycare.backend.dogAndOwnerManagement.owner.model.Owner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.sevendaycare.backend.dogAndOwnerManagement.owner.repository.OwnerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataMongoTest
@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
public class OwnerRepositoryTest {
    @Autowired
    MongoClient client;

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    DogRepository dogRepository;

    @BeforeEach
    void setup(){
        dogRepository.deleteAll();
        ownerRepository.deleteAll();
        Owner owner = Owner.builder()
                .ownerId("0")
                .name("zac")
                .email("zac@gmail.com")
                .build();
        Owner owner2 = Owner.builder()
                .ownerId("1")
                .email("sha@gmail.com")
                .name("sha")
                .build();

        ownerRepository.save(owner);
        ownerRepository.save(owner2);

        Dog dog = Dog.builder()
                .dogId("0")
                .name("Seven")
                .nickName("seven")
                .breed("Swiss Shp")
                .yearOfBirth(2022)
                .age(3)
                .weight(31.00)
                .ownerEmail("zac@gmail.com")
                .gender("male")
                .desexed(true)
                .build();
        dogRepository.save(dog);

        Dog dog2 = Dog.builder()
                .dogId("1")
                .name("Eleven")
                .nickName("eleven")
                .breed("Swiss Shp")
                .yearOfBirth(2020)
                .age(4)
                .weight(31.00)
                .gender("male")
                .desexed(true)
                .ownerEmail("sha@gmail.com")
                .build();
        dogRepository.save(dog2);

        Dog dog3 = Dog.builder()
                .dogId("2")
                .name("One")
                .nickName("one")
                .breed("Swiss Shp")
                .yearOfBirth(2020)
                .age(4)
                .weight(31.00)
                .gender("male")
                .desexed(true)
                .ownerEmail("sha@gmail.com")
                .build();
        dogRepository.save(dog3);

        owner.setDogs(new ArrayList<>());
        owner2.setDogs(new ArrayList<>());
        owner.getDogs().add(dog.getDogId());
        owner2.getDogs().add(dog2.getDogId());
        owner2.getDogs().add(dog3.getDogId());
        ownerRepository.save(owner);
        ownerRepository.save(owner2);
    }


    @AfterEach
    void teardown(){
        dogRepository.deleteAll();
        ownerRepository.deleteAll();
    }

    @Test
    void successfullyFindOwnerWithId(){
        Optional<Owner> owner = ownerRepository.findOwnerById("0");
        assertThat(owner.isPresent()).isTrue();
        assertThat(owner.map(Owner::getName).orElse(null)).isEqualTo("zac");
    }

    @Test
    void successfullyFindOwnerWithName(){
        List<Owner> owner = ownerRepository.findOwnerByName("zac");
        assertThat(owner.size()).isEqualTo(1);
        assertThat(owner.get(0).getName()).isEqualTo("zac");
    }

    @Test
    void successfullyFindOwnerWithPartialName(){
        List<Owner> owner = ownerRepository.findOwnerByName("za");
        assertThat(owner.size()).isEqualTo(1);
        assertThat(owner.get(0).getName()).isEqualTo("zac");
    }

    @Test
    void failedToFindOwnerWithNonExistName(){
        List<Owner> owner = ownerRepository.findOwnerByName("zas");
        assertThat(owner.size()).isEqualTo(0);
    }

    @Test
    void successfullyFindOwnerWithEmail(){
        Optional<Owner> owner = ownerRepository.findOwnerByEmail("zac@gmail.com");
        assertThat(owner.isPresent()).isTrue();
        assertThat(owner.map(Owner::getName).orElse(null)).isEqualTo("zac");
    }

    @Test
    void failedToFindOwnerWithNonExistEmail(){
        Optional<Owner> owner = ownerRepository.findOwnerByEmail("zac22@gmail.com");
        assertThat(owner.isPresent()).isFalse();
    }

    @Test
    void successfullyFindOwnerWithDogId(){
        Optional<Owner> owner = ownerRepository.findOwnerByDogId("2");
        assertThat(owner.isPresent()).isTrue();
        assertThat(owner.map(Owner::getName).orElse(null)).isEqualTo("sha");
    }

    @Test
    void failedToFindOwnerWithNonExistDogId(){
        Optional<Owner> owner = ownerRepository.findOwnerByDogId("9");
        assertThat(owner.isPresent()).isFalse();
    }

}
