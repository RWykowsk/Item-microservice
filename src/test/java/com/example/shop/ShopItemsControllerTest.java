package com.example.shop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class ShopItemsControllerTest
{
  @Autowired
  ShopItemsController shopItemsController;
  @Autowired
  ShopItemRepository shopItemRepository;

  @BeforeEach
  void clearDb(){
    shopItemRepository.reset();
  }

  @Test
  void shallCreateShopItem(){
    //given
    final CreateShopItemDto item = new CreateShopItemDto("test1", ShopItemType.GARDEN);
    //when
    shopItemsController.createShopItem(item);
    //then
    Assertions.assertEquals(1,shopItemsController.getShopItems().getShopItems().size());
  }

  @Test
  void shallCreate4ShopItems(){
    //given
    final CreateShopItemDto item = new CreateShopItemDto("test1", ShopItemType.GARDEN);
    final CreateShopItemDto item2 = new CreateShopItemDto("test2", ShopItemType.HOME);
    final CreateShopItemDto item3 = new CreateShopItemDto("test3", ShopItemType.SPORT);
    final CreateShopItemDto item4 = new CreateShopItemDto("test4", ShopItemType.OTHER);
    //when
    shopItemsController.createShopItems(new ShopItemsController.ShopItemsCreateListRequest(List.of(item,item2, item3, item4)));
    //then
    Assertions.assertEquals(4,shopItemsController.getShopItems().getShopItems().size());
  }

  @Test
  void shallUpdateShopItem(){
    //given
    final CreateShopItemDto item = new CreateShopItemDto("test1", ShopItemType.GARDEN);
    final ShopItem shopItem = shopItemsController.createShopItem(item);
    final ShopItem updateItem = new ShopItem(shopItem.getId(), "test2", ShopItemType.HOME);
    //when
    shopItemsController.updateShopItem(updateItem);
    //then
    final List<ShopItem> shopItems = shopItemsController.getShopItems()
                                                        .getShopItems();
    Assertions.assertEquals(1, shopItems.size());
    Assertions.assertEquals("test2",shopItems.get(0).getName());
    Assertions.assertEquals(ShopItemType.HOME,shopItems.get(0).getType());
  }

  @Test
  void shallDeleteShopItem(){
    //given
    final CreateShopItemDto item = new CreateShopItemDto("test1", ShopItemType.GARDEN);
    final ShopItem shopItem = shopItemsController.createShopItem(item);
    //when
    shopItemsController.deleteShopItem(shopItem.getId());
    //then
    final List<ShopItem> shopItems = shopItemsController.getShopItems()
                                                        .getShopItems();
    Assertions.assertEquals(0, shopItems.size());
  }

  @Test
  void shallFindShopItem(){
    //given
    final CreateShopItemDto item = new CreateShopItemDto("test1", ShopItemType.GARDEN);
    final ShopItem shopItem = shopItemsController.createShopItem(item);
    //when
    final ShopItem result = shopItemsController.getShopItemByIdAndNameAndType(shopItem.getId(), shopItem.getName(),
                                                                              shopItem.getType());
    //then
    Assertions.assertEquals("test1", result.getName());
    Assertions.assertEquals(ShopItemType.GARDEN, result.getType());
    Assertions.assertEquals(shopItem.getId(), result.getId());
  }
}
