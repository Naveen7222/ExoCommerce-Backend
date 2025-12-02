package com.exocommerce.user_service.service;

import com.exocommerce.user_service.dto.UserDTO;
import com.exocommerce.user_service.model.User;
import com.exocommerce.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = mapToEntity(userDTO);
        User saved = userRepository.save(user);
        return mapToDTO(saved);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToDTO(user);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existing.setName(userDTO.getName());
        existing.setEmail(userDTO.getEmail());
        existing.setPhone(userDTO.getPhone());
        existing.setAddress(userDTO.getAddress());
        existing.setProfileImg(userDTO.getProfileImg());

        User updated = userRepository.save(existing);
        return mapToDTO(updated);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }


    // ------------------------
    // Mapping Methods
    // ------------------------

    private UserDTO mapToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getProfileImg(),
                user.getAuthUserId(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    private User mapToEntity(UserDTO dto) {
        User user = new User();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());
        user.setProfileImg(dto.getProfileImg());
        user.setAuthUserId(dto.getAuthUserId());

        return user;
    }
}
