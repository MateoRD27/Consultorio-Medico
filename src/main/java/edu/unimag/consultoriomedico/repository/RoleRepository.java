package edu.unimag.consultoriomedico.repository;

import edu.unimag.consultoriomedico.entity.Role;
import edu.unimag.consultoriomedico.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
