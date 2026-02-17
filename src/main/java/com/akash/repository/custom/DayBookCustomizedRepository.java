package com.akash.repository.custom;

import com.akash.entity.DayBook;
import com.akash.entity.DayBookSearch;
import java.util.List;

public interface DayBookCustomizedRepository {
    public List<DayBook> searchPaginated(DayBookSearch search, int pageNumber);

    public long count(DayBookSearch search);
}
