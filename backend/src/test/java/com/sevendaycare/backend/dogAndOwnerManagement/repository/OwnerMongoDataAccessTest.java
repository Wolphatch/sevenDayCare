package com.sevendaycare.backend.dogAndOwnerManagement.repository;

import com.sevendaycare.backend.dogAndOwnerManagement.dog.model.Dog;
import com.sevendaycare.backend.dogAndOwnerManagement.dog.repository.DogMongoDataAccessService;
import com.sevendaycare.backend.dogAndOwnerManagement.dog.repository.DogRepository;
import com.sevendaycare.backend.dogAndOwnerManagement.owner.model.Owner;
import com.sevendaycare.backend.dogAndOwnerManagement.owner.repository.OwnerMongoDataAccessService;
import com.sevendaycare.backend.dogAndOwnerManagement.owner.repository.OwnerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("dev")
public class OwnerMongoDataAccessTest {
    OwnerMongoDataAccessService underTest;

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    DogRepository dogRepository;

    @BeforeEach
    void setup(){
        underTest = new OwnerMongoDataAccessService(ownerRepository);
        dogRepository.deleteAll();
        ownerRepository.deleteAll();
        Owner owner = Owner.builder()
                .ownerId("0")
                .dogs(new ArrayList<>())
                .email("zac@gmail.com")
                .name("zac")
                .build();

        ownerRepository.save(owner);

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
                .ownerEmail("zac@gmail.com")
                .build();
        dogRepository.save(dog2);

        owner.getDogs().add(dog.getDogId());
        owner.getDogs().add(dog2.getDogId());
        ownerRepository.save(owner);

    }

    @AfterEach
    public void teardown(){
        dogRepository.deleteAll();
        ownerRepository.deleteAll();
    }

    @Test
    void successfullyFindOwnerWithName(){
        List<Owner> owner = underTest.findOwnerByName("zac");
        assertThat(owner.size()).isEqualTo(1);
        assertThat(owner.get(0).getName()).isEqualTo("zac");
    }

    @Test
    void successfullyFindOwnerWithPartialName(){
        List<Owner> owner = underTest.findOwnerByName("za");
        assertThat(owner.size()).isEqualTo(1);
        assertThat(owner.get(0).getName()).isEqualTo("zac");
    }

    @Test
    void failedToFindOwnerWithNonExistName(){
        List<Owner> owner = underTest.findOwnerByName("zas");
        assertThat(owner.size()).isEqualTo(0);
    }

    @Test
    void successfullyFindOwnerWithEmail(){
        Optional<Owner> owner = underTest.findOwnerByEmail("zac@gmail.com");
        assertThat(owner.isPresent()).isTrue();
        assertThat(owner.map(Owner::getName).orElse(null)).isEqualTo("zac");
    }

    @Test
    void failedToFindOwnerWithNonExistEmail(){
        Optional<Owner> owner = underTest.findOwnerByEmail("zac22@gmail.com");
        assertThat(owner.isPresent()).isFalse();
    }

    @Test
    void successfullyFindOwnerWithDogId(){
        Optional<Owner> owner = underTest.findByDogId("1");
        assertThat(owner.isPresent()).isTrue();
        assertThat(owner.map(Owner::getName).orElse(null)).isEqualTo("zac");
    }

    @Test
    void failedToFindOwnerWithNonExistDogId(){
        Optional<Owner> owner = underTest.findByDogId("9");
        assertThat(owner.isPresent()).isFalse();
    }

}
