package griezma.mssc.beerinventory.web.mappers;

import griezma.mssc.beerinventory.data.BeerInventory;
import griezma.mssc.beerinventory.web.model.BeerInventoryDto;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
public interface BeerInventoryMapper {

    BeerInventoryDto beerInventoryToDto(BeerInventory beerInventory);

    BeerInventory dtoToBeerInventory(BeerInventoryDto beerInventoryDto);
}
