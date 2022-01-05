import axios from 'axios';
import { AddOperation, ColumnOperationsForTable } from './operations.types';
import { ApiResponse } from '../shared.types';

export const getOperationsForTableInWorksheet = (table: string, worksheetId: string) => {
  return axios.get<ColumnOperationsForTable>(`/api/v1/worksheet/${worksheetId}/operations?table=${table}`);
};

export const putAddOperationForColumn = (worksheetId: string, dto: AddOperation) => {
  return axios.put<ApiResponse>(`/api/v1/worksheet/${worksheetId}/operations`, dto);
};
