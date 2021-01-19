package griezma.mssc.beerinventory.api.mappers;

import griezma.mssc.beerinventory.data.BeerInventory;
import griezma.mssc.beerinventory.api.model.BeerInventoryDto;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
public interface BeerInventoryMapper {

    BeerInventoryDto beerInventoryToDto(BeerInventory beerInventory);

    BeerInventory dtoToBeerInventory(BeerInventoryDto beerInventoryDto);
}
