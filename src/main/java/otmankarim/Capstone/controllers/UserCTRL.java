package otmankarim.Capstone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import otmankarim.Capstone.entities.User;
import otmankarim.Capstone.exceptions.BadRequestException;
import otmankarim.Capstone.payloads.UserUpdateDTO;
import otmankarim.Capstone.services.UserSRV;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserCTRL {
    @Autowired
    public UserSRV userSRV;

    @GetMapping
    public Page<User> getUsers(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id") String orderBy) {
        return this.userSRV.getUsers(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable UUID id) {
        return this.userSRV.getUserById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID id) {
        this.userSRV.delete(id);
    }

    @GetMapping("/me")
    public User getMe(@AuthenticationPrincipal User user) {
        return this.userSRV.getUserById(user.getId());
    }

    @PutMapping("/me")
    @ResponseStatus(HttpStatus.CREATED)
    public User updateMe(@AuthenticationPrincipal User user, @RequestBody @Validated UserUpdateDTO updatedUser, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.userSRV.updateUserById(updatedUser, user.getId());
    }

    @PatchMapping("/me/uploadAvatar")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateMe(@AuthenticationPrincipal User user, @RequestParam("avatar") MultipartFile image) throws IOException {
        this.userSRV.uploadAndUpdateImage(image, user.getId());
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMe(@AuthenticationPrincipal User user) {
        this.userSRV.delete(user.getId());
    }

}
