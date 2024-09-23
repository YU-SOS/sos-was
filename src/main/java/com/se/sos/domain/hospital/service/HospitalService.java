package com.se.sos.domain.hospital.service;

import com.se.sos.domain.category.entity.Category;
import com.se.sos.domain.category.repository.CategoryHospitalRepository;
import com.se.sos.domain.hospital.entity.Hospital;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalService {

    private final CategoryHospitalRepository categoryHospitalRepository;
    public List<Hospital> getHospitalsByCategories (List<Category> categories){
        int categorySize = categories.size();
        return categoryHospitalRepository.findHospitalsByCategories(categories, categorySize);
    }
}
