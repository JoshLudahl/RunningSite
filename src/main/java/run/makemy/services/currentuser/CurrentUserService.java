package run.makemy.services.currentuser;

import run.makemy.domains.user.CurrentUser;

public interface CurrentUserService {

    boolean canAccessUser(CurrentUser currentUser, Long userId);

}
