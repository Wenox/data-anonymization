import { GridAlignment } from '@mui/x-data-grid';

export interface CenteredColumnInterface {
  headerClassName: string;
  headerAlign?: GridAlignment;
  align?: GridAlignment;
}

export function centeredColumn(): CenteredColumnInterface {
  return {
    headerClassName: 'data-grid-header',
    headerAlign: 'center',
    align: 'center',
  };
}

export function centeredHeader(): CenteredColumnInterface {
  return {
    headerClassName: 'data-grid-header',
    headerAlign: 'center',
  };
}
