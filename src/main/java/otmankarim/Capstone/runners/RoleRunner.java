package otmankarim.Capstone.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import otmankarim.Capstone.payloads.RoleDTO;
import otmankarim.Capstone.repositories.RoleDAO;
import otmankarim.Capstone.services.RoleSRV;

import java.util.Arrays;
import java.util.List;

@Component
public class RoleRunner implements CommandLineRunner {
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private RoleSRV roleSRV;

    @Override
    public void run(String... args) throws Exception {
        List<String> rolesToCheck = Arrays.asList("CLIENT", "FREELANCER", "ADMIN");
        rolesToCheck.forEach(role -> {
            if (roleDAO.findByRole(role).isEmpty()) {
                roleSRV.save(new RoleDTO(role));
            }
        });
    }
}
