package otmankarim.Capstone.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "id", nullable = false)
    private UUID id;
    private int evaluation;
    private String title;
    private String comment;
    private LocalDate date;
    private LocalDate lastEditDate;
    private boolean edited;

    @ManyToOne
    @JoinColumn(name = "performance_id")
    private Performance performance;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    public Review(int evaluation, String title, String comment, Performance performance, User client) {
        this.evaluation = evaluation;
        this.title = title;
        this.comment = comment;
        this.date = LocalDate.now();
        this.performance = performance;
        this.client = client;
    }
}
