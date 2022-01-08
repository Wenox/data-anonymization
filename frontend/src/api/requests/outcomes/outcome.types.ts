export interface GenerateOutcomeRequest {
  worksheetId: string;
  anonymisationScriptName: string;
  dumpMode: DumpMode;
}

export enum DumpMode {
  SCRIPT_FILE = 'SCRIPT_FILE',
  COMPRESSED_ARCHIVE = 'COMPRESSED_ARCHIVE',
}
