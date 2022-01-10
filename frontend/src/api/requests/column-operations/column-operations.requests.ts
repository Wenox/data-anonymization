import axios from 'axios';
import {
  AddColumnShuffle,
  AddGeneralisationRequest,
  AddHashingRequest,
  AddPatternMasking,
  AddPerturbationRequest,
  AddRandomNumberRequest,
  AddRowShuffle,
  AddShorteningRequest,
  AddSuppression,
  AddTokenizationRequest,
} from './column-operations.types';
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

export const putAddPatternMaskingOperation = (worksheetId: string, dto: AddPatternMasking) => {
  return axios.put<ApiResponse>(`/api/v1/worksheet/${worksheetId}/column-operations/add-pattern-masking`, dto);
};

export const putAddShorteningOperation = (worksheetId: string, dto: AddShorteningRequest) => {
  return axios.put<ApiResponse>(`/api/v1/worksheet/${worksheetId}/column-operations/add-shortening`, dto);
};

export const putAddGeneralisationOperation = (worksheetId: string, dto: AddGeneralisationRequest) => {
  return axios.put<ApiResponse>(`/api/v1/worksheet/${worksheetId}/column-operations/add-generalisation`, dto);
};

export const putAddPerturbationOperation = (worksheetId: string, dto: AddPerturbationRequest) => {
  return axios.put<ApiResponse>(`/api/v1/worksheet/${worksheetId}/column-operations/add-perturbation`, dto);
};

export const putAddRandomNumberOperation = (worksheetId: string, dto: AddRandomNumberRequest) => {
  return axios.put<ApiResponse>(`/api/v1/worksheet/${worksheetId}/column-operations/add-random-number`, dto);
};

export const putAddHashingOperation = (worksheetId: string, dto: AddHashingRequest) => {
  return axios.put<ApiResponse>(`/api/v1/worksheet/${worksheetId}/column-operations/add-hashing`, dto);
};

export const putAddTokenizationOperation = (worksheetId: string, dto: AddTokenizationRequest) => {
  return axios.put<ApiResponse>(`/api/v1/worksheet/${worksheetId}/column-operations/add-tokenization`, dto);
};
