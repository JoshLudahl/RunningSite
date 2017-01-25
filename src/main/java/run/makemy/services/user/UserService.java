package run.makemy.services.user;

import run.makemy.domains.user.User;
import run.makemy.domains.user.UserCreateForm;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Ohlaph on 5/4/2016.
 */
public interface UserService {

    Optional<User> getUserById(long id);

    Optional<User> getUserByEmail(String email);

    Collection<User> getAllUsers();

    User create(UserCreateForm form);

}
