package api.gestion.users.api_gestion.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import api.gestion.users.api_gestion.model.User;



public interface UserRepository extends JpaRepository<User,Long> {

}
