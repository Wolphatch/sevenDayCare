package com.sevendaycare.backend.dogAndOwnerManagement.service;

import com.sevendaycare.backend.dogAndOwnerManagement.dog.repository.DogDAO;
import com.sevendaycare.backend.dogAndOwnerManagement.owner.repository.OwnerDAO;
import com.sevendaycare.backend.dogAndOwnerManagement.request.DogCreationRequestMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OwnerService {
    private final DogDAO dogDao;

    private final OwnerDAO ownerDAO;

    public OwnerService(
            @Qualifier("dogMongoData") DogDAO dogDAO,
            @Qualifier("ownerMongoData") OwnerDAO ownerDAO,
            DogCreationRequestMapper dogCreationRequestMapper
    ){
        this.dogDao = dogDAO;
        this.ownerDAO = ownerDAO;
    }
}
