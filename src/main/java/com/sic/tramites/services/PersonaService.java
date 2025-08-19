package com.sic.tramites.services;

import com.sic.tramites.dto.PersonaDTO;
import com.sic.tramites.mapper.PersonaMapper;
import com.sic.tramites.model.Persona;
import com.sic.tramites.model.Usuario;
import com.sic.tramites.model.enums.Rol;
import com.sic.tramites.repository.PersonaRepository;
import com.sic.tramites.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonaService {

    private final PersonaRepository repo;
    private final PersonaMapper mapper;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public List<PersonaDTO> listar() {
        return repo.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    public Optional<PersonaDTO> obtener(Long id) {
        return repo.findById(id).map(mapper::toDTO);
    }

    @Transactional
    public PersonaDTO crear(PersonaDTO dto) {
        // Guardar persona
        Persona entity = mapper.toEntity(dto);
        Persona savedPersona = repo.save(entity);

        // Crear usuario asociado
        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername() != null ? dto.getUsername() : dto.getNumeroIdentificacion());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword() != null ? dto.getPassword() : "1234"));
        usuario.setRol(Rol.ROLE_PERSONA);
        usuario.setPersona(savedPersona);

        usuarioRepository.save(usuario);

        return mapper.toDTO(savedPersona);
    }

    @Transactional
    public Optional<PersonaDTO> actualizar(Long id, PersonaDTO dto) {
        return repo.findById(id).map(p -> {
            p.setNombres(dto.getNombres());
            p.setApellidos(dto.getApellidos());
            p.setTelefono(dto.getTelefono());
            p.setDireccion(dto.getDireccion());
            p.setEmail(dto.getEmail());
            return mapper.toDTO(repo.save(p));
        });
    }

    public boolean eliminar(Long id) {
        return repo.findById(id).map(p -> {
            repo.delete(p);
            return true;
        }).orElse(false);
    }
}
