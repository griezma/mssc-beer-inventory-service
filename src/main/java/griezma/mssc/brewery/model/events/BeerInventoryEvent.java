package griezma.mssc.brewery.model.events;

import griezma.mssc.brewery.model.BeerDto;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
public class BeerInventoryEvent implements Serializable {
    static final long serialVersionUID = -2806840971337102266L;

    private BeerDto beer;
    private final OffsetDateTime timestamp = OffsetDateTime.now();
    private final Integer quantityAdded;

    @Builder
    public BeerInventoryEvent(BeerDto beer, Integer quantityAdded) {
        this.quantityAdded = quantityAdded;
    }
}
