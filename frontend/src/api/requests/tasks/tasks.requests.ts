import axios from 'axios';
import { Task } from './tasks.types';

export const getTasks = () => {
  return axios.get<Task[]>(`/api/v1/tasks`);
};
