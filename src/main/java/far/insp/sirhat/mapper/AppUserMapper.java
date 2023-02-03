package far.insp.sirhat.mapper;

import far.insp.sirhat.persistance.entity.AppRole;
import far.insp.sirhat.persistance.entity.AppUser;
import far.insp.sirhat.presentation.dto.AppUserDto;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class AppUserMapper {

    public static AppUserDto toDto(AppUser user){
        if (user == null) {
            return AppUserDto.builder().build();
        }
        return AppUserDto.builder()
                .userid(user.getId())
                .username(user.getUsername())
                .AccountStatus(user.getAccountStatus())
                .origine(user.getOrigine())
                .avatar(user.getAvatar())
                .roles(user.getRoles().stream().map(AppRole::getRole).collect(Collectors.toList()))
                .rolesId(user.getRoles().stream().map(AppRole::getId).collect(Collectors.toList()))
                .build();
    }

    public static AppUser toEntity(AppUserDto dto){
        if (dto == null) {
            return AppUser.builder().build();
        }
        return AppUser.builder()
                .username(dto.getUsername())
                .accountStatus(dto.getAccountStatus())
                .origine(dto.getOrigine())
                .build();
    }

    public static List<AppUserDto> toDtos(List<AppUser> users) {
        return CollectionUtils.emptyIfNull(users).stream().map(AppUserMapper::toDto).collect(Collectors.toList());
    }

    public static List<AppUser> toentities(List<AppUserDto> dto) {
        return CollectionUtils.emptyIfNull(dto).stream().map(AppUserMapper::toEntity).collect(Collectors.toList());
    }

}
