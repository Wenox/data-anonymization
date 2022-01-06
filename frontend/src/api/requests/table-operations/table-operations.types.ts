import { Column } from '../templates/templates.types';

export interface TableOperations {
  tableName: string;
  primaryKeyColumnName: string;
  numberOfRows: number;
  listOfColumnOperations: ColumnOperations[];
}

export interface ColumnOperations {
  column: Column;
  listOfColumnOperation: Operation[];
}

export interface Operation {
  id: number;
  operationName: string;
  tableName: string;
  columnName: string;
}
