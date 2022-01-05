import { FC, useEffect, useState } from 'react';
import { DataGrid, GridColDef } from '@mui/x-data-grid';
import { Container, Divider } from '@mui/material';
import { theme } from '../../styles/theme';
import Typography from '@mui/material/Typography';
import { centeredColumn } from '../../styles/data-table';
import { ColumnOperations } from '../../api/requests/operations/operations.types';
import { getOperationsForTableInWorksheet } from '../../api/requests/operations/operations.requests';
import { useSearchParams } from 'react-router-dom';
import { toast } from 'react-toastify';

const OperationsInTable: FC = () => {
  const [tableName, setTableName] = useState<string>('');
  const [numberOfRows, setNumberOfRows] = useState<number>(0);
  const [operations, setOperations] = useState<ColumnOperations[]>([]);

  const [searchParams] = useSearchParams();
  const worksheetId: string = searchParams.get('worksheet_id') ?? '';
  const table: string = searchParams.get('table') ?? '';

  useEffect(() => {
    getOperationsForTableInWorksheet(table, worksheetId)
      .then((response) => {
        if (response.status === 200) {
          toast.success('Operations in table loaded successfully.', {
            position: 'top-right',
            autoClose: 600,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
          setTableName(response.data.tableName);
          setNumberOfRows(response.data.numberOfRows);
          setOperations(
            response.data.columnOperations.map((operation) => ({
              ...operation,
              id: operation.column.columnName,
            })),
          );
        }
      })
      .catch(() =>
        toast.error('Failed to load the operations.', {
          position: 'top-right',
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        }),
      );
  }, [table, worksheetId]);

  const columns: GridColDef[] = [
    {
      field: 'columnName',
      headerName: 'Column name',
      flex: 1,
      ...centeredColumn(),
      renderCell: ({ row }) => row.column.columnName,
    },
    {
      field: 'type',
      headerName: 'Type',
      flex: 1,
      ...centeredColumn(),
      renderCell: ({ row }) => row.column.type,
    },
    {
      field: 'nullable',
      headerName: 'Nullable',
      flex: 1,
      ...centeredColumn(),
      renderCell: ({ row }) => row.column.nullable.toString(),
    },
    {
      field: 'primaryKey',
      headerName: 'Primary key',
      flex: 1,
      ...centeredColumn(),
      renderCell: () => 'Todo',
    },
  ];

  return (
    <Container
      maxWidth={false}
      component="main"
      sx={{
        backgroundColor: '#fff',
        border: `1px solid ${theme.palette.primary.main}`,
        boxShadow: `4px 4px 0px ${theme.palette.primary.dark}`,
        borderRadius: '2px',
        pt: 2,
        pb: 3,
      }}
    >
      <Typography color="primary" variant="h4" sx={{ mb: 2 }}>
        Operations for table{' '}
        <span style={{ color: `${theme.palette.secondary.main}` }}>
          <strong>{tableName}</strong>
        </span>
      </Typography>
      <Divider sx={{ mb: 3 }} />
      <p>
        <strong>Rows count in {tableName}:</strong> {numberOfRows}
      </p>
      <DataGrid autoHeight columns={columns} rows={operations} loading={false} />
    </Container>
  );
};

export default OperationsInTable;
