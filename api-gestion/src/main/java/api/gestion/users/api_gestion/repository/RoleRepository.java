package api.gestion.users.api_gestion.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import api.gestion.users.api_gestion.model.Role;

public interface RoleRepository extends JpaRepository<Role,Long>{


}
