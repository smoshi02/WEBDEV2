package com.roi.cartoh.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, Integer id) {
        super(resource + " with ID " + id + " not found.");
    }
}
