package far.insp.sirhat.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AppUser implements Serializable {

    @Id
    private String id;

    @Column(nullable = false,unique = true)
    private String username;

    private String password;

    private String origine;

    private String avatar;

    private Boolean accountStatus;

    @ManyToMany()
    private List<AppRole> roles;

}
