package com.example.demo;

import java.util.List;
import java.util.Optional;

public interface ShopItemRepository
{
  ShopItem create(ShopItem shopItem);

  void createAll(List<ShopItem> shopItems);

  void delete(Integer id);

  ShopItem update(ShopItem shopItem);

  List<ShopItem> findAll();

  Optional<ShopItem> findByIdAndNameAndShopItemType(Integer id, String name, ShopItemType shopItemType);
}
