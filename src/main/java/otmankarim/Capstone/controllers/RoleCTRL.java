package otmankarim.Capstone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import otmankarim.Capstone.entities.Role;
import otmankarim.Capstone.exceptions.BadRequestException;
import otmankarim.Capstone.payloads.RoleDTO;
import otmankarim.Capstone.services.RoleSRV;

import java.util.UUID;

@RestController
@RequestMapping("/roles")
public class RoleCTRL {
    @Autowired
    private RoleSRV roleSRV;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Role> getRoles(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id") String orderBy) {
        return this.roleSRV.getRoles(page, size, orderBy);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Role getRoleById(@PathVariable UUID id) {
        return this.roleSRV.getRoleById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Role save(@RequestBody @Validated RoleDTO newRole, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.roleSRV.save(newRole);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        this.roleSRV.delete(id);
    }
}
