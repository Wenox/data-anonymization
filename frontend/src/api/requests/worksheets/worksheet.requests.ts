import axios from 'axios';
import { CreateWorksheet, WorksheetCreated, WorksheetSummary } from './worksheet.types';

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

export const getMyWorksheetSummary = (id: string) => {
  return axios.get<WorksheetSummary>(`/api/v1/worksheets/me/${id}/summary`);
};
