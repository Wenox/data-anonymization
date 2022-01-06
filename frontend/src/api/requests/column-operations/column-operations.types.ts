export interface AddOperation {
  tableName: string;
  columnName: string;
  primaryKeyColumnName: string;
}

export interface AddSuppression extends AddOperation {
  suppressionToken: string;
}
