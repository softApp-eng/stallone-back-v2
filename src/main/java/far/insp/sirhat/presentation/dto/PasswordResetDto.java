package far.insp.sirhat.presentation.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.validation.constraints.NotBlank;

@Getter @Setter @ToString
public class PasswordResetDto {

    @NotBlank
    private String currentPassword;

    @NotBlank
    private String newPassword;

}
