package api.gestion.users.api_gestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import api.gestion.users.api_gestion.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole,Long>{

    // Método para encontrar un UserRole por usuario y rol (útil para eliminar un rol asignado a un usuario)
    UserRole findByUserIdAndRoleId(Long userId, Long roleId);

    // Método para eliminar todos los roles de un usuario (en caso de eliminar todos sus roles)
    void deleteByUserId(Long userId);
}
