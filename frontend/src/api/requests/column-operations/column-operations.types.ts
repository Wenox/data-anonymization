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
