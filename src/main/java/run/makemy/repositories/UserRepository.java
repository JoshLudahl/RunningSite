package run.makemy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import run.makemy.domains.user.User;

import java.util.Optional;

/**
 * Created by Ohlaph on 5/7/2016.
 */
public interface UserRepository extends JpaRepository <User, Long> {

    Optional<User> findOneByEmail(String email);

}
