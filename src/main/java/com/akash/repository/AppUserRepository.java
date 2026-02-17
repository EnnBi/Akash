package com.akash.repository;

import com.akash.entity.AppUser;
import com.akash.entity.Site;
import com.akash.projections.AppUserProjection;
import com.akash.repository.custom.AppUserCustomizedRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AppUserRepository
extends CrudRepository<AppUser, Long>,
AppUserCustomizedRepository {
    public boolean existsByContact(String contact);

    @Query(value="select a from AppUser a where a.contact=:contact and a.id!=:id")
    public AppUser chechUserExistsAlready(@Param(value="contact") String contact, @Param(value="id") long id);

    public List<AppUser> findByUserType_NameAndActive(String name, boolean active);

    @Query(value="Select a from AppUser a where a.userType.name in :userType and a.active = :active")
    public List<AppUser> findAppUsersOnType(@Param(value="userType") String[] userType, @Param(value="active") boolean active);

    public List<AppUserProjection> findByUserType_NameInAndActive(String[] names, boolean active);

    @Query(value="Select u.sites from AppUser u where u.id= :id")
    public List<Site> findSitesOnUserId(@Param(value="id") long id);

    public List<AppUser> findByUserType_NameAndLabourGroup_IdAndActive(String name, long labourGroupId, boolean active);

    public List<AppUserProjection> findByUserType_IdAndActive(long userTypeId, boolean active);

    @Query(value="Select a from AppUser a where a.userType.name in :userType")
    public List<AppUser> findAllAppUsersOnType(@Param(value="userType") String[] userType);
}
