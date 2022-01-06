export interface AddOperation {
  tableName: string;
  columnName: string;
  primaryKeyColumnName: string;
}

export interface AddSuppression extends AddOperation {
  suppressionToken: string;
}

export interface AddShuffle extends AddOperation {
  withRepetitions: boolean;
}
