package com.example.demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/shopitems")
@Tag(name = "Shop items API", description = "API used to manage shop items")
@Validated
public class ShopItemsController {

  private final ShopItemRepository shopItemRepository;

  public ShopItemsController(final ShopItemRepository shopItemRepository) {
    this.shopItemRepository = shopItemRepository;
  }

  @Operation(summary = "Get list of shop items")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of shop items retrieved successfully", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ShopItemsListResponse.class))
      }),
  })
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ShopItemsListResponse getShopItems() {
    return new ShopItemsListResponse(shopItemRepository.findAll());
  }

  @Operation(summary = "Create a shop item")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Shop item has been created successfully", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ShopItem.class))
      })
  })
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public ShopItem createShopItem(
      @RequestBody ShopItem shopItem
  ) {
    return shopItemRepository.create(shopItem);
  }

  private static final class ShopItemsListResponse
  {
    private final List<ShopItem> shopItems;

    public ShopItemsListResponse(List<ShopItem> shopItems) {
      this.shopItems = shopItems;
    }

    public List<ShopItem> getShopItems() {
      return shopItems;
    }
  }

}
