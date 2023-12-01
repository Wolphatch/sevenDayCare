package com.sevendaycare.backend.dogAndOwnerManagement.repository;


import com.sevendaycare.backend.dogAndOwnerManagement.dog.model.Dog;
import com.sevendaycare.backend.dogAndOwnerManagement.dog.repository.DogMongoDataAccessService;
import com.sevendaycare.backend.dogAndOwnerManagement.dog.repository.DogRepository;
import com.sevendaycare.backend.dogAndOwnerManagement.owner.model.Owner;
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

import static org.assertj.core.api.Assertions.*;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("dev")
public class DogMongoDataAccessTest {
    DogMongoDataAccessService underTest;

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    DogRepository dogRepository;

    @BeforeEach
    void setup(){
        underTest = new DogMongoDataAccessService(dogRepository, ownerRepository);
        dogRepository.deleteAll();
        ownerRepository.deleteAll();
        Owner owner = Owner.builder()
                .ownerId("0")
                .dogs(new ArrayList<>())
                .phone("0000000000")
                .email("qwe@qwe.com")
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
                .ownerEmail("qwe@qwe.com")
                .ownerPhone("0000000000")
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
                .ownerEmail("qwe@qwe.com")
                .ownerPhone("0000000000")
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
    public void addDogSuccessfully(){
        Owner owner = Owner.builder()
                .ownerId("4")
                .dogs(new ArrayList<>())
                .name("mary")
                .build();

        Dog dog = Dog.builder()
                .dogId("3")
                .name("One")
                .nickName("one")
                .breed("Swiss Shp")
                .yearOfBirth(2022)
                .age(3)
                .weight(31.00)
                .gender("male")
                .desexed(true)
                .build();
        ownerRepository.save(owner);
        underTest.insertDog(dog);

       Optional<Dog> result =  underTest.findDogByDogId("3");
       assertThat(result.isPresent()).isTrue();
       assertThat(result.map(Dog::getName).orElse(null)).isEqualTo("One");
    }

    @Test
    void successfullyQueryAllDogs(){
        List<Dog> dogs = underTest.findAllDogs();
        assertThat(dogs.size()).isEqualTo(2);
        assertThat(dogs.get(0).getName()).isNotBlank();
        assertThat(dogs.get(1).getName()).isNotBlank();
    }

    @Test
    void queryReturnEmptyListWhenThereIsNoDog(){
        dogRepository.deleteAll();
        List<Dog> dogs = underTest.findAllDogs();
        assertThat(dogs.size()).isEqualTo(0);
    }

    @Test
    void successfullyFindDogById(){
        Optional<Dog> dog = underTest.findDogByDogId("1");
        assertThat(dog.isPresent()).isTrue();
        assertThat(dog.get().getName()).isEqualTo("Eleven");
    }

    @Test
    void emptyIsReturnedWhenDogIsNotFoundById(){
        Optional<Dog> dog = underTest.findDogByDogId("3");
        assertThat(dog.isEmpty()).isTrue();
    }

    @Test
    void successfullyFindDogWithDogNameAndBreed(){
        Dog dog3 = Dog.builder()
                .dogId("2")
                .name("One")
                .nickName("one")
                .breed("German Shp")
                .yearOfBirth(2013)
                .age(10)
                .weight(51.00)
                .desexed(false)
                .gender("male")
                .build();
        dogRepository.save(dog3);

        List<Dog> dogs = underTest.findByNameAndBreed("One","German Shp");
        assertThat(dogs.size()).isEqualTo(1);
        assertThat(dogs.get(0).getNickName()).isEqualTo("one");
    }

    @Test
    void successfullyFindDogWithOwnerEmail(){

        List<Dog> dogs = underTest.findByOwnerEmail("qwe@qwe.com");
        assertThat(dogs.size()).isEqualTo(2);
        assertThat(dogs.get(0).getNickName()).isNotNull();
    }

    @Test
    void successfullyFindDogWithName(){

        List<Dog> dogs = underTest.findDogByName("Seven");
        assertThat(dogs.size()).isEqualTo(1);
        assertThat(dogs.get(0).getNickName()).isNotNull();
    }

    @Test
    void successfullyFindDogWithGender(){

        List<Dog> dogs = underTest.findByGender("male");
        assertThat(dogs.size()).isEqualTo(2);
        assertThat(dogs.get(0).getNickName()).isNotNull();
    }

    @Test
    void successfullyFindDogWithDesexed(){

        List<Dog> dogs = underTest.findByDesexed(true);
        assertThat(dogs.size()).isEqualTo(2);
        assertThat(dogs.get(0).getNickName()).isNotNull();
    }

    @Test
    void successfullyCheckDogExistsByNameAndOwnerPhone(){
        Boolean exist = underTest.existsDogByNameAndOwnerPhone("Seven","0000000000");
        assertThat(exist).isTrue();
    }

    @Test
    void failedToCheckDogExistsByNonExistOwnerPhone(){
        Boolean exist = underTest.existsDogByNameAndOwnerPhone("Seven","00000000000");
        assertThat(exist).isFalse();
    }

    @Test
    void failedToCheckDogExistsByNonExistName(){
        Boolean exist = underTest.existsDogByNameAndOwnerPhone("Sevens","0000000000");
        assertThat(exist).isFalse();
    }


    @Test
    void successfullyCheckDogExistsByNameAndOwnerEmail(){
        Boolean exist = underTest.existsDogByNameAndOwnerEmail("Seven","qwe@qwe.com");
        assertThat(exist).isTrue();
    }

    @Test
    void failedToCheckDogExistsByNonExistOwnerEmail(){
        Boolean exist = underTest.existsDogByNameAndOwnerEmail("Seven","qwse@qwe.com");
        assertThat(exist).isFalse();
    }

    @Test
    void failedToCheckDogExistsByNonExistWithEmailMethod(){
        Boolean exist = underTest.existsDogByNameAndOwnerEmail("Sevens","qwe@qwe.com");
        assertThat(exist).isFalse();
    }

}
