package com.sideproject.social.Respositories;

import com.sideproject.social.Entities.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Integer> {
}
