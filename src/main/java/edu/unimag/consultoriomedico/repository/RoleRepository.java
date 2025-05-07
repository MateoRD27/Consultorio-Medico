package edu.unimag.consultoriomedico.repository;

import edu.unimag.consultoriomedico.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
