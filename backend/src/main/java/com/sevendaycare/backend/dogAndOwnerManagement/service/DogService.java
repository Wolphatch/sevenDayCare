package com.sevendaycare.backend.dogAndOwnerManagement.service;

import com.sevendaycare.backend.dogAndOwnerManagement.dog.model.Dog;
import com.sevendaycare.backend.dogAndOwnerManagement.dog.repository.DogDAO;
import com.sevendaycare.backend.dogAndOwnerManagement.exception.BadRequestException;
import com.sevendaycare.backend.dogAndOwnerManagement.exception.DepulicatedResourceException;
import com.sevendaycare.backend.dogAndOwnerManagement.exception.ResourceNotFoundException;
import com.sevendaycare.backend.dogAndOwnerManagement.owner.repository.OwnerDAO;
import com.sevendaycare.backend.dogAndOwnerManagement.request.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;

@Service
@Slf4j
public class DogService {
    private final DogDAO dogDao;

    private final OwnerDAO ownerDAO;

    private final DogCreationRequestMapper dogCreationRequestMapper;
    private final DogUpdateRequestMapper dogUpdateRequestMapper;

    public DogService(
            @Qualifier("dogMongoData") DogDAO dogDAO,
            @Qualifier("ownerMongoData") OwnerDAO ownerDAO,
            DogCreationRequestMapper dogCreationRequestMapper,
            DogUpdateRequestMapper dogUpdateRequestMapper
    ){
        this.dogDao = dogDAO;
        this.ownerDAO = ownerDAO;
        this.dogCreationRequestMapper = dogCreationRequestMapper;
        this.dogUpdateRequestMapper = dogUpdateRequestMapper;
    }

    public void addDog(DogCreationRequest request) throws Exception{
        try{
            checkDogNameAndOwner(request);

            // Check dog exists
            Dog newDog = dogCreationRequestMapper.mapDogCreationRequestToDog(request);

            log.info("Got Dog from request: " +newDog.toString() );

            if (dogDao.existsDogByNameAndOwnerEmail(newDog.getName(), newDog.getOwnerEmail())){
                throw new DepulicatedResourceException("DogAlreadyExit: Please don't add duplicated dogs under one owner");
            }
            dogDao.insertDog(newDog);
            log.info("Inserted: "+newDog.getDogId());
        }catch (Exception ex){
            log.error("Error: ",ex);
            throw ex;
        }
    }

    public void addDogs(DogBatchCreationRequest request) throws Exception{
        try{
            List<DogCreationRequest> requests = Optional.of(request)
                    .map(DogBatchCreationRequest::dogCreationRequests)
                    .orElseThrow(
                            ()-> new BadRequestException("FailedToParseCreationList: Should be a list of dogs with valid fields")
                    );

            Optional.of(requests)
                    .stream()
                    .flatMap(Collection::stream)
                    .filter(Objects::nonNull)
                    .forEach(this::checkDogNameAndOwner);

            List<Dog> dogs = new ArrayList<>();

            IntStream.range(0,requests.size()).forEach((i)->{
                Dog newDog = dogCreationRequestMapper.mapDogCreationRequestToDog(requests.get(i));
                if (dogDao.existsDogByNameAndOwnerEmail(newDog.getName(), newDog.getOwnerEmail())){
                    throw new DepulicatedResourceException("DogAlreadyExit: Please don't add duplicated dogs under one owner");
                }
                dogs.add(newDog);
                log.info("Got Dog from request to create: " +newDog.toString() );
            });
            dogDao.insertDogs(dogs);
        }
        catch (Exception ex){
            log.error("Error: ", ex);
            throw ex;
        }

    }

    public void checkDogNameAndOwner(DogCreationRequest request){
        String ownerPhone = Optional.of(request)
                .map(DogCreationRequest::ownerPhone)
                .orElse(null);

        String ownerEmail = Optional.of(request)
                .map(DogCreationRequest::ownerEmail)
                .orElse(null);

        if (Objects.equals(null,ownerEmail) && Objects.equals(null,ownerPhone)){
            log.warn("OwnerEmailAndPhoneIsMissing: Please add owner phone or email to avoid potential error");
        }

        Optional.of(request)
                .map(DogCreationRequest::name)
                .orElseThrow(
                        ()->new BadRequestException("DogNameIsMissing: Please include DogName in the request body")
                );

    }

    public void updateDogs(DogBatchUpdateRequest request){
        try{
            List<DogUpdateRequest> requests = Optional.of(request)
                    .map(DogBatchUpdateRequest::dogUpdateRequests)
                    .orElseThrow(
                            ()-> new BadRequestException("FailedToParseCreationList: Should be a list of dogs with valid fields")
                    );
            List<Dog> dogs = new ArrayList<>();
            IntStream.range(0,requests.size()).forEach((i)->{
                Dog newDog = dogUpdateRequestMapper.mapDogUpdateRequestToDog(requests.get(i));
                String dogId = Optional.of(newDog)
                        .map(Dog::getDogId)
                        .orElseThrow(
                                ()-> new ResourceNotFoundException("DogNotFound: Dog doesn't exist")
                        );
                Dog oldDog = dogDao.findDogByDogId(dogId).orElseThrow(
                        ()-> new ResourceNotFoundException("DogNotFound: Dog doesn't exist")
                );

                dogs.add(updateDogMapper(oldDog, newDog));
                log.info("Got Dog from request to update: " + newDog);
            });
            dogDao.updateDogs(dogs);
        } catch (Exception ex){
            log.error("Error: ", ex);
            throw ex;
        }
    }

    public  Dog updateDogMapper(Dog oldDog, Dog newDog){

        if (newDog.getName() == null && !Objects.equals(null, oldDog.getName())) {
            newDog.setName(oldDog.getName());
        }

        if (newDog.getNickName() == null && !Objects.equals(null, oldDog.getNickName())) {
            newDog.setNickName(oldDog.getNickName());
        }

        if (newDog.getBreed() == null && !Objects.equals(null, oldDog.getBreed())) {
            newDog.setBreed(oldDog.getBreed());
        }

        if (newDog.getGender() == null && !Objects.equals(null, oldDog.getGender())) {
            newDog.setGender(oldDog.getGender());
        }

        if (newDog.getDesexed() == null && !Objects.equals(null, oldDog.getDesexed())) {
            newDog.setDesexed(oldDog.getDesexed());
        }

        if (newDog.getYearOfBirth() == 0 && 0 != oldDog.getYearOfBirth()) {
            newDog.setYearOfBirth(oldDog.getYearOfBirth());
        }

        if (newDog.getAge() == 0 && 0 != oldDog.getAge()) {
            newDog.setAge(oldDog.getAge());
        }

        if (newDog.getWeight() == 0.0 && newDog.getWeight() != oldDog.getWeight()) {
            newDog.setWeight(oldDog.getWeight());
        }

        if (newDog.getOwnerPhone() == null && !Objects.equals(null, oldDog.getOwnerPhone())) {
            newDog.setOwnerPhone(oldDog.getOwnerPhone());
        }

        if (newDog.getOwnerEmail() == null && !Objects.equals(null, oldDog.getOwnerEmail())) {
            newDog.setOwnerEmail(oldDog.getOwnerEmail());
        }

        if (newDog.getComment() == null && !Objects.equals(null, oldDog.getComment())) {
            newDog.setComment(oldDog.getComment());
        }

        return newDog;

    }

}
