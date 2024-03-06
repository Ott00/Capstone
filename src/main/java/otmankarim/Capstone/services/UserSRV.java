package otmankarim.Capstone.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import otmankarim.Capstone.entities.User;
import otmankarim.Capstone.exceptions.NotFoundException;
import otmankarim.Capstone.payloads.UserUpdateDTO;
import otmankarim.Capstone.repositories.RoleDAO;
import otmankarim.Capstone.repositories.UserDAO;

import java.util.UUID;

@Service
public class UserSRV {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private PasswordEncoder bcrypt;

    public Page<User> getUsers(int pageNum, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNum, size, Sort.by(orderBy));
        return userDAO.findAll(pageable);
    }

    public User getUserById(UUID id) {
        return userDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User findByEmail(String email) {
        return userDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("Email " + email + " not found"));
    }

    public User updateUserById(UserUpdateDTO updatedUser, UUID id) {
        User found = getUserById(id);
        found.setName(updatedUser.name());
        found.setSurname(updatedUser.surname());
        found.setEmail(updatedUser.email());
        found.setPassword(updatedUser.password());
        found.setPhone(updatedUser.phone());
        found.setBirthday(updatedUser.birthday());
        return userDAO.save(found);
    }

    public void delete(UUID id) {
        User found = getUserById(id);
        userDAO.delete(found);
    }
}
