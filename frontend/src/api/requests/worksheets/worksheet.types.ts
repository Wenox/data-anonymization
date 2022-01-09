import { MyTemplate } from '../templates/templates.types';
import { DumpMode } from '../outcomes/outcome.types';

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
  outcomes: OutcomeSummary[];
}

export interface OutcomeSummary {
  id: string;
  outcomeStatus: string;
  dumpMode: DumpMode;
  dumpName: string;
  anonymisationScriptName: string;
  processingTime: number;
}

export type Worksheet = WorksheetSummaryResponse;
