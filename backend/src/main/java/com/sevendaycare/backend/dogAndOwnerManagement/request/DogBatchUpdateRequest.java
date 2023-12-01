package com.sevendaycare.backend.dogAndOwnerManagement.request;


import java.util.List;

public record DogBatchUpdateRequest(
    List<DogUpdateRequest> dogUpdateRequests
) {
}
