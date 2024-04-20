package com.example.countingmareep.ui.box

import java.util.Random

data class NatureData(
    val nature: String,
    val fieldUp: String,
    val fieldUpAmount: Int,
    val fieldDown: String,
    val fieldDownAmount: Int
)

class Nature {
    // TODO
    companion object {
        val natures = listOf(
            NatureData("Lonely", "Speed of Help", 10, "Energy Recovery", 12),
            NatureData("Adamant", "Speed of Help", 10, "Ingredient Finding", 20),
            NatureData("Naughty", "Speed of Help", 10, "Main Skill Chance", 20),
            NatureData("Brave", "Speed of Help", 10, "EXP Gains", 18),
            NatureData("Bold", "Energy Recovery", 20, "Speed of Help", 10),
            NatureData("Impish", "Energy Recovery", 20, "Ingredient Finding", 20),
            NatureData("Lax", "Energy Recovery", 20, "Main Skill Chance +", 20),
            NatureData("Relaxed", "Energy Recovery", 20, "EXP Gains", 18),
            NatureData("Modest", "Ingredient Finding", 20, "Speed of Help", 10),
            NatureData("Mild", "Ingredient Finding", 20, "Energy Recovery", 12),
            NatureData("Rash", "Ingredient Finding", 20, "Main Skill Chance +", 20),
            NatureData("Quiet", "Ingredient Finding", 20, "Exp", 0),
            NatureData("Calm", "Main Skill Chance", 20, "Speed of Help", 10),
            NatureData("Gentle", "Main Skill Chance", 20, "Energy Recovery", 12),
            NatureData("Careful", "Main Skill Chance", 20, "Ingredient Finding", 20),
            NatureData("Sassy", "Main Skill Chance", 20, "EXP Gains", 18),
            NatureData("Timid", "EXP Gains", 18, "Speed of Help", 10),
            NatureData("Hasty", "EXP Gains", 18, "Energy Recovery", 12),
            NatureData("Jolly", "EXP Gains", 18, "Ingredient Finding", 20),
            NatureData("Naive", "EXP Gains", 18, "Main Skill Chance", 20),
            NatureData("Bashful", "", 0, "", 0),
            NatureData("Hardy", "", 0, "", 0),
            NatureData("Docile", "", 0, "", 0),
            NatureData("Quirky", "", 0, "", 0),
            NatureData("Serious", "", 0, "", 0)
        )

        val natureNames = listOf(
            "Lonely",
            "Adamant",
            "Naughty",
            "Brave",
            "Bold",
            "Impish",
            "Lax",
            "Relaxed",
            "Modest",
            "Mild",
            "Rash",
            "Quiet",
            "Calm",
            "Gentle",
            "Careful",
            "Sassy",
            "Timid",
            "Hasty",
            "Jolly",
            "Naive",
            "Bashful",
            "Hardy",
            "Docile",
            "Quirky",
            "Serious"
        )

        fun natureFromIndex(index: Int): NatureData {
            return natures[index]
        }

        fun natureFromName(natureName: String): NatureData {
            return natures[natureNames.indexOf(natureName)]
        }

        fun getRandom(): NatureData {
            val rand = Random()
            return natures[rand.nextInt(natures.size)]
        }
    }
}