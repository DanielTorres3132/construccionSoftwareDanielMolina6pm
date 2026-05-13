package app.application.adapters.persistence.sql.persistenceAdapters;

import app.application.adapters.persistence.sql.entities.RoleEntity;
import app.application.adapters.persistence.sql.repositories.RoleRepository;
import app.domain.models.User.User;
import app.domain.models.User.enums.SystemRole;
import app.domain.models.User.enums.UserStatus;
import app.domain.ports.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

// NOTA: Este adapter maneja la persistencia relacionada con seguridad/autenticación.
// Implementa UserRepositoryPort usando la tabla SQL de usuarios (UserEntity).
// También interactúa con RoleRepository para gestionar los roles del sistema.
//
// En esta arquitectura, los usuarios se persisten en MongoDB (ver MongoUserPersistenceAdapter
// si existe), pero los roles de seguridad se gestionan desde SQL con RoleEntity.
// Este adapter conecta ambos mundos para el proceso de autenticación.

@Component
@RequiredArgsConstructor
public class AuthPersistenceAdapter {

    private final RoleRepository roleRepository;

    /**
     * Busca un rol por su nombre (e.g. TELLER_EMPLOYEE, COMPANY_CLIENT).
     * Usado durante la autenticación para cargar los permisos del usuario.
     */
    public Optional<RoleEntity> findRoleByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    /**
     * Guarda un nuevo rol en la base de datos SQL.
     * Usado al inicializar los roles del sistema.
     */
    public RoleEntity saveRole(String roleName) {
        RoleEntity role = RoleEntity.builder()
                .name(roleName)
                .build();
        return roleRepository.save(role);
    }

    /**
     * Verifica si un rol ya existe en la base de datos.
     */
    public boolean roleExists(String roleName) {
        return roleRepository.findByName(roleName).isPresent();
    }

    /**
     * Inicializa todos los roles del sistema si no existen.
     * Llama a este método al arrancar la aplicación.
     */
    public void initializeSystemRoles() {
        for (SystemRole systemRole : SystemRole.values()) {
            if (!roleExists(systemRole.name())) {
                saveRole(systemRole.name());
            }
        }
    }
}