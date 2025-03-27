package api.gestion.users.api_gestion.controller;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.gestion.users.api_gestion.dto.UserRoleDTO;
import api.gestion.users.api_gestion.model.User;
import api.gestion.users.api_gestion.service.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }


    // Obtener todos los usuarios
    @GetMapping
    public Set<User> getAllUsers() {
        return userService.getAllUsers();
    }
    

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Actualizar un usuario
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = userService.updateUser(id, updatedUser);
        return ResponseEntity.ok(user);
    }

    // Eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Asignar un rol a un usuario
    @PostMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<UserRoleDTO> assignRoleToUser(@PathVariable Long userId, @PathVariable Long roleId) {
        UserRoleDTO userRoleDTO = userService.assignRoleToUser(userId, roleId);
        return ResponseEntity.status(HttpStatus.CREATED).body(userRoleDTO);
    }

    // Eliminar un rol de un usuario
    @DeleteMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<UserRoleDTO> removeRoleFromUser(@PathVariable Long userId, @PathVariable Long roleId) {
        UserRoleDTO userRoleDTO = userService.removeRoleFromUser(userId, roleId);
        return ResponseEntity.ok(userRoleDTO);
    }

    // Eliminar todos los roles de un usuario
    @DeleteMapping("/{userId}/roles")
    public ResponseEntity<Void> removeAllRolesFromUser(@PathVariable Long userId) {
        userService.removeAllRolesFromUser(userId);
        return ResponseEntity.noContent().build();
    }
}
