import axios from 'axios';
import { AddSuppression } from './column-operations.types';
import { ApiResponse } from '../shared.types';

export const putAddSuppressionOperation = (worksheetId: string, dto: AddSuppression) => {
  return axios.put<ApiResponse>(`/api/v1/worksheet/${worksheetId}/column-operations/add-suppression`, dto);
};
