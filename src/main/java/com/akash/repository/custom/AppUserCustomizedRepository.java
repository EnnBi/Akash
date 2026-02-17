package com.akash.repository.custom;

import com.akash.entity.AppUser;
import com.akash.entity.AppUserSearch;
import java.util.List;

public interface AppUserCustomizedRepository {
    public List<AppUser> searchAppUserPaginated(AppUserSearch search, int pageNumber);

    public long searchAppUsersCount(AppUserSearch search);
}
