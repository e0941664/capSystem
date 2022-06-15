package team2.capSystem.repo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

import team2.capSystem.model.*;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    
	Boolean existsBy();
	
	@Query("SELECT a from Admin a WHERE a.email = :email")
	Admin findAdminByEmail(@Param("email") String email);
}

