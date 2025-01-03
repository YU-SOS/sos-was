package com.se.sos.domain.hospital.service;

import com.se.sos.domain.category.entity.Category;
import com.se.sos.domain.category.repository.CategoryHospitalRepository;
import com.se.sos.domain.category.repository.CategoryRepository;
import com.se.sos.domain.hospital.dto.HospitalReceptionRes;
import com.se.sos.domain.hospital.dto.HospitalRes;
import com.se.sos.domain.hospital.dto.HospitalUpdateReq;
import com.se.sos.domain.hospital.entity.Hospital;
import com.se.sos.domain.hospital.repository.HospitalRepository;
import com.se.sos.domain.reception.entity.Reception;
import com.se.sos.domain.reception.entity.ReceptionStatus;
import com.se.sos.domain.reception.repository.ReceptionRepository;
import com.se.sos.global.exception.CustomException;
import com.se.sos.global.response.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    public List<HospitalRes> getAllHospitals() {
        return hospitalRepository.findAll().stream().map(HospitalRes::from).toList();
    }
    public List<HospitalReceptionRes> getReceptionByHospitalAndStatus(UUID id){
        List<ReceptionStatus> receptionStatuses = List.of(ReceptionStatus.MOVE,ReceptionStatus.ARRIVAL);
        return receptionRepository.findByHospital_IdAndReceptionStatusIn(id,receptionStatuses)
                .stream().map(HospitalReceptionRes::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<HospitalRes> getAllHospitalsByCategories(List<String> categoryList, Pageable pageable) {
        List<Long> categories = categoryRepository.findByNameIn(categoryList).stream()
                .map(Category::getId)
                .toList();

        return categoryHospitalRepository.findHospitalsByCategories(categories, pageable)
                .map(HospitalRes::from);
    }

    @Transactional(readOnly = true)
    public HospitalRes findHospitalById(UUID id) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorType.HOSPITAL_NOT_FOUND));
        return HospitalRes.from(hospital);
    }

    @Transactional(readOnly = true)
    public Page<HospitalReceptionRes> findReceptionsById(UUID id, Pageable pageable) {
        return receptionRepository.findByHospital_Id(id,pageable)
                .map(HospitalReceptionRes::from);
    }

    @Transactional
    public void updateHospitalById(UUID id, HospitalUpdateReq hospitalUpdateReq) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorType.HOSPITAL_NOT_FOUND));

        List<Category> categories = categoryRepository.findByNameIn(hospitalUpdateReq.getCategories());

        if(!isValidCategories(categories))
            throw new CustomException(ErrorType.CATEGORY_NOT_FOUND);

        hospital.updateHospital(hospitalUpdateReq);
        hospital.updateCategories(categories);
    }

    private boolean isValidCategories(List<Category> categories) {
        for(Category category : categories){
            if(!categoryRepository.existsByName(category.getName()))
                return false;
        }
        return true;
    }

    @Transactional
    public void updateEmergencyStatus(UUID id, boolean emergencyStatus) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorType.HOSPITAL_NOT_FOUND));

        hospital.updateEmergencyStatus(emergencyStatus);
    }
}
