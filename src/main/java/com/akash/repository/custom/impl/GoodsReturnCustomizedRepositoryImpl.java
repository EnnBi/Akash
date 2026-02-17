package com.akash.repository.custom.impl;

import com.akash.entity.BillBook;
import com.akash.entity.GoodsReturn;
import com.akash.entity.GoodsReturnSearch;
import com.akash.repository.custom.GoodsReturnCustomizedRepository;
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

public class GoodsReturnCustomizedRepositoryImpl
implements GoodsReturnCustomizedRepository {
    @PersistenceContext
    EntityManager em;

    @Override
    public GoodsReturn billBookToGoodsReturnMapping(BillBook billbook) {
        GoodsReturn goodsReturn = new GoodsReturn();
        goodsReturn.setCarraige(null);
        goodsReturn.setCustomer(billbook.getCustomer());
        goodsReturn.setDate(null);
        goodsReturn.setDriver(null);
        goodsReturn.setDriverLoadingCharges(null);
        goodsReturn.setDriverUnloadingCharges(null);
        goodsReturn.setLabourGroup(null);
        goodsReturn.setLoadingAmount(null);
        goodsReturn.setLoadingAmountPerHead(null);
        goodsReturn.setOtherVehicle(null);
        goodsReturn.setReceiptNumber(billbook.getReceiptNumber());
        goodsReturn.setSales(billbook.getSales());
        goodsReturn.setSite(null);
        goodsReturn.setSites(null);
        goodsReturn.setTotal(billbook.getTotal() - billbook.getLoadingAmount() - billbook.getUnloadingAmount() - billbook.getCarraige());
        goodsReturn.setUnit(billbook.getUnit());
        goodsReturn.setUnloaderLabourGroup(null);
        goodsReturn.setUnloadingAmount(null);
        goodsReturn.setUnloadingAmountPerHead(null);
        goodsReturn.setVehicle(null);
        return goodsReturn;
    }

    @Override
    public List<GoodsReturn> searchPaginated(GoodsReturnSearch goodsReturnSearch, int from) {
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(GoodsReturn.class);
        Root root = cq.from(GoodsReturn.class);
        List<Predicate> predicates = this.getPredicates(cb, (Root<GoodsReturn>)root, goodsReturnSearch);
        cq.select((Selection)root).where(predicates.toArray(new Predicate[0]));
        cq.orderBy(new Order[]{cb.desc((Expression)root.get("date"))});
        cq.distinct(true);
        return this.em.createQuery(cq).setFirstResult(from).setMaxResults(20).getResultList();
    }

    @Override
    public long count(GoodsReturnSearch goodsReturnSearch) {
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Long.class);
        Root root = cq.from(GoodsReturn.class);
        List<Predicate> predicates = this.getPredicates(cb, (Root<GoodsReturn>)root, goodsReturnSearch);
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

    public List<Predicate> getPredicates(CriteriaBuilder cb, Root<GoodsReturn> root, GoodsReturnSearch goodsReturnSearch) {
        ArrayList<Predicate> predicates = new ArrayList<Predicate>();
        if (this.isNotNullOrNotEmpty(goodsReturnSearch.getReceiptNumber())) {
            predicates.add(cb.equal((Expression)root.get("receiptNumber"), (Object)goodsReturnSearch.getReceiptNumber()));
        }
        if (goodsReturnSearch.getCustomerId() != null) {
            predicates.add(cb.equal((Expression)root.get("customer").get("id"), (Object)goodsReturnSearch.getCustomerId()));
        }
        predicates.add(cb.between((Expression)root.get("date"), (Comparable)goodsReturnSearch.getStartDate(), (Comparable)goodsReturnSearch.getEndDate()));
        return predicates;
    }

    public boolean isNotNullOrNotEmpty(String string) {
        return string != null && !string.isEmpty();
    }
}
