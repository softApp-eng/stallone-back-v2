package far.insp.sirhat.presentation.controller;

import far.insp.sirhat.presentation.dto.TokenRefreshRequest;
import far.insp.sirhat.security.jwt.JwtUtils;
import far.insp.sirhat.security.services.UserPrincipal;
import far.insp.sirhat.presentation.dto.Token;
import far.insp.sirhat.service.face.TokenServiceFace;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import far.insp.sirhat.exception.TokenRefreshException;
import far.insp.sirhat.presentation.dto.LoginRequest;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

  public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, TokenServiceFace tokenService) {
    this.authenticationManager = authenticationManager;
    this.jwtUtils = jwtUtils;
    this.tokenService = tokenService;
  }

  private final AuthenticationManager authenticationManager;

  private final JwtUtils jwtUtils;

  private final TokenServiceFace tokenService;

  @PostMapping("/login")
  public ResponseEntity<Token> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
    Token token = tokenService.generateToken(userDetails.getId());
    return ResponseEntity.ok(token);
  }
  @PostMapping("/refreshtoken")
  public ResponseEntity<Token> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
    log.info("Refresh token");
    try {
      String requestRefreshToken = request.getRefreshToken();
      if (requestRefreshToken != null && jwtUtils.validateJwtToken(requestRefreshToken)) {
        return tokenService.findUserById(jwtUtils.getIdFromRefreshToken(requestRefreshToken))
                .map(user -> {
                  return ResponseEntity.ok(tokenService.generateToken(user.getId()));
                })
                .orElseThrow(() -> new TokenRefreshException("refreshtoken","Refresh token is invalide!"));
        }
      else throw new TokenRefreshException("refreshtoken","Refresh token is invalide!");
    } catch (Exception e) {
      throw new TokenRefreshException("refreshtoken", e.getMessage());
    }
  }

}
