import axios from 'axios';
import { ColumnOperations } from './operations.types';

export const getOperationsForTable = (worksheetId: string, table: string) => {
  return axios.get<ColumnOperations[]>(`/api/v1/worksheet/${worksheetId}/operations?table=${table}`);
};
