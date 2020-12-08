package griezma.mssc.beerinventory.web.mappers;

import griezma.mssc.beerinventory.domain.BeerInventory;
import griezma.mssc.beerinventory.web.model.BeerInventoryDto;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
public interface BeerInventoryMapper {

    BeerInventoryDto beerInventoryToDto(BeerInventory beerInventory);

    BeerInventory dtoToBeerInventory(BeerInventoryDto beerInventoryDto);
}
