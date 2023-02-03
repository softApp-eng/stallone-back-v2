package far.insp.sirhat.presentation.controller;

import far.insp.sirhat.presentation.dto.AppUserDto;
import far.insp.sirhat.security.services.UserPrincipal;
import far.insp.sirhat.service.face.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping("get")
    public ResponseEntity<AppUserDto> getCurrentUser(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK).body(appUserService.getCurrentUser(userPrincipal));
    }

}
