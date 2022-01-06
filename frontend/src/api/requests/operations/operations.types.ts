import { Column } from '../templates/templates.types';

export interface ColumnOperationsForTable {
  tableName: string;
  primaryKeyColumnName: string;
  numberOfRows: number;
  columnOperations: ColumnOperations[];
}

export interface ColumnOperations {
  column: Column;
  operations: Operation[];
}

export interface Operation {
  id: string;
  operationName: string;
  tableName: string;
  columnName: string;
}

export interface AddOperation {
  tableName: string;
  columnName: string;
  primaryKeyColumnName: string;
}

export interface AddSuppressionOperation extends AddOperation {
  suppressionToken: string;
}
