import axios from "axios";

export interface UserRequest {
    email: string;
    password: string;
}

export const registerUser = (body: UserRequest) => {
    return axios.post('/api/v1/auth/register', body);
}

export const login = (body: UserRequest) => {
    return axios.post('/api/v1/auth/login', body);
}
