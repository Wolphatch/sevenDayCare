package com.sevendaycare.backend.dogAndOwnerManagement.request;


public record DogCreationRequest(
    String name,

    String nickName,

    String breed,

    String gender,

    Boolean desexed,

    int yearOfBirth,

    int age,

    double weight,

    String ownerPhone,
    String ownerEmail,

    String comment
) {
}
