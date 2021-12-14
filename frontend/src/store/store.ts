import { combineReducers, createStore } from 'redux';
import {userReducer} from "./user.reducer";
import {IMe} from "../api/auth";

export const store = createStore(
    combineReducers({
        user: userReducer
    })
);

export interface IStore {
    user: IMe | null;
}