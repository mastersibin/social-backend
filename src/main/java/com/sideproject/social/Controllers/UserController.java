package com.sideproject.social.Controllers;

import com.sideproject.social.Entities.User;
import com.sideproject.social.RequestBodies.RegistrationRequestBody;
import com.sideproject.social.RequestBodies.UserRequestBody;
import com.sideproject.social.Services.UserService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    private String FS = System.getProperty("file.separator");

    private String imageStore = System.getProperty("user.dir") + FS + "ImageStore" + FS;

    private String defaultProfileImageURL = imageStore+"defaultProfile.png";

    @PostMapping(value = "/")
    public ResponseEntity<String> addUser(RegistrationRequestBody registrationRequestBody, @RequestParam(required = false, value = "profileImage") MultipartFile profileImage)
    {
        User user = new User(null, registrationRequestBody.getUsername(), registrationRequestBody.getPassword(), defaultProfileImageURL);
        if (user.getUsername() == null || user.getPassword() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username or Password Empty");
        if (profileImage != null)
        {
            String imageURL = imageStore+user.getUsername()+"."+ FilenameUtils.getExtension(registrationRequestBody.getFilename());
            user.setProfileImageURL(imageURL);
            Path imagePath = Paths.get(imageURL);
            try {
                OutputStream os = Files.newOutputStream(imagePath);
                os.write(profileImage.getBytes());
                os.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (userService.addUser(user))
            return ResponseEntity.status(HttpStatus.CREATED).body("User created");
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("User not created");
    }

    @GetMapping(value = "/")
    public ArrayList<User> getUser(@RequestParam(required = false, value = "id") Integer userId)
    {
        ArrayList<User> response = new ArrayList<>();
        if (userId != null)
            response.add(userService.getUser(userId));
        else
            response =  userService.getALlUsers();
        return response;
    }


    @PutMapping(value = "/")
    public ResponseEntity<String> editUser(UserRequestBody user, @RequestParam(required = false, value = "profileImage") MultipartFile profileImage)
    {
        if (user.getUserId() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        if (userService.editUser(new User(user)))
            return ResponseEntity.status(HttpStatus.OK).body("Updated Successfully");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
    }

    @GetMapping(value = "/getImage/")
    public ResponseEntity<InputStreamResource> getImage(@RequestParam(required = true, name = "id") Integer userId) throws Exception
    {
        User user = userService.getUser(userId);
        FileSystemResource fls = new FileSystemResource(user.getProfileImageURL());
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(fls.getInputStream()));
    }

    @GetMapping(value = "/getVideo", produces = {"video/mp4", "image/jpeg", "image/png"})
    public FileSystemResource getVideo()
    {
        return new FileSystemResource(imageStore+"defaultProfile.png");
    }



}
