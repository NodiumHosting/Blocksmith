# Items

Data format of Blocksmith items.

# Format
- `id` - The [NID](../../NID.md) of the item
- `translation?` - The translation key for the item's name, defaults to `item.<namespace>.<id>`
- `material` - The [NID](../../NID.md) of the vanilla item to use as the item base
- `model?` - The [NID](../../NID.md) of the [Model](../../assets/models/Models.md) to use
- `components?` - An array of [Item Component Instances](./components/ItemComponentInstance.md)
- `properties?` - An array of [Item Properties](./properties/ItemProperties.md)
- `states?` - An array of [Item States](./states/ItemStates.md)

# Example
```
{
  "id": "blocksmith:leaping_orb",
  "material": "minecraft:slime_ball",
  "model": "blocksmith:item/leaping_orb",
  "components": [
    {
      "id": "blocksmith:rarity",
      "rarity": "uncommon"
    }
  ],
  "properties": [
    {
      "key": "blocksmith:leaping_height",
      "type": "int[2, 5]",
      "default": 3
    }
  ],
  "states": [
    {
      "predicate": "blocksmith:leaping_height == 2",
      "translation": "item.blocksmith.leaping_orb_2",
      "model": "blocksmith:item/leaping_orb_2",
      "material": "minecraft:slime_ball"
    }
  ]
}
```