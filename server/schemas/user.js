import mongoose from 'mongoose';

const userSchema = new mongoose.Schema({
    email: String,
    name: String,
    password: String,
    rank: Number,
    befriended: Number,
    hoursSlept: Number,
    birthday: BigInt
});

export const User = mongoose.model('User', userSchema);