package com.sevendaycare.backend.dogAndOwnerManagement.request;


import java.util.List;

public record DogBatchCreationRequest(
    List<DogCreationRequest> dogCreationRequests
) {
}
