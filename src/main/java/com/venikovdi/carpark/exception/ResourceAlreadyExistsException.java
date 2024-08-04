package com.venikovdi.carpark.exception;

import jakarta.validation.constraints.NotNull;

import static java.lang.String.format;

public class ResourceAlreadyExistsException extends RuntimeException{
    public ResourceAlreadyExistsException(@NotNull String resourceType, @NotNull String resourceKey) {
        super(format("%s '%s' already exists", resourceType, resourceKey));
    }
}
