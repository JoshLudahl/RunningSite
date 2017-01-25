package run.makemy.services.currentuser;

import org.springframework.stereotype.Service;
import run.makemy.domains.user.CurrentUser;
import run.makemy.domains.user.Role;

@Service
public class CurrentUserServiceImpl implements CurrentUserService {

    @Override
    public boolean canAccessUser(CurrentUser currentUser, Long userID) {
        return currentUser != null
                && (currentUser.getRole().equals(Role.ADMIN) || currentUser.getId().equals(userID));
    }

}
