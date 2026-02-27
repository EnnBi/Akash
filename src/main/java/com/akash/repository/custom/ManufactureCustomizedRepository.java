package com.akash.repository.custom;

import com.akash.entity.Manufacture;
import com.akash.entity.ManufactureSearch;
import com.akash.entity.dto.ManufactureDTO;
import java.util.List;

public interface ManufactureCustomizedRepository {
    public List<ManufactureDTO> searchPaginated(ManufactureSearch search, int pageNumber);

    public long count(ManufactureSearch search);

    public List<Manufacture> searchCementConsumption(ManufactureSearch search, int from);

    public long countCementConsumption(ManufactureSearch search);
}
