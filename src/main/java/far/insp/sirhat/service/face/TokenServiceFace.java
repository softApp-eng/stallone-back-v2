package far.insp.sirhat.service.face;

import far.insp.sirhat.persistance.entity.AppUser;
import far.insp.sirhat.presentation.dto.Token;

import java.util.Optional;

public interface TokenServiceFace
{
    Token generateToken(String userid);
    Optional<AppUser> findUserById(String id);
}
