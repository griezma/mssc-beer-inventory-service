package griezma.mssc.beerinventory.services;

import griezma.mssc.beerinventory.entities.BeerInventory;
import griezma.mssc.beerinventory.repositories.BeerInventoryRepository;
import griezma.mssc.brewery.model.BeerOrderDto;
import griezma.mssc.brewery.model.BeerOrderLineDto;
import griezma.mssc.brewery.model.BeerStyle;
import griezma.mssc.brewery.model.OrderStatus;
import griezma.mssc.brewery.model.events.AllocateOrderResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles("test")
class AllocationServiceTest {
    @Autowired
    AllocationService service;

    @Autowired
    JmsTemplate jms;

    @MockBean
    BeerInventoryRepository mockRepo;

    @Test
    void canSendAllocateOrderResponse() {
        var response = AllocateOrderResponse.builder()
                .order(validOrder(UUID.randomUUID(), 42))
                .orderFilled(true)
                .build();
        jms.convertAndSend("test-queue", response);
    }

    @Test
    void allocateOrderWithSufficientInventory() {
        UUID beerId = UUID.randomUUID();
        var inventoryList = List.of(inventory(beerId, 100), inventory(beerId, 42));

        given(mockRepo.findAllByBeerId(any())).willReturn(inventoryList);

        var order = validOrder(beerId, 99);

        assertTrue(service.allocateOrder(order));
    }

    @Test
    void allocateOrderWithLackingInventory() {
        UUID beerId = UUID.randomUUID();
        var inventoryList = List.of(inventory(beerId, 100), inventory(beerId, 42));

        given(mockRepo.findAllByBeerId(any())).willReturn(inventoryList);

        var order = validOrder(beerId, 143);

        assertFalse(service.allocateOrder(order));
    }

    BeerInventory inventory(UUID beerId, int quantity) {
        return BeerInventory.builder()
                .beerId(beerId)
                .upc(beerId.toString())
                .quantityOnHand(quantity)
                .build();

    }

    BeerOrderDto validOrder(UUID beerId, int quantity) {
        return BeerOrderDto.builder()
                .id(beerId)
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .orderStatus(OrderStatus.ALLOCATION_PENDING)
                .customerId(UUID.randomUUID())
                .orderLines(List.of(beerOrderLine(beerId, quantity)))
                .build();
    }

    private BeerOrderLineDto beerOrderLine(UUID beerId, int quantity) {
        return BeerOrderLineDto.builder()
                .beerId(beerId)
                .beerStyle(BeerStyle.ALE.toString())
                .beerName("fucking hell")
                .price(BigDecimal.TEN)
                .upc("0123456789")
                .orderQuantity(quantity)
                .build();

    }

}