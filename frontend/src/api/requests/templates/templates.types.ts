import { TemplateStatus } from '../../../components/template/template-generation-base.types';
import { FileType } from '../shared.types';

export interface MyTemplate {
  id: string;
  originalFileName: string;
  status: TemplateStatus;
  type: FileType;
  title: string;
  description: string;
  createdDate: string;
  metadata: TemplateMetadata;
  restoreMode: RestoreMode;
}

export enum RestoreMode {
  ARCHIVE = 'ARCHIVE',
  SCRIPT = 'SCRIPT',
}

export interface TemplateMetadata {
  numberOfTables: number;
  tables: Record<string, Table>;
}

export interface Table {
  tableName: string;
  numberOfRows: number;
  numberOfColumns: number;
  columns: Record<string, Column>;
  primaryKey: PrimaryKey;
}

export interface Column {
  columnName: string;
  type: string;
  nullable: boolean;
  primaryKey: boolean;
  foreignKey: boolean;
}

export interface PrimaryKey {
  columnName: string;
  type: string;
  nullable: boolean;
  primaryKeyName: string;
}
