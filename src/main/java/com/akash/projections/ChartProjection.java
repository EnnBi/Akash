package com.akash.projections;

import java.time.LocalDate;

public interface ChartProjection {
    public int getQuantity();

    public String getName();

    public LocalDate getDate();
}
