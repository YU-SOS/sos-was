package com.se.sos.domain.category.dto;

import com.se.sos.domain.category.entity.Category;

public record CategoryRes(
        Long id,
        String name
) {
    public static CategoryRes from(Category category) {
        return new CategoryRes(
                category.getId(),
                category.getName()
        );
    }
}
