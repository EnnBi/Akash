package com.akash.util;

import com.akash.entity.BillBook;
import com.akash.entity.CustomerStatement;
import java.util.ArrayList;
import java.util.List;

public class BillBookToCustomerStatement {
    public static List<CustomerStatement> convert(List<BillBook> billBooks) {
        ArrayList<CustomerStatement> customerStatements = new ArrayList<CustomerStatement>();
        billBooks.forEach(b -> {
            Double discount = b.getDiscount() == null ? Double.valueOf(0.0) : b.getDiscount();
            b.setTotal(b.getTotal() - discount);
            CustomerStatement customerStatement = new CustomerStatement(b.getId(), b.getReceiptNumber(), b.getDate(), b.getVehicle(), b.getOtherVehicle(), b.getCustomer().getAddress(), b.getSites(), b.getLoadingAmount(), b.getUnloadingAmount(), b.getCarraige(), b.getDiscount(), b.getTotal());
            customerStatements.add(customerStatement);
        });
        return customerStatements;
    }
}
