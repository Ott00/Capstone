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
@Table(name = "performances")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"appointments", "reviews"})
public class Performance {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "id", nullable = false)
    private UUID id;
    private String title;
    private String description;
    private double price;
    private String location;
    private String image;
    @ManyToOne
    @JoinColumn(name = "freelancer_id")
    private User freelancer;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "performance")
    private Set<Appointment> appointments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "performance")
    private Set<Review> reviews = new LinkedHashSet<>();

    public Performance(String title, String description, double price, String location, String image, User freelancer, Category category) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.location = location;
        this.image = image;
        this.freelancer = freelancer;
        this.category = category;
    }
}
