package griezma.mssc.beerinventory.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerInventoryDto {
    private UUID id;
    private OffsetDateTime created;
    private OffsetDateTime lastModified;
    private UUID beerId;
    private String upc;
    private Integer quantityOnHand;
}
