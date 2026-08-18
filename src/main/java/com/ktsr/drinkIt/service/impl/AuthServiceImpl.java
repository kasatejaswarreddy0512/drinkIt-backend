package com.ktsr.drinkIt.service.impl;

import com.ktsr.drinkIt.DTO.LoginRequestDto;
import com.ktsr.drinkIt.DTO.RegisterRequestDto;
import com.ktsr.drinkIt.DTO.UserDto;
import com.ktsr.drinkIt.config.JwtProvider;
import com.ktsr.drinkIt.entity.Cart;
import com.ktsr.drinkIt.entity.User;
import com.ktsr.drinkIt.helper.AuthResponse;
import com.ktsr.drinkIt.mapper.UserMapper;
import com.ktsr.drinkIt.repository.CartRepository;
import com.ktsr.drinkIt.repository.UserRepository;
import com.ktsr.drinkIt.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserServiceImpl customUserService;
    private final CartRepository cartRepository;

    @Override
    public AuthResponse signup(RegisterRequestDto userDto) throws Exception {
        User user= userRepository.findByEmail(userDto.getEmail());
        if(user!=null){
            throw new Exception("User already exists");
        }

        User savedUser=new User();
        savedUser.setEmail(userDto.getEmail());
        savedUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        savedUser.setFullName( userDto.getFullName());
        savedUser.setPhone(userDto.getPhone());
        savedUser.setRole(userDto.getRole());
        savedUser.setDateOfBirth(userDto.getDateOfBirth());
        savedUser.setGender(userDto.getGender());
        savedUser.setVerified(userDto.getVerified());
        savedUser.setActive(true);
        userRepository.save(savedUser);

        Cart cart = Cart.builder()
                .user(savedUser)
                .totalAmount(0.0)
                .build();

        cartRepository.save(cart);

        Authentication authentication= new UsernamePasswordAuthenticationToken(
                userDto.getEmail(), userDto.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt=jwtProvider.generateToken(authentication);

        AuthResponse authResponse= new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Registered Successfully....!");
        authResponse.setUser(UserMapper.toDto(savedUser));

        return authResponse;
    }

    @Override
    public AuthResponse login(LoginRequestDto loginRequestDto) throws Exception {
        String email=loginRequestDto.getEmail();
        String password= loginRequestDto.getPassword();
        Authentication authentication= authenticate(email,password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Collection<? extends GrantedAuthority> authorities= authentication.getAuthorities();

        String role=authorities.iterator().next().getAuthority();
        String jwt=jwtProvider.generateToken(authentication);

        User user= userRepository.findByEmail(email);
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);

        AuthResponse authResponse= new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login Successfully....!");
        authResponse.setUser(UserMapper.toDto(user));

        return authResponse;
    }


    private Authentication authenticate(String email, String password) throws Exception {

        UserDetails userDetails= customUserService.loadUserByUsername(email);
        if(userDetails==null){
            throw  new Exception("Email id doesn't exists..!" + email);
        }

        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new Exception("Password doesn't match...!");
        }

        return  new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
