package com.sevendaycare.backend.dogAndOwnerManagement.request;

import com.sevendaycare.backend.dogAndOwnerManagement.dog.model.Dog;
import com.sevendaycare.backend.dogAndOwnerManagement.owner.model.Owner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class DogCreationRequestMapper {
    private DogCreationRequest dogCreationRequest;
    private Dog dog;

    public Dog mapDogCreationRequestToDog(DogCreationRequest request){
        return Dog.builder()
                .name(request.name())
                .nickName(request.nickName())
                .breed(request.breed())
                .gender(request.gender())
                .desexed(request.desexed())
                .yearOfBirth(request.yearOfBirth())
                .age(request.age())
                .weight(request.weight())
                .ownerEmail(request.ownerEmail())
                .ownerPhone(request.ownerPhone())
                .build();
    }
}
