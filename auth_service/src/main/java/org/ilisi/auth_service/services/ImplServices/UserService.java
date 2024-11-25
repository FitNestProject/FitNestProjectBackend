package org.ilisi.auth_service.services.ImplServices;

import org.ilisi.auth_service.Repos.AccountRepo;
import org.ilisi.auth_service.Repos.UserRepo;
import org.ilisi.auth_service.dto.UserDto;
import org.ilisi.auth_service.entities.Account;
import org.ilisi.auth_service.entities.User;
import org.ilisi.auth_service.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private UserRepo userRepo;
    private AccountRepo accountRepo;
    @Override
    public User addUser(UserDto userDto) {
        User user = new User();

        // Recherche du compte et gestion de l'absence de compte
        Account account = accountRepo.findByUsername(userDto.getUserName())
                .orElseThrow(() -> new UsernameNotFoundException("No account found for username: " + userDto.getUserName()));

        // Remplir les champs de l'entité User avec les données du DTO
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setDateBirth(userDto.getDateBirth());
        user.setGender(userDto.getGender());
        user.setDescription(userDto.getDescription());
        user.setIdFace(userDto.getIdFace());
        user.setIdBack(userDto.getIdBack());
        user.setProfilePicture(userDto.getProfilePicture());
        user.setInterests(userDto.getInterests());
        user.setAccount(account);

        return userRepo.save(user);
    }


    // Get user by ID
    @Override
    public Optional<User> getUser(Long userId) {
        return userRepo.findById(userId);
    }

    // Get user interests
    @Override
    public List<String> getUserInterests(Long userId) {
        return userRepo.findById(userId)
                .map(User::getInterests)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }


    @Override
    public Optional<String> getUserNameById(Long userId) {
        return userRepo.findById(userId).map(user -> user.getAccount().getUsername());
    }

    // Implémentation du service pour la mise à jour des informations de l'utilisateur
    @Override
    public User updateUser(Long userId, User updatedUser) {
        Optional<User> existingUser = userRepo.findById(userId);

        if (!existingUser.isPresent()) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        User user = existingUser.get();

        // Mise à jour des informations de l'utilisateur
        if (updatedUser.getFirstName() != null) {
            user.setFirstName(updatedUser.getFirstName());
        }
        if (updatedUser.getLastName() != null) {
            user.setLastName(updatedUser.getLastName());
        }

        if (updatedUser.getPhoneNumber() != null) {
            user.setPhoneNumber(updatedUser.getPhoneNumber());
        }
        if (updatedUser.getProfilePicture() != null) {
            user.setProfilePicture(updatedUser.getProfilePicture());
        }
        if (updatedUser.getInterests() != null) {
            user.setInterests(updatedUser.getInterests());
        }
        if (updatedUser.getDescription() != null) {
            user.setDescription(updatedUser.getDescription());
        }
        // Vous pouvez ajouter d'autres champs à mettre à jour selon vos besoins

        // Sauvegarde les modifications dans la base de données
        return userRepo.save(user);
    }

    @Nullable
    @Override
    public User addUser(@NotNull User user) {
        return null;
    }

}
