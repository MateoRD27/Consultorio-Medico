package edu.unimag.consultoriomedico.security.services;
import edu.unimag.consultoriomedico.entity.User;
import edu.unimag.consultoriomedico.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class JpaUserDetailsService implements UserDetailsService{
    private final UserRepository userRepository;

    @Override
    @Transactional
    //carga el usuario desde la base de datos para comparar el token
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado"));
        return new UserInfoDetails(user);
    }
}
