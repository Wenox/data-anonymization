import axios from 'axios';
import { ColumnOperationsForTable } from './operations.types';

export const getOperationsForTableInWorksheet = (table: string, worksheetId: string) => {
  return axios.get<ColumnOperationsForTable>(`/api/v1/worksheet/${worksheetId}/operations?table=${table}`);
};
