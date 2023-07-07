package training.basicsecuritydemo;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class UserService implements ReactiveUserDetailsService {

    private UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

//    public Mono<User> create(Mono<CreateUserCommand> commandMono) {
//        return commandMono
//                // Élesben MapStruct
//                .map(command -> new User(command.getName(), command.getPassword(), command.getRole()))
//                .flatMap(userRepository::save);
//    }
}
