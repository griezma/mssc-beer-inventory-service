package griezma.mssc.brewery.model.events;

import griezma.mssc.brewery.model.BeerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Getter
@Builder @NoArgsConstructor @AllArgsConstructor
public class BeerInventoryEvent {
    private BeerDto beer;
    private OffsetDateTime timestamp = OffsetDateTime.now();
    private Integer quantityAdded;
}
