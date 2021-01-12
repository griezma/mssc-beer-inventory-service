package griezma.mssc.beerinventory.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BeerInventory {
    @Id
    @GeneratedValue
    private UUID id;

    @Version
    private Long version;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp created;

    @UpdateTimestamp
    private Timestamp lastModified;

    public boolean isNew() {
        return this.id == null;
    }

    @Column(updatable = false)
    private UUID beerId;

    @Column(updatable = false)
    private String upc;

    private int quantityOnHand = 0;
}

