package com.akash.repository.custom.impl;

import com.akash.entity.DayBook;
import com.akash.entity.DayBookSearch;
import com.akash.repository.custom.DayBookCustomizedRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

public class DayBookCustomizedRepositoryImpl
implements DayBookCustomizedRepository {
    @PersistenceContext
    EntityManager em;

    @Override
    public List<DayBook> searchPaginated(DayBookSearch dayBookSearch, int from) {
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(DayBook.class);
        Root root = cq.from(DayBook.class);
        List<Predicate> predicates = this.getPredicates(cb, (Root<DayBook>)root, dayBookSearch);
        cq.select((Selection)root).where(predicates.toArray(new Predicate[0]));
        cq.orderBy(new Order[]{cb.desc((Expression)root.get("date"))});
        cq.distinct(true);
        return this.em.createQuery(cq).setFirstResult(from).setMaxResults(20).getResultList();
    }

    private List<Predicate> getPredicates(CriteriaBuilder cb, Root<DayBook> root, DayBookSearch dayBookSearch) {
        ArrayList<Predicate> predicates = new ArrayList<Predicate>();
        if (dayBookSearch.getUser() != null) {
            predicates.add(cb.equal((Expression)root.get("user").get("id"), (Object)dayBookSearch.getUser()));
        }
        if (this.isNotNullAndNotEmpty(dayBookSearch.getTransactionBy())) {
            predicates.add(cb.equal((Expression)root.get("transactionBy"), (Object)dayBookSearch.getTransactionBy()));
        }
        if (this.isNotNullAndNotEmpty(dayBookSearch.getTransactionNumber())) {
            predicates.add(cb.equal((Expression)root.get("transactionNumber"), (Object)dayBookSearch.getTransactionNumber()));
        }
        if (this.isNotNullAndNotEmpty(dayBookSearch.getTransactionType())) {
            predicates.add(cb.equal((Expression)root.get("transactionType"), (Object)dayBookSearch.getTransactionType()));
        }
        if (this.isNotNullAndNotEmpty(dayBookSearch.getAccountNumber())) {
            predicates.add(cb.equal((Expression)root.get("accountNumber"), (Object)dayBookSearch.getAccountNumber()));
        }
        if (this.isNotNullAndNotEmpty(dayBookSearch.getStatus())) {
            predicates.add(cb.equal((Expression)root.get("status"), (Object)dayBookSearch.getStatus()));
        }
        predicates.add(cb.between((Expression)root.get("date"), (Comparable)dayBookSearch.getStartDate(), (Comparable)dayBookSearch.getEndDate()));
        return predicates;
    }

    @Override
    public long count(DayBookSearch dayBookSearch) {
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Long.class);
        Root root = cq.from(DayBook.class);
        List<Predicate> predicates = this.getPredicates(cb, (Root<DayBook>)root, dayBookSearch);
        cq.select((Selection)cb.count((Expression)root)).where(predicates.toArray(new Predicate[0]));
        cq.orderBy(new Order[]{cb.desc((Expression)root.get("date"))});
        cq.distinct(true);
        try {
            return (Long)this.em.createQuery(cq).getSingleResult();
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public boolean isNotNullAndNotEmpty(String source) {
        return source != null && !source.isEmpty();
    }
}
