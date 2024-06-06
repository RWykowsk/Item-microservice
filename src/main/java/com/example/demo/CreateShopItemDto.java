package com.example.demo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateShopItemDto(@NotNull @NotBlank String name, @NotNull ShopItemType type)
{
}
