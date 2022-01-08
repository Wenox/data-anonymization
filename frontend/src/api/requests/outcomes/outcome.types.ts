export interface GenerateOutcomeRequest {
  worksheetId: string;
  anonymisationScriptName: string;
  dumpName: string;
  dumpMode: DumpMode;
}

export enum DumpMode {
  SCRIPT_FILE = 'SCRIPT_FILE',
  COMPRESSED_ARCHIVE = 'COMPRESSED_ARCHIVE',
}

export interface OutcomeResponse {
  id: string;
  worksheetId: string;
  outcomeStatus: string;
  dumpMode: DumpMode;
  dumpName: string;
  anonymisationScriptName: string;
  processingTime: number;
}
