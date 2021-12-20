import axios from "axios";
import {ACCESS_TOKEN, JWT_EXPIRED_MSG, REFRESH_TOKEN} from "../constants/auth";
import {postRefreshToken} from "./requests/auth/auth.requests";

export const refresher = axios.create({});

axios.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem(ACCESS_TOKEN);
    if (token && config.headers) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

axios.interceptors.response.use(
  (res) => {
    return res;
  },
  (err) => {
    let originalConfig = err.config;

    if (err.response) {
      if (err.response.status === 403 && err.response.headers && err.response.headers['error'] && err.response.headers['error'].startsWith(JWT_EXPIRED_MSG) && !originalConfig._retry) {
        originalConfig._retry = true;

        postRefreshToken().then(response => {
          if (response.status === 200 && response.headers[ACCESS_TOKEN] && response.headers[REFRESH_TOKEN]) {
            localStorage.setItem(ACCESS_TOKEN, response.headers[ACCESS_TOKEN]);
            localStorage.setItem(REFRESH_TOKEN, response.headers[REFRESH_TOKEN]);

            originalConfig.headers['Authorization'] = `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`;

            return axios(originalConfig);
          }
        });
      }
    }
    return Promise.reject(err);
  }
);
