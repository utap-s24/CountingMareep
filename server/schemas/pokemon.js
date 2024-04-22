/**
 * var name: String,
    var level: Int,
    val pokedexEntry: Int,
    val subSkills: List<SubSkill>,
    val ingredients: List<Ingredient>,
    val nature: NatureData,
    var RP: Int,
    var mainSkillLevel: Int,
    val pokemonID: String
 */
import mongoose from 'mongoose';

const pokemonSchema = new mongoose.Schema({
    name: String,
    level: Number,
    pokedexEntry: Number,
    subSkills: [Number],
    ingredients: [[Number]], // Ingredient(enum, quantity)
    nature: String, // Nature.natureFromName("Bold")
    RP: Number,
    mainSkillLevel: Number,
    pokemonID: String
});

export const Pokemon = mongoose.model('User', userSchema);