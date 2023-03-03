package ru.pfr.contracts2.entity.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final RayonMapper rayonMapper;

    public UserDto toDto(User obj) {
        return UserDto.builder()
                .id(obj.getId())
                .rayon(rayonMapper.toDto(obj.getRayon()))
                .login(obj.getLogin())
                .email(obj.getEmail())
                .build();
    }

    public User fromDto(UserDto dto) {
        return User.builder()
                .id(dto.getId())
                .rayon(rayonMapper.fromDto(dto.getRayon()))
                .login(dto.getLogin())
                .email(dto.getEmail())
                .build();
    }
}
