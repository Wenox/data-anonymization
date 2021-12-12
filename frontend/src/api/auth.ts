import axios, {AxiosRequestConfig, AxiosRequestHeaders} from "axios";
import {ACCESS_TOKEN, BEARER_PREFIX} from "../constants/token";

export interface UserRequest {
    email: string;
    password: string;
}

export const getHeadersConfig = (): AxiosRequestConfig => ({ headers: accessTokenHeader() })

export const accessTokenHeader = (): AxiosRequestHeaders => ({ 'Authorization': BEARER_PREFIX + localStorage.getItem(ACCESS_TOKEN) })

export const registerUser = (body: UserRequest) => {
    return axios.post('/api/v1/auth/register', body);
}

export const login = (body: UserRequest) => {
    return axios.post('/api/v1/auth/login', body);
}

export const testUser = () => {
    return axios.post('/api/v1/some/user');
}

export const testAdmin = () => {
    return axios.post('/api/v1/some/admin', {}, getHeadersConfig());
}

export const testNone = () => {
    return axios.post('/api/v1/some/none');
}
