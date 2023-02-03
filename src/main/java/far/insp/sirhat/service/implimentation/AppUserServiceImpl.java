package far.insp.sirhat.service.implimentation;

import far.insp.sirhat.exception.BadRequestException;
import far.insp.sirhat.mapper.AppUserMapper;
import far.insp.sirhat.persistance.entity.AppRole;
import far.insp.sirhat.persistance.entity.AppUser;
import far.insp.sirhat.persistance.repository.AppRoleRepository;
import far.insp.sirhat.persistance.repository.AppUserRepository;
import far.insp.sirhat.presentation.dto.AppUserDto;
import far.insp.sirhat.presentation.dto.PasswordResetDto;
import far.insp.sirhat.security.services.UserPrincipal;
import far.insp.sirhat.service.face.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AppRoleRepository appRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void addUser(AppUserDto appUserDto) {
        if (appUserRepository.existsByUsername(appUserDto.getUsername()))
            throw new BadRequestException("email deja existe");
        AppUser appUser = AppUserMapper.toEntity(appUserDto);
        appUser.setId(UUID.randomUUID().toString());
        appUser.setPassword(passwordEncoder.encode(appUserDto.getPassword()));
        appUser.setRoles(appRoleRepository.findAllById(appUserDto.getRolesId()));
        appUserRepository.save(appUser);
        log.info("user "+appUserDto.getUsername()+" added");
    }

    @Override
    public void editUser(AppUserDto appUserDto) {
        AppUser appUser = appUserRepository.findById(appUserDto.getUserid())
                .orElseThrow(()->new BadRequestException("user not found"));
        AppUser newAppUser = AppUserMapper.toEntity(appUserDto);
        newAppUser.setPassword(appUser.getPassword());
        newAppUser.setRoles(appRoleRepository.findAllById(appUserDto.getRolesId()));
        newAppUser.setId(appUser.getId());
        newAppUser.setId(appUser.getAvatar());
        appUserRepository.save(newAppUser);
        log.info("user "+appUserDto.getUsername()+" updated");
    }

    @Override
    public void deleteUser(String id) {
        appUserRepository.deleteById(id);
        log.info("user deleted "+id);
    }

    @Override
    public AppUserDto getCurrentUser(UserPrincipal userPrincipal) {
        AppUser user = appUserRepository.findById(userPrincipal.getId()).orElseThrow(()->new BadRequestException("User introuvable"));
        //log.info("getting connected user "+user.getUsername());
        return AppUserMapper.toDto(user);
    }

    @Override
    public List<AppUserDto> getAllUsers() {
        return AppUserMapper.toDtos(appUserRepository.findAll());
    }

    @Override
    public AppUserDto getUserById(String id) {
        AppUser appUser = appUserRepository.findById(id)
                .orElseThrow(()->new BadRequestException("user not found"));
        return AppUserMapper.toDto(appUser);
    }

    @Override
    public List<AppRole> getRoles() {
        return appRoleRepository.findAll();
    }

    @Override
    public Boolean resetPassword(PasswordResetDto dto, UserPrincipal userPrincipal) {
        if ( !passwordEncoder.matches(dto.getCurrentPassword(),userPrincipal.getPassword()) )
            throw new BadRequestException("Mot de passe actuelle est invalide.");
        AppUser user = appUserRepository.findById(userPrincipal.getId())
                .orElseThrow(()->new BadRequestException("Invalide user"));
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        appUserRepository.save(user);
        log.info("password updated for user "+ user.getUsername());
        return true;
    }

    @Override
    public Boolean editUserMdp(String password, String userid) {
        AppUser appUser = appUserRepository.findById(userid)
                .orElseThrow(()->new BadRequestException("user not found"));
        appUser.setPassword(passwordEncoder.encode(password));
        appUserRepository.save(appUser);
        log.info("password updated for user "+ appUser.getUsername());
        return true;
    }
    @Override
    public Boolean isHasRoleSpa(String username) {
        AppUser appUser = appUserRepository.findByUsernameAndAccountStatusTrue(username)
                .orElseThrow(()->new BadRequestException("user not found"));
        return appUser.getRoles().stream().anyMatch(o->"SPA".equals(o.getRole()));
    }
}
