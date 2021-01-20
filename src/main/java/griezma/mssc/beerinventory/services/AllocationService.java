package griezma.mssc.beerinventory.services;

import griezma.mssc.beerinventory.config.JmsConfig;
import griezma.mssc.beerinventory.data.BeerInventory;
import griezma.mssc.beerinventory.data.BeerInventoryRepository;
import griezma.mssc.brewery.model.BeerOrderDto;
import griezma.mssc.brewery.model.BeerOrderLineDto;
import griezma.mssc.brewery.model.events.AllocateOrderRequest;
import griezma.mssc.brewery.model.events.AllocateOrderResponse;
import griezma.mssc.brewery.model.events.DeallocateOrderRequest;
import griezma.mssc.brewery.model.events.DeallocateOrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AllocationService {
    private final BeerInventoryRepository repo;
    private final JmsTemplate jms;

    @JmsListener(destination = JmsConfig.ALLOCATEORDER_REQUEST_QUEUE)
    @Transactional
    public void allocateOrderRequestHandler(AllocateOrderRequest allocateOrderRequest) {
        BeerOrderDto order = allocateOrderRequest.getOrder();
        boolean success = allocateOrder(order);
        AllocateOrderResponse response;
        if (success) {
            response = AllocateOrderResponse.builder().order(order).orderFilled(true).build();
        } else {
            response = AllocateOrderResponse.builder().order(order).orderFilled(false).build();
        }
        jms.convertAndSend(JmsConfig.ALLOCATEORDER_RESPONSE_QUEUE, response);
    }

    @Transactional
    public boolean allocateOrder(BeerOrderDto order) {
        assert TransactionSynchronizationManager.isActualTransactionActive() : "tx required";

        log.debug("allocateOrder {}", order);
        int totalOrdered = 0, totalAllocated = 0;
        for (BeerOrderLineDto orderLine : order.getOrderLines()) {
            totalOrdered += orderLine.getOrderQuantity();
            totalAllocated += allocateOrderLine(orderLine);
        }
        log.debug("result: totalOrdered {}, totalAllocated {}", totalOrdered, totalAllocated);
        return totalOrdered == totalAllocated;
    }

    int allocateOrderLine(BeerOrderLineDto orderLine) {
        int quantityToFill = orderLine.getOrderQuantity();
        var beerInventory = repo.findAllByBeerId(orderLine.getBeerId());

        for (BeerInventory inventoryItem : beerInventory) {
            int onHand = inventoryItem.getQuantityOnHand();
            int allocated = Math.min(quantityToFill, onHand);
            quantityToFill -= allocated;

            int remaining = onHand - allocated;
            if (remaining > 0) {
                inventoryItem.setQuantityOnHand(remaining);
            }  else {
                repo.delete(inventoryItem);
            }
            if (quantityToFill == 0) {
                break;
            }
        }
        orderLine.setAllocatedQuantity(orderLine.getOrderQuantity() - quantityToFill);
        return orderLine.getAllocatedQuantity();
    }

    @JmsListener(destination = JmsConfig.DEALLOCATE_ORDER_QUEUE)
    @Transactional
    public void deallocateOrderRequest(DeallocateOrderRequest deallocateOrderRequest) {
        BeerOrderDto order = deallocateOrderRequest.getOrder();
        deallocateOrder(order);
        jms.convertAndSend(JmsConfig.DEALLOCATE_ORDER_RESPONSE_QUEUE, DeallocateOrderResponse.builder()
                .orderId(order.getId())
                .complete(true)
                .build());
    }

    @Transactional
    public void deallocateOrder(BeerOrderDto order) {
        log.debug("deallocateOrder {}", order);
        order.getOrderLines().forEach(this::deallocateOrderLine);
    }

    private void deallocateOrderLine(BeerOrderLineDto orderLine) {
        BeerInventory deallocated = BeerInventory.builder()
                .beerId(orderLine.getBeerId())
                .quantityOnHand(orderLine.getAllocatedQuantity())
                .build();
        repo.save(deallocated);
        orderLine.setAllocatedQuantity(0);
    }
}
