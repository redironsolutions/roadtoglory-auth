package io.rediron.auth.repository;

import io.rediron.auth.domain.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {}