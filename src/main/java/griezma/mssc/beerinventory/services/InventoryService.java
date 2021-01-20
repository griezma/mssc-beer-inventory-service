package griezma.mssc.beerinventory.services;

import griezma.mssc.beerinventory.data.BeerInventory;
import griezma.mssc.beerinventory.data.BeerInventoryRepository;
import griezma.mssc.brewery.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryService {
    private final BeerInventoryRepository repo;

    public void addInventory(BeerDto beer, Integer quantityAdded) {
        log.debug("addInventory of {} to {}", quantityAdded, beer);
        BeerInventory inventory = repo.findById(beer.getId())
                .orElseGet(() -> initialInventory(beer));
        inventory.setQuantityOnHand(inventory.getQuantityOnHand() + quantityAdded);
        repo.save(inventory);
    }

    private BeerInventory initialInventory(BeerDto beer) {
        return BeerInventory.builder()
                .beerId(beer.getId())
                .quantityOnHand(beer.getQuantityOnHand())
                .build();
    }
}
