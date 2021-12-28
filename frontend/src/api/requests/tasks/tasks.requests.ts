import axios from 'axios';
import { Task } from './tasks.types';
import { ApiResponse } from '../shared.types';

export const getTasks = () => {
  return axios.get<Task[]>(`/api/v1/tasks`);
};

export const postExecuteTask = (task: string) => {
  return axios.post<ApiResponse>(`/api/v1/tasks/${task}/execute`);
};
