package com.example.demo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ShopItem
{

  public ShopItem()
  {
  }

  public ShopItem(final Integer id,
                  final String name,
                  final ShopItemType type)
  {
    this.id = id;
    this.name = name;
    this.type = type;
  }

  private Integer id;

  @NotNull
  @NotBlank
  private String name;

  @NotNull
  private ShopItemType type;

  public Integer getId()
  {
    return id;
  }

  public void setId(final Integer id)
  {
    this.id = id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(final String name)
  {
    this.name = name;
  }

  public ShopItemType getType()
  {
    return type;
  }

  public void setType(final ShopItemType type)
  {
    this.type = type;
  }
}
