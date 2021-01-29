package griezma.mssc.beerinventory.data;

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
    @Column(columnDefinition = "char(36)", updatable = false)
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

    private int quantityOnHand = 0;
}

