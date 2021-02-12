package com.ertelecom.carrental.model.filter;

import com.fasterxml.jackson.annotation.JsonInclude;

public class RentalFilter {
    public Long carId = null;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public long[] carBrand;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public long[] carModel;
}
