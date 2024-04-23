import mongoose from 'mongoose';

const teamSchema = new mongoose.Schema({
    ownerName: String,
    teamName: String,
    pokemon1ID: String,
    pokemon2ID: String, // "null" if Pokemon not Defined
    pokemon3ID: String,
    pokemon4ID: String,
    pokemon5ID: String
});

export const Team = mongoose.model('Team', teamSchema);