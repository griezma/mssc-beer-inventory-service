package griezma.mssc.beerinventory.services;

import griezma.mssc.beerinventory.config.JmsConfig;
import griezma.mssc.beerservice.events.BeerInventoryEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryEventListener {
    private final InventoryService inventoryService;

    @JmsListener(destination = JmsConfig.INVENTORY_EVENT_QUEUE)
    void handleNewInventoryEvent(BeerInventoryEvent event) {
        inventoryService.addInventory(event.getBeer(), event.getQuantityAdded());
    }
}
