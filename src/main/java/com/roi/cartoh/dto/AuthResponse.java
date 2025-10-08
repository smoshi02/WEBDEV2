package com.roi.cartoh.dto;

public record AuthResponse(String token, String username, Long expiresAt) {
}