package com.se.sos.domain.hospital.service;

import com.se.sos.domain.category.entity.Category;
import com.se.sos.domain.category.repository.CategoryHospitalRepository;
import com.se.sos.domain.category.repository.CategoryRepository;
import com.se.sos.domain.hospital.dto.HospitalReceptionRes;
import com.se.sos.domain.hospital.dto.HospitalRes;
import com.se.sos.domain.hospital.dto.HospitalUpdateReq;
import com.se.sos.domain.hospital.entity.Hospital;
import com.se.sos.domain.hospital.repository.HospitalRepository;
import com.se.sos.domain.reception.repository.ReceptionRepository;
import com.se.sos.global.exception.CustomException;
import com.se.sos.global.response.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class HospitalService {

    private final CategoryRepository categoryRepository;
    private final CategoryHospitalRepository categoryHospitalRepository;
    private final HospitalRepository hospitalRepository;
    private final ReceptionRepository receptionRepository;


    public Page<HospitalRes> getAllHospitals(Pageable pageable) {
        return hospitalRepository.findAll(pageable).map(HospitalRes::from);
    }

    @Transactional(readOnly = true)
    public Page<HospitalRes> getHospitalsByCategories(List<String> categoryList, Pageable pageable) {
        List<Category> categories = categoryRepository.findByNameIn(categoryList);
        int categorySize = categories.size();

        return categoryHospitalRepository.findHospitalsByCategories(categories, categorySize,pageable)
                .map(HospitalRes::from);
    }


    public HospitalRes findHospitalById(UUID id) {
        Hospital hospital = hospitalRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorType.HOSPITAL_NOT_FOUND));
        return HospitalRes.from(hospital);
    }

    @Transactional(readOnly = true)
    public Page<HospitalReceptionRes> findReceptionsById(UUID id, Pageable pageable) {
        return receptionRepository.findByHospital_Id(id,pageable)
                .map(HospitalReceptionRes::from);
    }

    @Transactional
    public HospitalRes updateHospitalById(UUID id, HospitalUpdateReq hospitalUpdateReq) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorType.HOSPITAL_NOT_FOUND));
        hospital.updateHospital(hospitalUpdateReq);
        List<Category> categories = categoryRepository.findByNameIn(hospitalUpdateReq.getCategories());
        hospital.updateCategories(categories);
        return HospitalRes.from(hospital);
    }

    @Transactional
    public HospitalRes updateEmergencyStatus(UUID id, boolean emergencyStatus) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorType.HOSPITAL_NOT_FOUND));
        hospital.updateEmergencyStatus(emergencyStatus);

        return HospitalRes.from(hospital);
    }
}
