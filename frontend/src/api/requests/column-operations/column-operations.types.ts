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

export interface AddShuffle extends AddOperation {
  withRepetitions: boolean;
}
