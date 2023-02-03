package far.insp.sirhat.presentation.controller;

import far.insp.sirhat.presentation.dto.ApiResponse;
import far.insp.sirhat.presentation.dto.AppUserDto;
import far.insp.sirhat.service.face.AppUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {


    @Autowired
    private AppUserService appUserService;


    @GetMapping("/user/all")
    public ResponseEntity<List<AppUserDto>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(appUserService.getAllUsers());
    }

    @GetMapping("/user/get/{id}")
    public ResponseEntity<AppUserDto> getUserById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(appUserService.getUserById(id));
    }

    @GetMapping("/roles")
    public ResponseEntity<?> getRoles() {
        return ResponseEntity.status(HttpStatus.OK).body(appUserService.getRoles());
    }

    @PostMapping("/user/add")
    public ResponseEntity<ApiResponse> addUser(@Valid @RequestBody AppUserDto appUserDto) {
        appUserService.addUser(appUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true,"user bien ajouté."));
    }

    @PostMapping("/user/mdp/{userid}")
    public ResponseEntity<ApiResponse> editMdpUser(@NotBlank @NotEmpty @RequestBody String password,
                                                   @PathVariable String userid) {
        appUserService.editUserMdp(password,userid);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true,"Mot de pass modifié."));
    }

    @PostMapping("/user/edit")
    public ResponseEntity<ApiResponse> editUser(@Valid @RequestBody AppUserDto appUserDto) {
        appUserService.editUser(appUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true,"user bien modifié."));
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String id) {
        appUserService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true,"user bien supprimé."));
    }

}
