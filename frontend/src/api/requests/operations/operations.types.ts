import { Column } from '../templates/templates.types';

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
