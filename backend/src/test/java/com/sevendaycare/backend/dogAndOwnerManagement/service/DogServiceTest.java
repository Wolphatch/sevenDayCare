package com.sevendaycare.backend.dogAndOwnerManagement.service;


import com.sevendaycare.backend.dogAndOwnerManagement.dog.model.Dog;
import com.sevendaycare.backend.dogAndOwnerManagement.dog.repository.DogDAO;
import com.sevendaycare.backend.dogAndOwnerManagement.owner.model.Owner;
import com.sevendaycare.backend.dogAndOwnerManagement.owner.repository.OwnerDAO;
import com.sevendaycare.backend.dogAndOwnerManagement.request.DogBatchCreationRequest;
import com.sevendaycare.backend.dogAndOwnerManagement.request.DogCreationRequest;
import com.sevendaycare.backend.dogAndOwnerManagement.request.DogCreationRequestMapper;
import com.sevendaycare.backend.dogAndOwnerManagement.request.DogUpdateRequestMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class DogServiceTest {

    private DogService underTest;

    @Mock
    DogDAO dogDAO;

    @Mock
    OwnerDAO ownerDAO;

    DogCreationRequestMapper dogCreationRequestMapper;

    DogUpdateRequestMapper dogUpdateRequestMapper;



    @Captor
    private ArgumentCaptor<ArrayList<Dog>> dogListArgumentCaptor;

    @BeforeEach
    void setup(){
        dogCreationRequestMapper = new DogCreationRequestMapper();
        underTest = new DogService(dogDAO,ownerDAO,dogCreationRequestMapper,dogUpdateRequestMapper);
    }
    
    @Test
    void successfullyAddSingleDog()throws  Exception{
        DogCreationRequest request = new DogCreationRequest(
                 "One" ,
                 "one" ,
                 "Swiss Shp" ,
                 "male",
                 true,
                 1995 ,
                 20 ,
                 12.23,
                "0000000000",
                "qwe@qwe.com",
                "no"
                );

        underTest.addDog(request);

        ArgumentCaptor<Dog>  dogArgumentCaptor = ArgumentCaptor.forClass(Dog.class);
        verify(dogDAO).insertDog(dogArgumentCaptor.capture());
        Dog capturedDog = dogArgumentCaptor.getValue();
        assertThat(capturedDog.getName()).isEqualTo("One");
        assertThat(capturedDog.getOwnerEmail()).isEqualTo("qwe@qwe.com");

    }

    @Test
    void successfullyAddDogWithBatch()throws  Exception{
        DogCreationRequest request = new DogCreationRequest(
                "One" ,
                "one" ,
                "Swiss Shp" ,
                "male",
                true,
                1995 ,
                20 ,
                12.23,
                "0000000000",
                "qwe@qwe.com",
                "no"
        );
        DogCreationRequest request2 = new DogCreationRequest(
                "Two" ,
                "two" ,
                "Swiss Shp" ,
                "male",
                true,
                1995 ,
                20 ,
                12.23,
                "0000000000",
                "qwe@qwe.com",
                "no"
        );
        DogBatchCreationRequest batchRequest = new DogBatchCreationRequest(
                new ArrayList<>(){{
                    add(request);
                    add(request2);
                }}
        );

        underTest.addDogs(batchRequest);

        verify(dogDAO).insertDogs(dogListArgumentCaptor.capture());
        ArrayList<Dog> capturedDog = dogListArgumentCaptor.getValue();
        assertThat(capturedDog.get(0).getName()).isEqualTo("One");
        assertThat(capturedDog.get(1).getOwnerEmail()).isEqualTo("qwe@qwe.com");
    }


}
