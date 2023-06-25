package com.team.tesbro.User;


import com.team.tesbro.DataNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password, UserRole role) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        this.userRepository.save(user);
        return user;
    }
    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = this.userRepository.findByusername(username);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }

    // 학원 관리자 업데이트
    private void updateAcademyUserRole(String username) {
        Optional<SiteUser> siteUserOpt = userRepository.findByusername(username);
        if (siteUserOpt.isPresent()) {
            SiteUser siteUser = siteUserOpt.get();
            siteUser.setRole(UserRole.ACADEMY);
            userRepository.save(siteUser);
        }
    }
}
