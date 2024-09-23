package com.se.sos.domain.category.dataloader;

import com.se.sos.domain.category.entity.Category;
import com.se.sos.domain.category.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public DataLoader(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> categories = Arrays.asList(
                "내과", "정형외과", "소아과", "피부과", "이비인후과",
                "정신건강의학과", "산부인과", "치과", "신경과", "응급의학과"
        );

        for (String categoryName : categories) {
                Category category = new Category(categoryName);
                categoryRepository.save(category);
        }
    }
}


