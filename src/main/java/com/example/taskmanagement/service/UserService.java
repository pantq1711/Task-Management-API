package com.example.taskmanagement.service;

import com.example.taskmanagement.dto.AuthResponseDTO;
import com.example.taskmanagement.dto.LoginRequest;
import com.example.taskmanagement.dto.RegisterRequest;
import com.example.taskmanagement.dto.UserDTO;
import com.example.taskmanagement.entity.User;
import com.example.taskmanagement.exception.ResourceNotFoundException;
import com.example.taskmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserDTO register(RegisterRequest registerRequest){
        //check trong DB xem co username chua
        if(!userRepository.existsByUsername(registerRequest.getUsername())){
            throw new RuntimeException("Username existed !");
        }
        //hash password
        passwordEncoder.encode(registerRequest.getPassword());
        // tao user voi username, password
        User user = new User();
        user.setName(registerRequest.getName());
        user.setPassword(registerRequest.getPassword());
        user.setUsername(registerRequest.getUsername());
        //luu user vao DB
        userRepository.save(user);
        return convertToDTO(user);

    }

    public AuthResponseDTO login(LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        //Check password
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Not found user!"));
        //Tao token
        String accessToken = jwtService.generateToken(user.getUsername());
        //Tra ve AuthResponse
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setAccessToken(accessToken);
        return authResponseDTO;
    }

    private UserDTO convertToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setRole(user.getRole());
        return userDTO;
    }
}
