import { MyTemplate } from '../templates/templates.types';

export interface CreateWorksheet {
  templateId: string;
}

export interface WorksheetCreated {
  id: string;
  templateId: string;
  userId: string;
}

export interface WorksheetSummaryResponse {
  id: string;
  template: MyTemplate;
}

export type Worksheet = WorksheetSummaryResponse;
