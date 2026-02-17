package com.akash.repository.custom;

import com.akash.entity.BillBook;
import com.akash.entity.GoodsReturn;
import com.akash.entity.GoodsReturnSearch;
import java.util.List;

public interface GoodsReturnCustomizedRepository {
    public GoodsReturn billBookToGoodsReturnMapping(BillBook billBook);

    public List<GoodsReturn> searchPaginated(GoodsReturnSearch search, int pageNumber);

    public long count(GoodsReturnSearch search);
}
