package com.sideproject.social.Respositories;

import com.sideproject.social.Entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
