package app.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Name is required.")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters.")
    private String name;

    @Column(nullable = false)
    @Positive(message = "Price must be positive.")
    private Double price;

    public Produto() {

    }
}
