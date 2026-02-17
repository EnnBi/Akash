package com.akash.repository.custom;

import com.akash.entity.RawMaterial;
import com.akash.entity.RawMaterialSearch;
import java.util.List;

public interface RawMaterialCustomizedRepository {
    public List<RawMaterial> searchRawMaterialPaginated(RawMaterialSearch search, int pageNumber);

    public long searchRawMaterialsCount(RawMaterialSearch search);
}
