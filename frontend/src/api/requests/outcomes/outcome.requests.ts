import axios from 'axios';
import { GenerateOutcomeRequest, OutcomeResponse } from './outcome.types';

export const postGenerateOutcome = (dto: GenerateOutcomeRequest) => {
  return axios.post<string>(`/api/v1/outcomes/generate`, dto);
};

export const getMyOutcomes = () => {
  return axios.get<OutcomeResponse[]>('/api/v1/outcomes/me');
};
