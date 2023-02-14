package com.sideproject.social.Services;

import com.sideproject.social.Entities.User;
import com.sideproject.social.Respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean addUser(User user)
    {
        if(userRepository.save(user) != null)
            return true;
        return false;
    }

    public User getUser(Integer userId)
    {
        return userRepository.findById(userId).orElseGet(null);
    }

    public ArrayList<User> getALlUsers()
    {
        ArrayList<User> users = new ArrayList<>();
        for (User user: userRepository.findAll())
            users.add(user);
        return users;
    }

    public boolean editUser(User user)
    {
        Integer userId = user.getUserId();
        if (userId == null)
            return false;
        User updatedUser = getUser(userId);
        if (user.getUsername() != null)
            updatedUser.setUsername(user.getUsername());
        if (user.getPassword() != null)
            updatedUser.setPassword(user.getPassword());
        if (user.getProfileImageURL() != null)
            updatedUser.setProfileImageURL(user.getProfileImageURL());
        userRepository.save(updatedUser);
        return true;
    }
}
