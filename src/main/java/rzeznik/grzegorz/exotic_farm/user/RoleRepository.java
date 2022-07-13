package rzeznik.grzegorz.exotic_farm.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("SELECT r FROM Role r WHERE lower(r.roleName)=lower(?1)")
    Role findByRoleName(String roleName);
}
