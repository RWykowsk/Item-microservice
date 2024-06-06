package com.example.demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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

  @Operation(summary = "Search shop item by id, name and type")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Shop item retrieved successfully", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ShopItem.class))}),})
  @GetMapping("/search")
  public ShopItem getShopItemByIdAndNameAndType(@RequestParam("id") Integer id,
                                                @RequestParam("name") String name,
                                                @RequestParam("type") ShopItemType type)
  {
    return shopItemRepository.findByIdAndNameAndShopItemType(id, name, type)
                             .orElseThrow(() -> new IllegalStateException("Shop item not found"));
  }

  @Operation(summary = "Create a shop item")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Shop item has been created successfully", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CreateShopItemDto.class))
      })
  })
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public ShopItem createShopItem(
      @Valid @RequestBody CreateShopItemDto shopItem
  ) {
    return shopItemRepository.create(ShopItem.fromCreateDto(shopItem));
  }

  @Operation(summary = "Create shop items")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Shop items have been created successfully", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ShopItemsCreateListRequest.class))
      })
  })
  @PostMapping(path ="/bulk", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void createShopItems(
      @Valid @RequestBody ShopItemsCreateListRequest shopItems
  ) {
    shopItemRepository.createAll(shopItems.getShopItems().stream().map(ShopItem::fromCreateDto).toList());
  }

  @Operation(summary = "Update a shop item")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Shop item has been updated successfully", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ShopItem.class))
      })
  })
  @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ShopItem updateShopItem(
      @Valid @RequestBody ShopItem shopItem
  ) {
    return shopItemRepository.update(shopItem);
  }

  @Operation(summary = "Delete a shop item")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Shop item has been deleted successfully")
  })
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteShopItem(
      @PathVariable Integer id
  ) {
    shopItemRepository.delete(id);
  }

  private static final class ShopItemsListResponse
  {
    @NotNull
    private final List<ShopItem> shopItems;

    public ShopItemsListResponse(List<ShopItem> shopItems) {
      this.shopItems = shopItems;
    }

    public List<ShopItem> getShopItems() {
      return shopItems;
    }
  }

  private static final class ShopItemsCreateListRequest
  {

    public ShopItemsCreateListRequest()
    {
    }

    @Valid
    @NotNull
    private List<CreateShopItemDto> shopItems;

    public ShopItemsCreateListRequest(List<CreateShopItemDto> shopItems) {
      this.shopItems = shopItems;
    }

    public List<CreateShopItemDto> getShopItems() {
      return shopItems;
    }

    public void setShopItems(final List<CreateShopItemDto> shopItems)
    {
      this.shopItems = shopItems;
    }
  }

}
