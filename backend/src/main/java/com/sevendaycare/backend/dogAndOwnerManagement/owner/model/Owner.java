package com.sevendaycare.backend.dogAndOwnerManagement.owner.model;

import com.sevendaycare.backend.dogAndOwnerManagement.dog.model.Dog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("owner")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Owner {
    @Id
    private String ownerId;

    private String name;

    private String email;

    private String phone;

    private String redBookName;

    private List<String> dogs;




}
