package otmankarim.Capstone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import otmankarim.Capstone.entities.User;
import otmankarim.Capstone.services.UserSRV;

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
    public void delete(@PathVariable UUID id) {
        this.userSRV.delete(id);
    }

}
