package com.airplane.schedule.service;

import com.airplane.schedule.dto.PageApiResponse;
import com.airplane.schedule.dto.request.PasswordRequestDTO;
import com.airplane.schedule.dto.request.UserInforRequestDTO;
import com.airplane.schedule.dto.request.UserRequestDTO;
import com.airplane.schedule.dto.request.UserSearchRequest;
import com.airplane.schedule.dto.response.UserResponseDTO;
import com.airplane.schedule.exception.ResourceNotFoundException;
import com.airplane.schedule.exception.UserAlreadyExistsException;
import com.airplane.schedule.mapper.UserMapper;
import com.airplane.schedule.model.Role;
import com.airplane.schedule.model.User;
import com.airplane.schedule.model.UserActivityLog;
import com.airplane.schedule.repository.RoleRepository;
import com.airplane.schedule.repository.UserActivityLogRepository;
import com.airplane.schedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final UserActivityLogRepository userActivityLogRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public UserResponseDTO getUserById(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        return userMapper.userToUserResponseDTO(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::userToUserResponseDTO).collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByEmail((userRequestDTO.getEmail()))) {
            throw new UserAlreadyExistsException(userRequestDTO.getEmail() + " already exists");
        }
        User user = userMapper.UserRequestDTOToUser(userRequestDTO);
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        Role role = roleRepository.findById(userRequestDTO.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + userRequestDTO.getRoleId()));
        role.assignRoleToUser(user);
        emailService.sendMailAlert(user.getEmail(), "signin");
        return userMapper.userToUserResponseDTO(userRepository.save(user));
    }

    @Override
    public UserResponseDTO updateInfoUser(int id, UserInforRequestDTO userInforRequestDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        if (userInforRequestDTO.getFirstName() != null) {
            user.setFirstName(userInforRequestDTO.getFirstName());
        }
        if (userInforRequestDTO.getLastName() != null) {
            user.setLastName(userInforRequestDTO.getLastName());
        }
        if (userInforRequestDTO.getBirthDate() != null) {
            user.setBirthDate(LocalDate.parse(userInforRequestDTO.getBirthDate()));
        }
        if (userInforRequestDTO.getPhone() != null) {
            user.setPhone(userInforRequestDTO.getPhone());
        }
        if (userInforRequestDTO.getAddress() != null) {
            user.setAddress(userInforRequestDTO.getAddress());
        }
        emailService.sendMailAlert(user.getEmail(), "change_info");
        return userMapper.userToUserResponseDTO(userRepository.save(user));
    }

    @Override
    public void updatePassword(String email, PasswordRequestDTO passwordRequestDTO) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));
        if(passwordEncoder.matches(passwordRequestDTO.getOldPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(passwordRequestDTO.getNewPassword()));
            userRepository.save(user);
            emailService.sendMailAlert(user.getEmail(), "change_password");

            UserActivityLog log = new UserActivityLog();
            log.setUserId(user.getId());
            log.setActivity("Change PassWord");
            userActivityLogRepository.save(log);
        } else {
            throw new ResourceNotFoundException("Old password is incorrect");
        }
    }

    @Override
    public String updateAvatar(int id, MultipartFile file) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        Map data = cloudinaryService.upLoadFile(file);
        String avatar = (String) data.get("url");
        user.setAvatar(avatar);
        userRepository.save(user);
        emailService.sendMailAlert(user.getEmail(), "change_info");
        return avatar;
    }

    @Override
    public void deleteUser(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        Role role = user.getRole();
        role.removeUserFromRole(user);
        userRepository.delete(user);
        UserActivityLog log = new UserActivityLog();
        log.setUserId(user.getId());
        log.setActivity("Delete User");
        userActivityLogRepository.save(log);
    }

    @Override
    public PageApiResponse<List<UserResponseDTO>> search(UserSearchRequest userSearchRequest) {
        Long totalUsers = userRepository.count(userSearchRequest);
        List<User> users = userRepository.search(userSearchRequest);
        List <UserResponseDTO> userResponseDTOS = users.stream().map(userMapper::userToUserResponseDTO).collect(Collectors.toList());
        PageApiResponse.PageableResponse pageableResponse = PageApiResponse.PageableResponse.builder()
                .pageSize(userSearchRequest.getPageSize())
                .pageIndex(userSearchRequest.getPageIndex())
                .totalElements(totalUsers)
                .totalPages((int)(Math.ceil((double)totalUsers / userSearchRequest.getPageSize())))
                .hasNext((userSearchRequest.getPageIndex() + 1) * userSearchRequest.getPageSize() < totalUsers)
                .hasPrevious(userSearchRequest.getPageIndex() >0).build();
        return PageApiResponse.<List<UserResponseDTO>>builder()
                .data(userResponseDTOS)
                .success(true)
                .code(200)
                .pageable(pageableResponse)
                .message("Search user successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
}
