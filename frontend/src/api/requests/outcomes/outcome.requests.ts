import axios from 'axios';
import { GenerateOutcomeRequest } from './outcome.types';

export const postGenerateOutcome = (dto: GenerateOutcomeRequest) => {
  return axios.post<string>(`/api/v1/outcomes/generate`, dto);
};
