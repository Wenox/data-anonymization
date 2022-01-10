export interface AddOperation {
  tableName: string;
  columnName: string;
  columnType: string;
  primaryKeyColumnName: string;
  primaryKeyColumnType: string;
}

export interface AddSuppression extends AddOperation {
  suppressionToken: string;
}

export interface AddColumnShuffle extends AddOperation {
  withRepetitions: boolean;
}

export interface AddRowShuffle extends AddColumnShuffle {
  letterMode: LetterMode;
}

export enum LetterMode {
  RETAIN_CASE = 'RETAIN_CASE',
  TO_LOWERCASE = 'TO_LOWERCASE',
  TO_UPPERCASE = 'TO_UPPERCASE',
}

export interface AddPatternMasking extends AddOperation {
  pattern: string;
  maskingCharacter: string;
  discardExcessiveCharacters: boolean;
}

export interface AddShorteningRequest extends AddOperation {
  length: number;
  endsWithPeriod: boolean;
}

export interface AddGeneralisationRequest extends AddOperation {
  minValue: number | null;
  maxValue: number | null;
  intervalSize: number | null;
  numberOfDistributions: number | null;
  generalisationMode: GeneralisationMode;
}

export enum GeneralisationMode {
  VALUE = 'VALUE',
  DISTRIBUTION = 'DISTRIBUTION',
}
