package com.example.countingmareep.ui.box

enum class Rarity {
    COMMON, // White
    RARE, // Blue
    VERY_RARE // Gold
}

data class SkillData(
    val rarity: Rarity,
    val skillName: String,
    val description: String
)

enum class Skills {
    BerryFindingS,
    DreamShardBonus,
    EnergyRecoveryBonus,
    HelpingBonus,
    ResearchEXPBonus,
    SkillLevelM,
    SleepEXPBonus,
    HelpingSpeedM,
    IngredientFinderM,
    InventoryUpL,
    InventoryUpM,
    SkillLevelS,
    SkillTriggerM,
    HelpingSpeedS,
    IngredientFinderS,
    InventoryUpS,
    SkillTriggerS
}

class Skill(id: Skills) {
    companion object {
        val SKILLS = listOf(
            SkillData(
                Rarity.VERY_RARE,
                "Berry Finding S",
                "Increases the number of Berries this Pokémon finds at one time by 1."
            ),
            SkillData(
                Rarity.VERY_RARE,
                "Dream Shard Bonus",
                "Boosts the number of Dream Shards you earn from sleep research by 6%."
            ),
            SkillData(
                Rarity.VERY_RARE,
                "Energy Recovery Bonus",
                "Multiplies the Energy the team recovers from sleep sessions by 1.12."
            ),
            SkillData(
                Rarity.VERY_RARE,
                "Helping Bonus",
                "Reduces the time needed by team members to help out by 5%."
            ),
            SkillData(
                Rarity.VERY_RARE,
                "Research EXP Bonus",
                "Boosts the EXP you earn from doing sleep research by 6%."
            ),
            SkillData(
                Rarity.VERY_RARE,
                "Skill Level Up M",
                "Boosts the level of this Pokémon's main skill by 2."
            ),
            SkillData(
                Rarity.VERY_RARE,
                "Sleep EXP Bonus",
                "Boosts the EXP earned by the team after sleep sessions by 14%."
            ),
            SkillData(
                Rarity.RARE,
                "Helping Speed M",
                "Reduces the time needed for this Pokémon to help out by 14%."
            ),
            SkillData(
                Rarity.RARE,
                "Ingredient Finder M",
                "Boosts the likelihood of this Pokémon finding ingredients by 36%."
            ),
            SkillData(
                Rarity.RARE,
                "Inventory Up L",
                "Increases the max number of Berries and ingredients this Pokémon can have by 18."
            ),
            SkillData(
                Rarity.RARE,
                "Inventory Up M",
                "Increases the max number of Berries and ingredients this Pokémon can have by 12."
            ),
            SkillData(
                Rarity.RARE,
                "Skill Level Up S",
                "Boosts the level of this Pokémon's main skill by 1."
            ),
            SkillData(
                Rarity.RARE,
                "Skill Trigger M",
                "Boosts the chance of this Pokémon's main skill being used by 36%."
            ),
            SkillData(
                Rarity.COMMON,
                "Helping Speed S",
                "Reduces the time needed for this Pokémon to help out by 7%."
            ),
            SkillData(
                Rarity.COMMON,
                "Ingredient Finder S",
                "Slightly boosts the likelihood of this Pokémon finding ingredients by 18%."
            ),
            SkillData(
                Rarity.COMMON,
                "Inventory Up S",
                "Increases the max number of Berries and ingredients this Pokémon can have by 6."
            ),
            SkillData(
                Rarity.COMMON,
                "Skill Trigger S",
                "Slightly boosts the chance of this Pokémon's main skill being used by 18%."
            )
        )
    }

    private var thisSkill = SKILLS[id.ordinal]

    fun getDesc(): String {
        return thisSkill.description
    }

    fun getRarity(): Rarity {
        return thisSkill.rarity
    }

    fun getName(): String {
        return thisSkill.skillName
    }
}