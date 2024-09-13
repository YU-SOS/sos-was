package com.se.sos.domain.paramedic.repository;

import com.se.sos.domain.paramedic.entity.Paramedic;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ParamedicRepository extends CrudRepository<Paramedic, UUID> {
}
