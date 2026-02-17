package com.akash.repository.custom.impl;

import com.akash.entity.Manufacture;
import com.akash.entity.ManufactureSearch;
import com.akash.entity.dto.ManufactureDTO;
import com.akash.repository.custom.ManufactureCustomizedRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

public class ManufactureCustomizedRepositoryImpl
implements ManufactureCustomizedRepository {
    @PersistenceContext
    EntityManager em;

    @Override
    public List<ManufactureDTO> searchPaginated(ManufactureSearch manufactureSearch, int from) {
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(ManufactureDTO.class);
        Root root = cq.from(Manufacture.class);
        List<Predicate> predicates = this.getPredicates(cb, (Root<Manufacture>)root, manufactureSearch);
        cq.select((Selection)cb.construct(ManufactureDTO.class, new Selection[]{root.get("id"), root.get("product").get("name"), root.get("size").get("name"), root.get("date"), root.get("totalQuantity"), root.get("totalAmount")})).where(predicates.toArray(new Predicate[0]));
        cq.orderBy(new Order[]{cb.desc((Expression)root.get("date"))});
        cq.distinct(true);
        return this.em.createQuery(cq).setFirstResult(from).setMaxResults(20).getResultList();
    }

    public List<Predicate> getPredicates(CriteriaBuilder cb, Root<Manufacture> root, ManufactureSearch manufactureSearch) {
        ArrayList<Predicate> predicates = new ArrayList<Predicate>();
        if (manufactureSearch.getProductId() != null) {
            predicates.add(cb.equal((Expression)root.get("product").get("id"), (Object)manufactureSearch.getProductId()));
        }
        if (manufactureSearch.getSizeId() != null) {
            predicates.add(cb.equal((Expression)root.get("size").get("id"), (Object)manufactureSearch.getSizeId()));
        }
        if (manufactureSearch.getLabourId() != null) {
            Join labourInfoJoin = root.join("labourInfo");
            Join labourJoin = labourInfoJoin.join("labours");
            predicates.add(cb.equal((Expression)labourJoin.get("id"), (Object)manufactureSearch.getLabourId()));
        }
        predicates.add(cb.between((Expression)root.get("date"), (Comparable)manufactureSearch.getStartDate(), (Comparable)manufactureSearch.getEndDate()));
        return predicates;
    }

    @Override
    public long count(ManufactureSearch manufactureSearch) {
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Long.class);
        Root root = cq.from(Manufacture.class);
        List<Predicate> predicates = this.getPredicates(cb, (Root<Manufacture>)root, manufactureSearch);
        cq.select((Selection)cb.count((Expression)root)).where(predicates.toArray(new Predicate[0]));
        cq.orderBy(new Order[]{cb.desc((Expression)root.get("date"))});
        cq.distinct(true);
        try {
            return (Long)this.em.createQuery(cq).getSingleResult();
        }
        catch (Exception e) {
            return 0L;
        }
    }

    public boolean isNotNullOrNotEmpty(String string) {
        return string != null && !string.isEmpty();
    }
}
