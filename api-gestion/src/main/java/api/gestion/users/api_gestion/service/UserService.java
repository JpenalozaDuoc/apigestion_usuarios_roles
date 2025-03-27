package api.gestion.users.api_gestion.service;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.gestion.users.api_gestion.dto.UserRoleDTO;
import api.gestion.users.api_gestion.model.Role;
import api.gestion.users.api_gestion.model.User;
import api.gestion.users.api_gestion.model.UserRole;
import api.gestion.users.api_gestion.repository.RoleRepository;
import api.gestion.users.api_gestion.repository.UserRepository;
import api.gestion.users.api_gestion.repository.UserRoleRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;
    
    
    // Crear un nuevo usuario
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Obtener todos los usuarios
    public Set<User> getAllUsers() {
        List<User> usersList = userRepository.findAll();
        return new HashSet<>(usersList);  // Convertir a Set
    }
    

    // Obtener un usuario por ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    //Eliminar un usuario
    public void deleteUser(Long userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(existingUser);
    }

    // Actualizar un usuario
    public User updateUser(Long userId, User updatedUser) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Actualiza los campos del usuario
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());  // En una aplicación real, cifrar la contraseña

        return userRepository.save(existingUser);
    }

    // Asignar un rol a un usuario
    public UserRoleDTO assignRoleToUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // Verificar si ya existe un UserRole para este usuario y rol
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);

        userRole = userRoleRepository.save(userRole);

        // Retornamos el DTO
        return new UserRoleDTO(userRole.getId(), user.getId(), role.getId());
    }

    // Quitar un rol de un usuario
    public UserRoleDTO removeRoleFromUser(Long userId, Long roleId) {
        // Buscar la relación UserRole entre usuario y rol
        UserRole userRole = userRoleRepository.findByUserIdAndRoleId(userId, roleId);
        if (userRole != null) {
            userRoleRepository.delete(userRole);
        } else {
            throw new RuntimeException("Role not assigned to this user");
        }

        // Devolver el DTO del UserRole eliminado
        return new UserRoleDTO(userRole.getId(), userId, roleId);
    }

    // Eliminar todos los roles de un usuario
    public void removeAllRolesFromUser(Long userId) {
        userRoleRepository.deleteByUserId(userId);
    }
    
}
