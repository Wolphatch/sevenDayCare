package com.sevendaycare.backend.dogAndOwnerManagement.dog.model;


import com.sevendaycare.backend.dogAndOwnerManagement.owner.model.Owner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("dog")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dog {
    @Id
    private String dogId;

    private String name;

    private String nickName;

    private String breed;

    private String gender;

    private Boolean desexed;

    private int yearOfBirth;

    private int age;

    private double weight;

    private String ownerPhone;

    private String ownerEmail;

    private String comment;



}
