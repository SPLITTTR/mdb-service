package com.splitttr.docs.dto;

public record DocumentCreateRequest(
    String title,
    String content,
    String ownerId
) {}
