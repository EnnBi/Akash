package com.akash.repository.custom.impl;

import com.akash.entity.BillBook;
import com.akash.entity.BillBookSearch;
import com.akash.entity.dto.BillBookDTO;
import com.akash.repository.custom.BillBookCustomizedRepository;
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

public class BillBookCustomizedRepositoryImpl
implements BillBookCustomizedRepository {
    @PersistenceContext
    EntityManager em;

    @Override
    public List<BillBookDTO> searchPaginated(BillBookSearch billBookSearch, int from) {
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(BillBookDTO.class);
        Root root = cq.from(BillBook.class);
        List<Predicate> predicates = this.getPredicates(cb, (Root<BillBook>)root, billBookSearch);
        cq.select((Selection)cb.construct(BillBookDTO.class, new Selection[]{root.get("id"), root.get("receiptNumber"), root.get("customer").get("name"), root.get("customer").get("address"), root.get("date"), root.get("sites"), root.get("total")})).where(predicates.toArray(new Predicate[0]));
        cq.orderBy(new Order[]{cb.desc((Expression)root.get("receiptNumber"))});
        cq.distinct(true);
        return this.em.createQuery(cq).setFirstResult(from).setMaxResults(20).getResultList();
    }

    public List<Predicate> getPredicates(CriteriaBuilder cb, Root<BillBook> root, BillBookSearch billBookSearch) {
        ArrayList<Predicate> predicates = new ArrayList<Predicate>();
        if (this.isNotNullOrNotEmpty(billBookSearch.getReceiptNumber())) {
            predicates.add(cb.equal((Expression)root.get("receiptNumber"), (Object)billBookSearch.getReceiptNumber()));
        }
        if (billBookSearch.getCustomerId() != null) {
            predicates.add(cb.equal((Expression)root.get("customer").get("id"), (Object)billBookSearch.getCustomerId()));
        }
        if (billBookSearch.getVehicleId() != null) {
            predicates.add(cb.equal((Expression)root.get("vehicle").get("id"), (Object)billBookSearch.getVehicleId()));
        }
        if (billBookSearch.getSiteId() != null) {
            predicates.add(cb.equal((Expression)root.get("site").get("id"), (Object)billBookSearch.getSiteId()));
        }
        predicates.add(cb.between((Expression)root.get("date"), (Comparable)billBookSearch.getStartDate(), (Comparable)billBookSearch.getEndDate()));
        return predicates;
    }

    @Override
    public long count(BillBookSearch billBookSearch) {
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Long.class);
        Root root = cq.from(BillBook.class);
        List<Predicate> predicates = this.getPredicates(cb, (Root<BillBook>)root, billBookSearch);
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

    public boolean isNotNullOrNotEmpty(String string) {
        return string != null && !string.isEmpty();
    }
}
