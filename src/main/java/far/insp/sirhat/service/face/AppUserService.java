package far.insp.sirhat.service.face;

import far.insp.sirhat.persistance.entity.AppRole;
import far.insp.sirhat.presentation.dto.AppUserDto;
import far.insp.sirhat.presentation.dto.PasswordResetDto;
import far.insp.sirhat.security.services.UserPrincipal;

import java.util.List;

public interface AppUserService {
    void addUser(AppUserDto appUserDto);
    void editUser(AppUserDto appUserDto);
    void deleteUser(String id);
    AppUserDto getCurrentUser(UserPrincipal userPrincipal);
    List<AppUserDto> getAllUsers();
    AppUserDto getUserById(String id);
    List<AppRole> getRoles();
    Boolean resetPassword(PasswordResetDto dto, UserPrincipal userPrincipal);
    Boolean editUserMdp(String password,String userid);
    Boolean isHasRoleSpa(String username);
}
