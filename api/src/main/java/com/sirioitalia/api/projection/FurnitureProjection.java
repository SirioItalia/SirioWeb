package com.sirioitalia.api.projection;

import com.sirioitalia.api.embeddable.Dimension;
import com.sirioitalia.api.model.Category;

import java.util.Collection;

public interface FurnitureProjection {
    Long getId();

    String getLabel();

    String getDescription();

    Category getCategory();

    Dimension getDimension();

    Double getWeight();

    Double getPrice();

    Collection<ItemProjection.Full> getItems();
}
