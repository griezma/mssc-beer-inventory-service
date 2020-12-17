package griezma.mssc.beerservice.events;

import griezma.mssc.beerservice.api.model.BeerDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BeerInventoryEvent extends BeerEvent {

    private final Integer quantityAdded;

    @Builder
    public BeerInventoryEvent(BeerDto beer, Integer quantityAdded) {
        super(beer);
        this.quantityAdded = quantityAdded;
    }
}
