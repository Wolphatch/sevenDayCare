package com.sevendaycare.backend.dogAndOwnerManagement.dog.controller;

import com.sevendaycare.backend.dogAndOwnerManagement.request.DogBatchCreationRequest;
import com.sevendaycare.backend.dogAndOwnerManagement.request.DogBatchUpdateRequest;
import com.sevendaycare.backend.dogAndOwnerManagement.request.DogCreationRequest;
import com.sevendaycare.backend.dogAndOwnerManagement.service.DogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("api/v1/dogs")
public class DogController {

    public final DogService dogService;

    public DogController (DogService dogService){
        this.dogService = dogService;
    }
    @RequestMapping(method = RequestMethod.POST, path= "create")
    public void createDog(@RequestBody DogCreationRequest request) throws Exception{
        dogService.addDog(request);
    }

    @RequestMapping(method = RequestMethod.POST, path= "batchCreate")
    public void batchCreateDog(@RequestBody DogBatchCreationRequest request) throws Exception{
        dogService.addDogs(request);
    }

    @RequestMapping(method = RequestMethod.PUT, path="batchUpdate")
    public void batchUpdateDog(@RequestBody DogBatchUpdateRequest request) throws Exception{
        dogService.updateDogs(request);
    }
}
