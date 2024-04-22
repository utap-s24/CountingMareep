import 'dotenv/config';
import express from "express";
import mongoose from 'mongoose';
import bcrypt from 'bcrypt';
import * as EmailValidator from 'email-validator';
import { v4 as uuidv4 } from 'uuid';
import { User } from "./schemas/user.js";
import { Session } from './session.js';

const app = express();
const PORT = process.env.PORT || 3030;
const SALT_ROUNDS = 10;

const sessionsList = {};

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

app.get("/tests", (req, res) => {
    return res.json({ msg: "Success" });
});

app.get("/getAllUsers", async (req, res) => {
    let users = await User.find({});
    users = users.map(user => {
        return { name: user.name, rank: user.rank, befriended: user.befriended, hoursSlept: user.hoursSlept, birthday: user.birthday };
    });
    res.json(users);
});

/*
    name: String,
    password: String,
    rank: Number,
    befriended: Number,
    hoursSlept: Number,
    birthday: Number
 */
app.post("/signUp", async (req, res) => {
    const inputs = req.body;
    if (!inputs) {
        return res.status(403).json({ msg: "Missing Request Body" });
    }
    if (inputs.name === undefined || inputs.password === undefined || inputs.rank === undefined || inputs.befriended === undefined || inputs.hoursSlept === undefined || inputs.birthday === undefined || inputs.email === undefined) {
        return res.status(403).json({ msg: "Missing Field" });
    }

    let nameAlreadyUsed = await User.findOne({ name: inputs.name });
    if (nameAlreadyUsed !== null) {
        return res.status(503).json({ msg: "Failed - Name Taken" });
    }
    if (!EmailValidator.validate(inputs.email)) {
        return res.status(503).json({ msg: "Invalid Email" });
    }

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

    return res.status(200).json({ msg: "Success" });
});

/*
    name: String,
    password: String
 */
app.post("/login", async (req, res) => {
    const inputs = req.body;
    if (!inputs) {
        return res.status(403).json({ msg: "Missing Request Body" });
    }
    if (inputs.name === undefined || inputs.password === undefined) {
        return res.status(403).json({ msg: "Missing Field" });
    }
    // Check if user is in DB
    const user = await User.findOne({ name: inputs.name });
    if (user == null) {
        return res.status(400).json({ msg: "No user with that name," });
    }

    if (bcrypt.compareSync(inputs.password, user.password)) {
        let sessionID = uuidv4();
        if (sessionsList[inputs.name]) {
            // Reuse Session
            sessionID = sessionsList[inputs.name].uuid;
        } else {

        }

        return res.status(200).json({ msg: "Success", sessionID: sessionID });
    } else {
        return res.status(400).json({ msg: "Incorrect Password" });
    }
});

app.listen(PORT, async () => {
    await connectToDB();
    console.log(`Listening at http://localhost:${process.env.PORT}`);
});