package otmankarim.Capstone.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import otmankarim.Capstone.entities.Role;
import otmankarim.Capstone.exceptions.BadRequestException;
import otmankarim.Capstone.exceptions.NotFoundException;
import otmankarim.Capstone.payloads.RoleDTO;
import otmankarim.Capstone.repositories.RoleDAO;

import java.util.UUID;

@Service
public class RoleSRV {
    @Autowired
    private RoleDAO roleDAO;

    public Page<Role> getRoles(int pageNum, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNum, size, Sort.by(orderBy));
        return roleDAO.findAll(pageable);
    }

    public Role save(RoleDTO newRole) {
        roleDAO.findByRole(newRole.role().toUpperCase()).ifPresent(role -> {
            throw new BadRequestException("Role " + newRole.role().toUpperCase() + " already exist!");
        });
        Role role = new Role(newRole.role());
        return roleDAO.save(role);
    }

    public Role getRoleById(UUID id) {
        return roleDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void delete(UUID id) {
        Role found = getRoleById(id);
        roleDAO.delete(found);
    }
}
