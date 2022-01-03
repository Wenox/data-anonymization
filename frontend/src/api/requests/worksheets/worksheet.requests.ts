import axios from 'axios';
import { CreateWorksheet, WorksheetCreated } from './worksheet.types';

export const postCreateMyWorksheet = (dto: CreateWorksheet) => {
  return axios.post<WorksheetCreated>('/api/v1/worksheets/', dto, {
    headers: {
      'Content-Type': 'application/json',
    },
  });
};

export const getMyWorksheets = () => {
  return axios.get<any>(`/api/v1/worksheets/me`);
};
