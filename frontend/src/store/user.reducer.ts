import {UserAction} from "./user.actions";
import {IMe} from "../api/auth";

export const userReducer = (state: IMe | null = null, action: UserAction) => {
    switch (action.type) {
        case 'SET_USER':
            return action.payload;
        default:
            return state;
    }
};