package api.gestion.users.api_gestion.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.gestion.users.api_gestion.model.Role;
import api.gestion.users.api_gestion.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    // Crear un nuevo rol
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    // Obtener todos los roles
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Obtener un rol por ID
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    // Actualizar un rol
    public Role updateRole(Long roleId, Role updatedRole) {
        Role existingRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        existingRole.setRoleName(updatedRole.getRoleName());

        return roleRepository.save(existingRole);
    }

    // Eliminar un rol
    public void deleteRole(Long roleId) {
        Role existingRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        roleRepository.delete(existingRole);
    }
}
