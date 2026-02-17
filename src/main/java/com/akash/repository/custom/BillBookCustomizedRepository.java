package com.akash.repository.custom;

import com.akash.entity.BillBookSearch;
import com.akash.entity.dto.BillBookDTO;
import java.util.List;

public interface BillBookCustomizedRepository {
    public List<BillBookDTO> searchPaginated(BillBookSearch search, int pageNumber);

    public long count(BillBookSearch search);
}
