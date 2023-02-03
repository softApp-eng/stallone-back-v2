package far.insp.sirhat.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class AppUserDto {

    private String userid;

    @NotBlank
    private String username;

    //@NotBlank
    private String password;

    @NotBlank
    private String origine;

    private String avatar;

    private Boolean AccountStatus = false;

    private List<Long> rolesId;

    private List<String> roles;
}
