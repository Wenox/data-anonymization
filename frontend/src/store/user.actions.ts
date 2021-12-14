import {IMe} from "../api/auth";

type SetUser = {
    type: 'SET_USER';
    payload: IMe | null;
};

export type UserAction = SetUser;