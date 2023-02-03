package far.insp.sirhat.service.implimentation;

import far.insp.sirhat.exception.BadRequestException;
import far.insp.sirhat.persistance.entity.AppUser;
import far.insp.sirhat.persistance.repository.AppUserRepository;
import far.insp.sirhat.presentation.dto.Token;
import far.insp.sirhat.security.jwt.JwtUtils;
import far.insp.sirhat.service.face.TokenServiceFace;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class TokenServiceImpl implements TokenServiceFace {

    public TokenServiceImpl(JwtUtils jwtUtils, AppUserRepository appUserRepository) {
        this.jwtUtils = jwtUtils;
        this.appUserRepository = appUserRepository;
    }

    private final JwtUtils jwtUtils;
    private final AppUserRepository appUserRepository;

    @Override
    public Token generateToken(String userid) {
        AppUser user = appUserRepository.findByIdAndAccountStatusTrue(userid)
                    .orElseThrow(()->new BadRequestException("user introuvable"));
        String accessToken = jwtUtils.generateAccessToken(
                user.getUsername(),
                user.getRoles().stream().map(r->r.getRole()).collect(Collectors.toList()),
                user.getOrigine()
                );
        String refreshToken = jwtUtils.generateRefreshToken(user.getId(),user.getOrigine());
        return new Token(accessToken,refreshToken);
    }

    @Override
    public Optional<AppUser> findUserById(String id) {
        return appUserRepository.findByIdAndAccountStatusTrue(id);
    }

}
