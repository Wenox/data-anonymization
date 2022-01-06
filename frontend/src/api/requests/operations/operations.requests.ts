import axios from 'axios';
import { AddOperation, AddSuppressionOperation, ColumnOperationsForTable } from './operations.types';
import { ApiResponse } from '../shared.types';

export const getOperationsForTableInWorksheet = (table: string, worksheetId: string) => {
  return axios.get<ColumnOperationsForTable>(`/api/v1/worksheet/${worksheetId}/operations?table=${table}`);
};

export const putAddSuppressionOperationForColumn = (worksheetId: string, dto: AddSuppressionOperation) => {
  return axios.put<ApiResponse>(`/api/v1/worksheet/${worksheetId}/operations/add-suppression`, dto);
};
