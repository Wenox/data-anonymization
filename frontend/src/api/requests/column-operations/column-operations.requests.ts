import axios from 'axios';
import { AddColumnShuffle, AddRowShuffle, AddSuppression } from './column-operations.types';
import { ApiResponse } from '../shared.types';

export const putAddSuppressionOperation = (worksheetId: string, dto: AddSuppression) => {
  return axios.put<ApiResponse>(`/api/v1/worksheet/${worksheetId}/column-operations/add-suppression`, dto);
};

export const putAddColumnShuffleOperation = (worksheetId: string, dto: AddColumnShuffle) => {
  return axios.put<ApiResponse>(`/api/v1/worksheet/${worksheetId}/column-operations/add-column-shuffle`, dto);
};

export const putAddRowShuffleOperation = (worksheetId: string, dto: AddRowShuffle) => {
  return axios.put<ApiResponse>(`/api/v1/worksheet/${worksheetId}/column-operations/add-row-shuffle`, dto);
};
