package app.dto;

import jakarta.validation.constraints.*;


public record ProdutosDTO(
        @NotNull(message = "ID não pode ser nulo.") Long id,
        @NotBlank @Size(min = 3, max = 100) String name,
        @Positive Double price
) {}
