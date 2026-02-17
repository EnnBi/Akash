package com.akash.repository.custom.impl;

import com.akash.entity.RawMaterial;
import com.akash.entity.RawMaterialSearch;
import com.akash.repository.custom.RawMaterialCustomizedRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

public class RawMaterialCustomizedRepositoryImpl
implements RawMaterialCustomizedRepository {
    @PersistenceContext
    EntityManager em;

    @Override
    public List<RawMaterial> searchRawMaterialPaginated(RawMaterialSearch rawMaterialSearch, int from) {
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(RawMaterial.class);
        Root root = cq.from(RawMaterial.class);
        List<Predicate> predicates = this.getPredicates((Root<RawMaterial>)root, cb, rawMaterialSearch);
        cq.select((Selection)root).where(predicates.toArray(new Predicate[0]));
        cq.distinct(true);
        cq.orderBy(new Order[]{cb.desc((Expression)root.get("date")), cb.desc((Expression)root.get("id"))});
        TypedQuery query = this.em.createQuery(cq);
        return query.setFirstResult(from).setMaxResults(10).getResultList();
    }

    public List<Predicate> getPredicates(Root<RawMaterial> root, CriteriaBuilder cb, RawMaterialSearch rawMaterialSearch) {
        ArrayList<Predicate> predicates = new ArrayList<Predicate>();
        if (this.isNotNullOrNotEmpty(rawMaterialSearch.getAppUserId())) {
            predicates.add(cb.equal((Expression)root.get("dealer").get("id"), (Object)rawMaterialSearch.getAppUserId()));
        }
        if (this.isNotNullOrNotEmpty(rawMaterialSearch.getMaterialTypeId())) {
            predicates.add(cb.equal((Expression)root.get("material").get("id"), (Object)rawMaterialSearch.getMaterialTypeId()));
        }
        if (rawMaterialSearch.getStartDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo((Expression)root.get("date"), (Comparable)rawMaterialSearch.getStartDate()));
        }
        if (rawMaterialSearch.getStartDate() != null) {
            predicates.add(cb.lessThanOrEqualTo((Expression)root.get("date"), (Comparable)rawMaterialSearch.getEndDate()));
        }
        return predicates;
    }

    @Override
    public long searchRawMaterialsCount(RawMaterialSearch rawMaterialSearch) {
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Long.class);
        Root root = cq.from(RawMaterial.class);
        List<Predicate> predicates = this.getPredicates((Root<RawMaterial>)root, cb, rawMaterialSearch);
        cq.distinct(true);
        cq.multiselect(new Selection[]{cb.count((Expression)root)}).where(predicates.toArray(new Predicate[0]));
        cq.orderBy(new Order[]{cb.desc((Expression)root.get("date"))});
        TypedQuery query = this.em.createQuery(cq);
        try {
            return (Long)query.getSingleResult();
        }
        catch (Exception e) {
            return 0L;
        }
    }

    public boolean isNotNullOrNotEmpty(Long lng) {
        return lng != null;
    }
}
