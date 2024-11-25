package org.ilisi.auth_service.services;
import org.ilisi.auth_service.dto.UserDto;
import org.ilisi.auth_service.entities.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    User addUser(UserDto userDto);

    Optional<User> getUser(Long userId);
    Optional<String> getUserNameById(Long userId);
    List<String> getUserInterests(Long userId);
    User updateUser(Long userId, User updatedUser);

    @Nullable
    User addUser(@NotNull User user);
}


