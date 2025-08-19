package com.sic.tramites.services;

import com.sic.tramites.dto.EmpleadoDTO;
import com.sic.tramites.mapper.EmpleadoMapper;
import com.sic.tramites.model.Empleado;
import com.sic.tramites.model.Usuario;
import com.sic.tramites.model.enums.Rol;
import com.sic.tramites.repository.EmpleadoRepository;
import com.sic.tramites.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpleadoService {

    private final EmpleadoRepository repo;
    private final EmpleadoMapper mapper;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public List<EmpleadoDTO> listar() {
        return repo.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    public Optional<EmpleadoDTO> obtener(Long id) {
        return repo.findById(id).map(mapper::toDTO);
    }

    @Transactional
    public EmpleadoDTO crear(EmpleadoDTO dto) {
        // Crear empleado
        Empleado entity = mapper.toEntity(dto);
        Empleado savedEmpleado = repo.save(entity);

        // Crear usuario asociado
        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername() != null ? dto.getUsername() : dto.getCodigo());
        usuario.setPassword(passwordEncoder.encode(
                dto.getPassword() != null ? dto.getPassword() : "1234"
        ));
        usuario.setRol(Rol.ROLE_EMPLEADO);
        usuario.setEmpleado(savedEmpleado);

        usuarioRepository.save(usuario);

        return mapper.toDTO(savedEmpleado);
    }

    @Transactional
    public Optional<EmpleadoDTO> actualizar(Long id, EmpleadoDTO dto) {
        return repo.findById(id).map(e -> {
            e.setNombres(dto.getNombres());
            e.setApellidos(dto.getApellidos());
            e.setCodigo(dto.getCodigo());
            e.setFechaIngreso(dto.getFechaIngreso());
            return mapper.toDTO(repo.save(e));
        });
    }

    public boolean eliminar(Long id) {
        return repo.findById(id).map(e -> {
            repo.delete(e);
            return true;
        }).orElse(false);
    }
}
