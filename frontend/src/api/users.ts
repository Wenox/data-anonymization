import axios, {AxiosResponse} from "axios";

export interface User {
  id: string;
  email: string;
  blocked: boolean;
  role: String;
}

export const getUsers = (): Promise<AxiosResponse<User[]>> => {
  return axios.get(`/api/v1/users`);
}
