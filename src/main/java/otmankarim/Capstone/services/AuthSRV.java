package otmankarim.Capstone.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import otmankarim.Capstone.entities.Role;
import otmankarim.Capstone.entities.User;
import otmankarim.Capstone.exceptions.BadRequestException;
import otmankarim.Capstone.exceptions.NotFoundException;
import otmankarim.Capstone.exceptions.UnauthorizedException;
import otmankarim.Capstone.payloads.UserDTO;
import otmankarim.Capstone.payloads.UserLoginDTO;
import otmankarim.Capstone.repositories.RoleDAO;
import otmankarim.Capstone.repositories.UserDAO;
import otmankarim.Capstone.security.JWTTools;

@Service
public class AuthSRV {
    @Autowired
    private UserSRV userSRV;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private JWTTools jwtTools;

    public String authenticateUserAndGenerateToken(UserLoginDTO payload) {
        User user = userSRV.findByEmail(payload.email());
        if (bcrypt.matches(payload.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Wrong credentials!");
        }
    }

    public User save(UserDTO newUser) {
        userDAO.findByEmail(newUser.email()).ifPresent(user -> {
            throw new BadRequestException("User with email " + newUser.email() + " already exist!");
        });
        Role role = roleDAO.findByRole(newUser.role().toUpperCase()).orElseThrow(() -> new NotFoundException(newUser.role()));
        User user = new User(
                newUser.name(),
                newUser.surname(),
                newUser.email(),
                bcrypt.encode(newUser.password()),
                newUser.phone(),
                newUser.birthday(),
                role
        );
        return userDAO.save(user);
    }
}
