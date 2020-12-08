package griezma.mssc.beerinventory.domain;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class BeerInventory extends BaseEntity {
    @Column(updatable = false)
    private UUID beerId;
    @Column(updatable = false)
    private String upc;
    private Integer quantityOnHand = 0;
}
