package com.uade.tpo.demo.controllers.categories;

import com.uade.tpo.demo.entity.Category.CategoryType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CategoryRequest {
    private int id;
    private CategoryType categoryType;
}
