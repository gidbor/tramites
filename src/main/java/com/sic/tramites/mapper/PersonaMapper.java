package com.sic.tramites.mapper;

import com.sic.tramites.dto.PersonaDTO;
import com.sic.tramites.model.Persona;
import org.springframework.stereotype.Component;

@Component
public class PersonaMapper {

    public PersonaDTO toDTO(Persona entity) {
        PersonaDTO dto = new PersonaDTO();
        dto.setId(entity.getId());
        dto.setTipoIdentificacion(entity.getTipoIdentificacion());
        dto.setNumeroIdentificacion(entity.getNumeroIdentificacion());
        dto.setNombres(entity.getNombres());
        dto.setApellidos(entity.getApellidos());
        dto.setTelefono(entity.getTelefono());
        dto.setDireccion(entity.getDireccion());
        dto.setEmail(entity.getEmail());

        if (entity.getUsuario() != null) {
            dto.setUsername(entity.getUsuario().getUsername());
            // 🚫 nunca devolvemos el password en un response real,
            // pero lo dejo comentado para pruebas:
            // dto.setPassword(entity.getUsuario().getPassword());
        }
        return dto;
    }

    public Persona toEntity(PersonaDTO dto) {
        Persona entity = new Persona();
        entity.setId(dto.getId());
        entity.setTipoIdentificacion(dto.getTipoIdentificacion());
        entity.setNumeroIdentificacion(dto.getNumeroIdentificacion());
        entity.setNombres(dto.getNombres());
        entity.setApellidos(dto.getApellidos());
        entity.setTelefono(dto.getTelefono());
        entity.setDireccion(dto.getDireccion());
        entity.setEmail(dto.getEmail());
        return entity;
    }
}
