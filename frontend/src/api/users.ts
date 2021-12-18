import axios, {AxiosResponse} from "axios";

export interface User {
  email: string;
  blocked: boolean;
  role: String;
}

export const getUsers = (): Promise<AxiosResponse<User[]>> => {
  return axios.get(`/api/v1/users`);
}
