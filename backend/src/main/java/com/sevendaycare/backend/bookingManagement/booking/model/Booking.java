package com.sevendaycare.backend.bookingManagement.booking.model;

import com.sevendaycare.backend.dogAndOwnerManagement.dog.model.Dog;
import com.sevendaycare.backend.dogAndOwnerManagement.owner.model.Owner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("booking")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    private String bookingId;

    private List<Dog> dogs;

    private Owner owner;

    private String serviceType;

    private String dateFrom;

    private String dateTo;

    private String comment;

    private String creationDate;

    private String modifiedDate;

    private Boolean isCancelled;

}
