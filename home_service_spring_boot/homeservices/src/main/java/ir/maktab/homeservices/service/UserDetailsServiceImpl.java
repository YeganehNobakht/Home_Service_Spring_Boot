package ir.maktab.homeservices.service;

import ir.maktab.homeservices.data.repository.User.UserRepository;
import ir.maktab.homeservices.dto.enums.UserRole;
import ir.maktab.homeservices.service.maktabMassageSource.MaktabMessageSource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final MaktabMessageSource maktabMessageSource;

    public UserDetailsServiceImpl(UserRepository userRepository, MaktabMessageSource maktabMessageSource) {
        this.userRepository = userRepository;
        this.maktabMessageSource = maktabMessageSource;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ir.maktab.homeservices.data.entity.User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            ir.maktab.homeservices.data.entity.User user = optionalUser.get();
            if (user.getUserRole().equals(UserRole.Customer)) {
                if (user.isEnabled()) {
                    return User.withUsername(user.getUsername())
                            .password(user.getPassword())
                            .roles(UserRole.Customer.name())
                            .build();
                }
            }
            if (user.getUserRole().equals(UserRole.Specialist)) {

                if (user.isEnabled()) {
                    return User.withUsername(user.getUsername())
                            .password(user.getPassword())
                            .roles(UserRole.Specialist.name())
                            .build();
                }
//
            }
            if (user.getUserRole().equals(UserRole.Manager)) {
                return User.withUsername(user.getUsername())
                        .password(user.getPassword())
                        .roles(UserRole.Manager.name())
                        .build();
            }

        }
        throw new UsernameNotFoundException(maktabMessageSource.getEnglish("user.not.found1"));

    }
}
