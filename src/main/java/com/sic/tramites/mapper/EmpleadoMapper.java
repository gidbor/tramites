package com.sic.tramites.mapper;

import com.sic.tramites.dto.EmpleadoDTO;
import com.sic.tramites.model.Empleado;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmpleadoMapper {
    EmpleadoDTO toDTO(Empleado entity);
    Empleado toEntity(EmpleadoDTO dto);
}