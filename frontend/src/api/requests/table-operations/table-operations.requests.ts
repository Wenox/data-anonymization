import axios from 'axios';
import { TableOperations } from './table-operations.types';

export const getTableOperations = (table: string, worksheetId: string) => {
  return axios.get<TableOperations>(`/api/v1/worksheet/${worksheetId}/table-operations/${table}`);
};
