package com.example.countingmareep.ui.box

enum class Ingredients {
    BEAN_SAUSAGE,
    FANCY_APPLE,
    FANCY_EGG,
    FIERY_HERB,
    GREENGRASS_CORN,
    GREENGRASS_SOYBEANS,
    HONEY,
    LARGE_LEEK,
    MOOMOO_MILK,
    PURE_OIL,
    SLOWPOKE_TAIL,
    SNOOZY_TOMATO,
    SOOTHING_CACAO,
    SOFT_POTATO,
    TASTY_MUSHROOM,
    WARMING_GINGER
}

class Ingredient(var id: Ingredients, var quantity: Int) {
    companion object {
        fun getIngredientFromOrdinal(ordinal: Int): Ingredients {
            return Ingredients.entries[ordinal]
        }
    }
}