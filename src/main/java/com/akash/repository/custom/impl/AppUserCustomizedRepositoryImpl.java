package com.akash.repository.custom.impl;

import com.akash.entity.AppUser;
import com.akash.entity.AppUserSearch;
import com.akash.repository.custom.AppUserCustomizedRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

public class AppUserCustomizedRepositoryImpl
implements AppUserCustomizedRepository {
    @PersistenceContext
    EntityManager em;

    @Override
    public List<AppUser> searchAppUserPaginated(AppUserSearch appUserSearch, int from) {
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(AppUser.class);
        Root root = cq.from(AppUser.class);
        List<Predicate> predicates = this.getPredicates((Root<AppUser>)root, cb, appUserSearch);
        cq.select((Selection)root).where(predicates.toArray(new Predicate[0]));
        cq.distinct(true);
        TypedQuery query = this.em.createQuery(cq);
        return query.setFirstResult(from).setMaxResults(20).getResultList();
    }

    public List<Predicate> getPredicates(Root<AppUser> root, CriteriaBuilder cb, AppUserSearch appUserSearch) {
        ArrayList<Predicate> predicates = new ArrayList<Predicate>();
        if (this.isNotNullOrNotEmpty(appUserSearch.getAccountNumber())) {
            predicates.add(cb.equal((Expression)root.get("accountNumber"), (Object)appUserSearch.getAccountNumber()));
        }
        if (this.isNotNullOrNotEmpty(appUserSearch.getLedgerNumber())) {
            predicates.add(cb.equal((Expression)root.get("ledgerNumber"), (Object)appUserSearch.getLedgerNumber()));
        }
        if (this.isNotNullOrNotEmpty(appUserSearch.getContact())) {
            predicates.add(cb.or(new Predicate[]{cb.equal((Expression)root.get("contact"), (Object)appUserSearch.getContact()), cb.equal((Expression)root.get("contactTwo"), (Object)appUserSearch.getContact()), cb.equal((Expression)root.get("contactThree"), (Object)appUserSearch.getContact())}));
        }
        if (this.isNotNullOrNotEmpty(appUserSearch.getName())) {
            predicates.add(cb.like((Expression)root.get("name"), "%" + appUserSearch.getName() + "%"));
        }
        if (this.isNotNull(appUserSearch.getUserTypeId())) {
            predicates.add(cb.equal((Expression)root.get("userType").get("id"), (Object)appUserSearch.getUserTypeId()));
        }
        if (this.isNotNull(appUserSearch.getLabourGroupId())) {
            predicates.add(cb.equal((Expression)root.get("labourGroup").get("id"), (Object)appUserSearch.getLabourGroupId()));
        }
        return predicates;
    }

    @Override
    public long searchAppUsersCount(AppUserSearch appUserSearch) {
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Long.class);
        Root root = cq.from(AppUser.class);
        List<Predicate> predicates = this.getPredicates((Root<AppUser>)root, cb, appUserSearch);
        cq.distinct(true);
        cq.multiselect(new Selection[]{cb.count((Expression)root)}).where(predicates.toArray(new Predicate[0]));
        TypedQuery query = this.em.createQuery(cq);
        try {
            return (Long)query.getSingleResult();
        }
        catch (Exception e) {
            return 0L;
        }
    }

    public boolean isNotNull(Long lng) {
        return lng != null;
    }

    public boolean isNotNullOrNotEmpty(String str) {
        return str != null && !str.isEmpty();
    }
}
