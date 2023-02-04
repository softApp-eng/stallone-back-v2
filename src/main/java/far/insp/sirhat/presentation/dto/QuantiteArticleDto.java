package far.insp.sirhat.presentation.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuantiteArticleDto {

	@NotNull(message = "Veuillez choisir l'article")
	private Long articleId;

	@NotNull(message = "veuillez définir la quantité")
	@Min(value = 1,message = "La quantité doit étre valide")
	private Long quentite;

}
