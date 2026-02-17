package com.akash.util;

import com.akash.entity.AppUser;
import com.akash.repository.AppUserRepository;
import com.akash.repository.BillBookRepository;
import com.akash.repository.ClearDuesRepository;
import com.akash.repository.DayBookRepository;
import com.akash.repository.GoodsReturnRepository;
import com.akash.repository.ManufactureRepository;
import com.akash.repository.RawMaterialRepository;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class CommonMethods {
    static DecimalFormat df = new DecimalFormat("#.##");

    public static Double getCustomerBalance(Long id, LocalDate startDate, LocalDate endDate, BillBookRepository billBookRepo, DayBookRepository dayBookRepo, ClearDuesRepository clearDuesRepositoy, GoodsReturnRepository goodsReturnRepository) {
        Double prevBillBookCredit = billBookRepo.sumOfCustomerDebits(id, startDate, endDate);
        Double prevBillBookDiscount = billBookRepo.sumOfCustomerDiscounts(id, startDate, endDate);
        Double prevDayBookCredit = dayBookRepo.findUserCredits(id, startDate, endDate);
        Double prevDayBookDebit = dayBookRepo.findUserDebits(id, startDate, endDate);
        Double prevClearDues = clearDuesRepositoy.findUserClearDues(id, startDate, endDate);
        Double prevGoodsReturn = goodsReturnRepository.findUserGoodsReturn(id, startDate, endDate);
        prevBillBookCredit = prevBillBookCredit == null ? Double.valueOf(0.0) : prevBillBookCredit;
        prevDayBookCredit = prevDayBookCredit == null ? Double.valueOf(0.0) : prevDayBookCredit;
        prevDayBookDebit = prevDayBookDebit == null ? Double.valueOf(0.0) : prevDayBookDebit;
        prevBillBookDiscount = prevBillBookDiscount == null ? Double.valueOf(0.0) : prevBillBookDiscount;
        prevClearDues = prevClearDues == null ? Double.valueOf(0.0) : prevClearDues;
        prevGoodsReturn = prevGoodsReturn == null ? Double.valueOf(0.0) : prevGoodsReturn;
        Double prevBalance = prevBillBookCredit + prevDayBookCredit - (prevDayBookDebit + prevBillBookDiscount + prevClearDues + prevGoodsReturn);
        prevBalance = Double.valueOf(df.format(prevBalance));
        return prevBalance;
    }

    public static Double getCustomerCredit(Long id, LocalDate startDate, LocalDate endDate, BillBookRepository billBookRepo, DayBookRepository dayBookRepo, GoodsReturnRepository goodsReturnRepository, ClearDuesRepository clearDuesRepositoy) {
        Double prevBillBookCredit = billBookRepo.sumOfCustomerDebits(id, startDate, endDate);
        Double prevBillBookDiscount = billBookRepo.sumOfCustomerDiscounts(id, startDate, endDate);
        Double prevDayBookCredit = dayBookRepo.findUserCredits(id, startDate, endDate);
        Double prevGoodsReturn = goodsReturnRepository.findUserGoodsReturn(id, startDate, endDate);
        Double prevClearDues = clearDuesRepositoy.findUserClearDues(id, startDate, endDate);
        prevBillBookCredit = Objects.isNull(prevBillBookCredit) ? Double.valueOf(0.0) : prevBillBookCredit;
        prevDayBookCredit = Objects.isNull(prevDayBookCredit) ? Double.valueOf(0.0) : prevDayBookCredit;
        prevBillBookDiscount = Objects.isNull(prevBillBookDiscount) ? Double.valueOf(0.0) : prevBillBookDiscount;
        prevGoodsReturn = Objects.isNull(prevGoodsReturn) ? Double.valueOf(0.0) : prevGoodsReturn;
        prevClearDues = Objects.isNull(prevClearDues) ? Double.valueOf(0.0) : prevClearDues;
        Double credit = prevBillBookCredit + prevDayBookCredit - (prevBillBookDiscount + prevGoodsReturn + prevClearDues);
        credit = Double.valueOf(df.format(credit));
        return credit;
    }

    public static Double getCustomerDebit(Long id, LocalDate startDate, LocalDate endDate, DayBookRepository dayBookRepo) {
        Double prevDayBookDebit = dayBookRepo.findUserDebits(id, startDate, endDate);
        Double debit = prevDayBookDebit = Objects.isNull(prevDayBookDebit) ? Double.valueOf(0.0) : prevDayBookDebit;
        debit = Double.valueOf(df.format(debit));
        return debit;
    }

    public static Double getDriverCredit(Long id, LocalDate startDate, LocalDate endDate, DayBookRepository dayBookRepo) {
        Double previousCredit = dayBookRepo.findUserCredits(id, startDate, endDate);
        Double credit = previousCredit = Objects.isNull(previousCredit) ? Double.valueOf(0.0) : previousCredit;
        credit = Double.valueOf(df.format(credit));
        return credit;
    }

    public static Double getDriverDebit(Long id, LocalDate startDate, LocalDate endDate, DayBookRepository dayBookRepo, BillBookRepository billBookRepo, AppUserRepository appUserRepo) {
        AppUser driver = appUserRepo.findById(id).orElse(null);
        Double previousCarraige = billBookRepo.sumOfCarraige(id, startDate, endDate);
        Double previousLoading = billBookRepo.sumOfDriverLoading(driver, startDate, endDate);
        Double previousUnloading = billBookRepo.sumOfDriverUnloading(driver, startDate, endDate);
        Double previousDebit = dayBookRepo.findUserDebits(id, startDate, endDate);
        previousCarraige = Objects.isNull(previousCarraige) ? Double.valueOf(0.0) : previousCarraige;
        previousLoading = Objects.isNull(previousLoading) ? Double.valueOf(0.0) : previousLoading;
        previousUnloading = Objects.isNull(previousUnloading) ? Double.valueOf(0.0) : previousUnloading;
        previousDebit = Objects.isNull(previousDebit) ? Double.valueOf(0.0) : previousDebit;
        Double debit = previousCarraige + previousLoading + previousUnloading + previousDebit;
        debit = Double.valueOf(df.format(debit));
        return debit;
    }

    public static Double getOwnerCredit(Long id, LocalDate startDate, LocalDate endDate, DayBookRepository dayBookRepo, AppUserRepository appUserRepo) {
        AppUser owner = appUserRepo.findById(id).orElse(null);
        Double previousCredit = dayBookRepo.findOwnerCredit(owner.getAccountNumber(), startDate, endDate);
        Double credit = previousCredit = Objects.isNull(previousCredit) ? Double.valueOf(0.0) : previousCredit;
        credit = Double.valueOf(df.format(credit));
        return credit;
    }

    public static Double getOwnerDebit(Long id, LocalDate startDate, LocalDate endDate, DayBookRepository dayBookRepo, AppUserRepository appUserRepo) {
        AppUser owner = appUserRepo.findById(id).orElse(null);
        Double previousDebit = dayBookRepo.findOwnerDebit(owner.getAccountNumber(), startDate, endDate);
        Double debit = previousDebit = Objects.isNull(previousDebit) ? Double.valueOf(0.0) : previousDebit;
        debit = Double.valueOf(df.format(debit));
        return debit;
    }

    public static Double getLabourCredit(Long id, LocalDate startDate, LocalDate endDate, DayBookRepository dayBookRepo) {
        Double prevDayBookCredit = dayBookRepo.findUserCredits(id, startDate, endDate);
        Double credit = prevDayBookCredit = Objects.isNull(prevDayBookCredit) ? Double.valueOf(0.0) : prevDayBookCredit;
        credit = Double.valueOf(df.format(credit));
        return credit;
    }

    public static Double getLabourDebit(Long id, LocalDate startDate, LocalDate endDate, DayBookRepository dayBookRepo, AppUserRepository appUserRepo, BillBookRepository billBookRepo, ManufactureRepository manufactureRepo) {
        AppUser labour = appUserRepo.findById(id).orElse(null);
        Double prevLoadingDebit = billBookRepo.sumOfLabourLoading(labour, startDate, endDate);
        Double prevUnloadingDebit = billBookRepo.sumOfLabourUnloading(labour, startDate, endDate);
        Double prevManufactureDebit = manufactureRepo.sumManufactureDebits(labour, startDate, endDate);
        Double prevDayBookDebit = dayBookRepo.findUserDebits(id, startDate, endDate);
        prevLoadingDebit = Objects.isNull(prevLoadingDebit) ? Double.valueOf(0.0) : prevLoadingDebit;
        prevUnloadingDebit = Objects.isNull(prevUnloadingDebit) ? Double.valueOf(0.0) : prevUnloadingDebit;
        prevManufactureDebit = Objects.isNull(prevManufactureDebit) ? Double.valueOf(0.0) : prevManufactureDebit;
        prevDayBookDebit = Objects.isNull(prevDayBookDebit) ? Double.valueOf(0.0) : prevDayBookDebit;
        Double debit = prevLoadingDebit + prevUnloadingDebit + prevManufactureDebit + prevDayBookDebit;
        debit = Double.valueOf(df.format(debit));
        return debit;
    }

    public static Double getDealerCredit(Long id, LocalDate startDate, LocalDate endDate, DayBookRepository dayBookRepo) {
        Double sumDayBookCredit = dayBookRepo.findUserCredits(id, startDate, endDate);
        Double credit = sumDayBookCredit = Objects.isNull(sumDayBookCredit) ? Double.valueOf(0.0) : sumDayBookCredit;
        credit = Double.valueOf(df.format(credit));
        return credit;
    }

    public static Double getDealerDebit(Long id, LocalDate startDate, LocalDate endDate, DayBookRepository dayBookRepo, RawMaterialRepository rawMaterialRepo) {
        Double sumPreviousDebit = rawMaterialRepo.sumDebits(id, startDate, endDate);
        Double sumDayBookDebit = dayBookRepo.findUserDebits(id, startDate, endDate);
        sumPreviousDebit = Objects.isNull(sumPreviousDebit) ? Double.valueOf(0.0) : sumPreviousDebit;
        sumDayBookDebit = Objects.isNull(sumDayBookDebit) ? Double.valueOf(0.0) : sumDayBookDebit;
        Double debit = sumPreviousDebit + sumDayBookDebit;
        debit = Double.valueOf(df.format(debit));
        return debit;
    }

    public static Double format(Object number) {
        System.err.println(number);
        return Objects.isNull(number) ? Double.valueOf(0.0) : Double.valueOf(df.format(number));
    }
}
