import { Column, TemplateMetadata } from '../templates/templates.types';

export interface TableOperations {
  tableName: string;
  primaryKeyColumnName: string;
  primaryKeyColumnType: string;
  numberOfRows: number;
  listOfColumnOperations: ColumnOperations[];
  metadata: TemplateMetadata;
}

export interface ColumnOperations {
  column: Column;
  listOfColumnOperation: OperationDto[];
}

export interface OperationDto {
  id: number;
  operationName: string;
  tableName: string;
  columnName: string;
}
