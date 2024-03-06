package otmankarim.Capstone.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "performances")
@Getter
@Setter
@NoArgsConstructor
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
    @ManyToOne
    @JoinColumn(name = "freelancer_id")
    private User freelancer;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Performance(String title, String description, double price, String location, User freelancer, Category category) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.location = location;
        this.freelancer = freelancer;
        this.category = category;
    }
}
