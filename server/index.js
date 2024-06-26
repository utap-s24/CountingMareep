import 'dotenv/config';
import express from "express";
import mongoose from 'mongoose';
import bcrypt from 'bcrypt';
import * as EmailValidator from 'email-validator';
import { v4 as uuidv4 } from 'uuid';
import cors from 'cors';
// Personal Files
import { User } from "./schemas/user.js";
import { Session } from './session.js';
import { Pokemon } from './schemas/pokemon.js';
import { Team } from './schemas/team.js';

const app = express();
const PORT = process.env.PORT || 3030;
const SALT_ROUNDS = 10;

const sessionsList = {};

app.use(cors());
app.use(express.json({ limit: '2kb' }));
app.use(express.urlencoded({ extended: true })); // for form data

/* Database Stuff */

async function connectToDB() {
    try {
        let x = await mongoose.connect(`mongodb+srv://emperorbob:${process.env.PASSWORD}@cluster0.d100l.mongodb.net/CountingMareep?retryWrites=true&w=majority`);
        console.log("connected");
    } catch (e) {
        console.log(e.message);
    }
}

function isAnyArgUndefined(inputs, argList) {
    for (const arg of argList) {
        if (inputs[arg] === undefined) {
            return true;
        }
    }
    return false;
}

app.get("/", (req, res) => {
    return res.json({ msg: "Success" });
});

app.get("/getAllUsers", async (req, res) => {
    let users = await User.find({});
    users = users.map(user => {
        return { name: user.name, rank: user.rank, befriended: user.befriended, hoursSlept: user.hoursSlept, birthday: Number(user.birthday) };
    });
    res.json(users);
});

// Get the user data for the user with the given sessionID
app.post("/getUserData", async (req, res) => {
    const inputs = req.body;
    if (!inputs) {
        return res.status(400).json({ msg: "Missing Request Body" });
    }
    const relevantArgs = ["sessionID"];
    if (isAnyArgUndefined(inputs, relevantArgs)) {
        return res.status(401).json({ msg: "Missing Field" });
    }
    const session = sessionsList[inputs.sessionID];
    if (!session) {
        return res.status(401).json({ msg: "Invalid Session" });
    }
    const user = await User.findOne({ name: session.username });
    if (user == null) {
        return res.status(404).json({ msg: "No user with that name" });
    }
    return res.status(200).json({ name: user.name, rank: user.rank, befriended: user.befriended, hoursSlept: user.hoursSlept, birthday: Number(user.birthday) });
});

// Update the user data for the user with the given sessionID
app.post("/updateUserData", async (req, res) => {
    const inputs = req.body;
    if (!inputs) {
        return res.status(400).json({ msg: "Missing Request Body" });
    }
    const relevantArgs = ["sessionID", "rank", "befriended", "hoursSlept", "birthday"];
    if (isAnyArgUndefined(inputs, relevantArgs)) {
        return res.status(401).json({ msg: "Missing Field" });
    }
    const session = sessionsList[inputs.sessionID];
    if (!session) {
        return res.status(401).json({ msg: "Invalid Session" });
    }

    await User.updateOne({ name: session.username }, { rank: inputs.rank, befriended: inputs.befriended, hoursSlept: inputs.hoursSlept, birthday: inputs.birthday });
    return res.status(200).json({ msg: "Success" });
});

app.post("/signUp", async (req, res) => {
    const inputs = req.body;
    if (!inputs) {
        return res.status(400).json({ msg: "Missing Request Body" });
    }
    const relevantArgs = ["name", "password", "rank", "befriended", "hoursSlept", "birthday", "email"];
    if (isAnyArgUndefined(inputs, relevantArgs)) {
        return res.status(401).json({ msg: "Missing Field" });
    }
    // Unique Username and Valid Email
    let nameAlreadyUsed = await User.findOne({ name: inputs.name });
    if (nameAlreadyUsed !== null) {
        return res.status(403).json({ msg: "Failed - Name Taken" });
    }
    if (!EmailValidator.validate(inputs.email)) {
        return res.status(403).json({ msg: "Invalid Email" });
    }
    // Valid Details, Create User
    const hashedPassword = bcrypt.hashSync(inputs.password, SALT_ROUNDS);
    await User.insertMany({
        email: inputs.email,
        name: inputs.name,
        password: hashedPassword,
        rank: inputs.rank,
        befriended: inputs.befriended,
        hoursSlept: inputs.hoursSlept,
        birthday: inputs.birthday
    });

    return res.status(201).json({ msg: "Success" });
});

/*
    name: String,
    password: String
 */
app.post("/login", async (req, res) => {
    const inputs = req.body;
    if (!inputs) {
        return res.status(400).json({ msg: "Missing Request Body", sessionID: "" });
    }
    const relevantArgs = ["name", "password"];
    if (isAnyArgUndefined(inputs, relevantArgs)) {
        return res.status(401).json({ msg: "Missing Field", sessionID: "" });
    }
    // Check if user is in DB
    const user = await User.findOne({ name: inputs.name });
    if (user == null) {
        return res.status(404).json({ msg: "No user with that name", sessionID: "" });
    }
    // Password Check
    if (bcrypt.compareSync(inputs.password, user.password)) {
        let sessionID = uuidv4();
        sessionsList[sessionID] = new Session(new Date(), sessionID, inputs.name);

        return res.status(200).json({ msg: "Success", sessionID: sessionID });
    }
    return res.status(401).json({ msg: "Incorrect Password", sessionID: "" });
});

app.post("/logout", async (req, res) => {
    const inputs = req.body;
    if (!inputs) {
        return res.status(400).json({ msg: "Missing Request Body" });
    }
    const relevantArgs = ["sessionID"];
    if (isAnyArgUndefined(inputs, relevantArgs)) {
        return res.status(401).json({ msg: "Missing Field" });
    }
    if (inputs.sessionID && sessionsList[inputs.sessionID]) {
        delete sessionsList[inputs.sessionID];
        return res.status(200).json({ msg: "Success" });
    } else {
        return res.status(401).json({ msg: "Invalid Session" });
    }
});

app.post("/createPokemon", async (req, res) => {
    const inputs = req.body;
    if (!inputs) {
        return res.status(400).json({ msg: "Missing Request Body" });
    }
    const relevantArgs = ["sessionID", "name", "level", "pokedexEntry", "subSkills", "ingredients", "nature", "RP", "mainSkillLevel", "pokemonID"];
    if (isAnyArgUndefined(inputs, relevantArgs)) {
        return res.status(401).json({ msg: "Missing Field" });
    }
    // Validate Session
    const session = sessionsList[inputs.sessionID];
    if (!session) {
        return res.status(401).json({ msg: "Invalid Session" });
    }
    await Pokemon.insertMany({
        ownerName: session.username,
        name: inputs.name,
        level: inputs.level,
        pokedexEntry: inputs.pokedexEntry,
        subSkills: inputs.subSkills,
        ingredients: inputs.ingredients,
        nature: inputs.nature,
        RP: inputs.RP,
        mainSkillLevel: inputs.mainSkillLevel,
        pokemonID: inputs.pokemonID
    });
    return res.status(200).json({ msg: "Success" });
});

app.post("/updatePokemon", async (req, res) => {
    const inputs = req.body;
    if (!inputs) {
        return res.status(400).json({ msg: "Missing Request Body" });
    }
    const relevantArgs = ["sessionID", "name", "level", "pokedexEntry", "subSkills", "ingredients", "nature", "RP", "mainSkillLevel", "pokemonID"];
    if (isAnyArgUndefined(inputs, relevantArgs)) {
        return res.status(401).json({ msg: "Missing Field" });
    }
    // Validate Session
    const session = sessionsList[inputs.sessionID];
    if (!session) {
        return res.status(401).json({ msg: "Invalid Session" });
    }
    await Pokemon.updateOne(
        { pokemonID: inputs.pokemonID, ownerName: session.username },
        {
            ownerName: session.username,
            name: inputs.name,
            level: inputs.level,
            pokedexEntry: inputs.pokedexEntry,
            subSkills: inputs.subSkills,
            ingredients: inputs.ingredients,
            nature: inputs.nature,
            RP: inputs.RP,
            mainSkillLevel: inputs.mainSkillLevel,
            pokemonID: inputs.pokemonID
        });
    return res.status(200).json({ msg: "Success" });
});

/*
 * sessionID 
 */
app.post("/getPokemon", async (req, res) => {
    const inputs = req.body;
    if (!inputs) {
        return res.status(400).json({ msg: "Missing Request Body" });
    }
    const session = sessionsList[inputs.sessionID];
    if (!session) {
        return res.status(401).json({ msg: "Invalid Session" });
    }
    let result = await Pokemon.find({ ownerName: session.username });
    return res.status(200).json(result);
});

app.post("/getSinglePokemon", async (req, res) => {
    const inputs = req.body;
    if (!inputs) {
        return res.status(400).json({ msg: "Missing Request Body" });
    }
    const relevantArgs = ["pokemonID"];
    if (isAnyArgUndefined(inputs, relevantArgs)) {
        return res.status(401).json({ msg: "Missing Field" });
    }

    return res.status(200).json(await Pokemon.findOne({ pokemonID: inputs.pokemonID }));
});

// Get all teams for the current user
app.post("/getTeams", async (req, res) => {
    const inputs = req.body;
    if (!inputs) {
        return res.status(400).json({ msg: "Missing Request Body" });
    }
    const relevantArgs = ["sessionID"];
    if (isAnyArgUndefined(inputs, relevantArgs)) {
        return res.status(401).json({ msg: "Missing Field" });
    }
    // Validate Session
    const session = sessionsList[inputs.sessionID];
    if (!session) {
        return res.status(401).json({ msg: "Invalid Session" });
    }

    return res.status(200).json(await Team.find({ ownerName: session.username }));
});

app.post("/getAllTeams", async (req, res) => {
    const inputs = req.body;
    if (!inputs) {
        return res.status(400).json({ msg: "Missing Request Body" });
    }

    let teamsList = await Team.find({});
    const outList = [];
    for (const team of teamsList) {
        const idList = [team.pokemon1ID, team.pokemon2ID, team.pokemon3ID, team.pokemon4ID, team.pokemon5ID];
        let out = {
            "ownerName": team.ownerName,
            "teamName": team.teamName,
        };
        let index = 1;
        for (const id of idList) {
            if (id == "null") {
                out[`pok${index}Entry`] = -1;
            } else {
                out[`pok${index}Entry`] = (await Pokemon.findOne({ pokemonID: id })).pokedexEntry;
            }
            index++;
        }
        outList.push(out);
    }

    return res.status(200).json(outList);
});

app.post("/createTeam", async (req, res) => {
    const inputs = req.body;
    if (!inputs) {
        return res.status(400).json({ msg: "Missing Request Body" });
    }
    const relevantArgs = ["sessionID", "teamName", "pokemon1ID", "pokemon2ID", "pokemon3ID", "pokemon4ID", "pokemon5ID"];
    if (isAnyArgUndefined(inputs, relevantArgs)) {
        return res.status(401).json({ msg: "Missing Field" });
    }
    // Validate Session
    const session = sessionsList[inputs.sessionID];
    if (!session) {
        return res.status(401).json({ msg: "Invalid Session" });
    }

    await Team.insertMany({
        ownerName: session.username,
        teamName: inputs.teamName,
        pokemon1ID: inputs.pokemon1ID,
        pokemon2ID: inputs.pokemon2ID,
        pokemon3ID: inputs.pokemon3ID,
        pokemon4ID: inputs.pokemon4ID,
        pokemon5ID: inputs.pokemon5ID
    });

    return res.status(200).json({ msg: "Success" });
});

app.listen(PORT, async () => {
    await connectToDB();
    console.log(`Listening at http://localhost:${process.env.PORT}`);
});