package com.example.shop;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryShopItemRepository implements ShopItemRepository
{
  private static final Map<Integer, ShopItem> ID_TO_SHOP_ITEM = new HashMap<>();
  private static final AtomicInteger ID_COUNTER = new AtomicInteger(0);
  @Override
  public ShopItem create(final ShopItem shopItem)
  {
    final int id = ID_COUNTER.incrementAndGet();
    shopItem.setId(id);
    ID_TO_SHOP_ITEM.put(id, shopItem);
    return shopItem;
  }

  @Override
  public void createAll(final List<ShopItem> shopItems)
  {
   shopItems.forEach(this::create);
  }

  @Override
  public void delete(final Integer id)
  {
    ID_TO_SHOP_ITEM.remove(id);
  }

  @Override
  public ShopItem update(final ShopItem shopItem)
  {
    return Optional.ofNullable(ID_TO_SHOP_ITEM.get(shopItem.getId()))
        .map(item-> ID_TO_SHOP_ITEM.put(item.getId(), shopItem))
        .orElseThrow(()-> new IllegalStateException(String.format("Shop item with id %d not found", shopItem.getId())));
  }

  @Override
  public List<ShopItem> findAll()
  {
    return ID_TO_SHOP_ITEM.values().stream().toList();
  }

  @Override
  public Optional<ShopItem> findByIdAndNameAndShopItemType(final Integer id,
                                                           final String name,
                                                           final ShopItemType shopItemType)
  {
    return Optional.ofNullable(ID_TO_SHOP_ITEM.get(id))
                   .filter(item -> item.getName()
                                       .equals(name) && item.getType()
                                                            .equals(shopItemType));
  }

  @Override
  public void reset()
  {
    ID_TO_SHOP_ITEM.clear();
    ID_COUNTER.set(0);
  }
}
