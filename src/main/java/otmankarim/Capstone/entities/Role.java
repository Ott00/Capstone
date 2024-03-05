package otmankarim.Capstone.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"users"})
public class Role {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "id", nullable = false)
    private UUID id;
    private String role;
    @OneToMany(mappedBy = "role")
    private Set<User> users = new LinkedHashSet<>();

    public Role(String role) {
        this.role = role.toUpperCase();
    }

    @JsonValue
    public String getRoleAsString() {
        return role;
    }
}
