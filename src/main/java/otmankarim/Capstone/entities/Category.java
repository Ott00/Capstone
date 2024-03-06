package otmankarim.Capstone.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"performances"})
public class Category {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "id", nullable = false)
    private UUID id;
    private String name;
    @OneToMany(mappedBy = "category")
    private Set<Performance> performances = new LinkedHashSet<>();

    public Category(String name) {
        this.name = name.toLowerCase();
    }
}
