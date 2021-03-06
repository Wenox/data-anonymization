import { FC, useEffect, useState } from 'react';
import { DataGrid, GridColDef } from '@mui/x-data-grid';
import { Button, Container, Divider, Tooltip } from '@mui/material';
import { theme } from '../../styles/theme';
import Typography from '@mui/material/Typography';
import { centeredColumn, centeredHeader } from '../../styles/data-table';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { toast } from 'react-toastify';
import { ROUTES } from '../../constants/routes';
import { Add, Edit, Key } from '@mui/icons-material';
import AddOperationDialog from '../../components/operation/add-operation-dialog';
import { ColumnOperations, OperationDto } from '../../api/requests/table-operations/table-operations.types';
import { getTableOperations } from '../../api/requests/table-operations/table-operations.requests';
import { getColorForOperation } from '../../utils/anonymisation-colors';
import { Operation } from '../../constants/anonymisation-validation-matrix';

const TableOperations: FC = () => {
  const [tableName, setTableName] = useState<string>('');
  const [primaryKeyColumnName, setPrimaryKeyColumnName] = useState<string>('');
  const [primaryKeyColumnType, setPrimaryKeyColumnType] = useState<string>('');
  const [numberOfRows, setNumberOfRows] = useState<number>(0);
  const [operations, setOperations] = useState<ColumnOperations[]>([]);
  const [isAddOperation, setIsAddOperation] = useState(false);
  const [selectedRow, setSelectedRow] = useState<ColumnOperations | null>(null);

  const [searchParams] = useSearchParams();
  const worksheetId: string = searchParams.get('worksheet_id') ?? '';
  const table: string = searchParams.get('table') ?? '';

  const navigate = useNavigate();

  const refetch = () => {
    getTableOperations(table, worksheetId).then((response) => {
      if (response.status === 200) {
        setTableName(response.data.tableName);
        setPrimaryKeyColumnName(response.data.primaryKeyColumnName);
        setPrimaryKeyColumnType(response.data.primaryKeyColumnType);
        setNumberOfRows(response.data.numberOfRows);
        setOperations(
          response.data.listOfColumnOperations.map((operation) => ({
            ...operation,
            id: operation.column.columnName,
          })),
        );
      }
    });
  };

  useEffect(() => {
    getTableOperations(table, worksheetId)
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
          setPrimaryKeyColumnName(response.data.primaryKeyColumnName);
          setPrimaryKeyColumnType(response.data.primaryKeyColumnType);
          setNumberOfRows(response.data.numberOfRows);
          setOperations(
            response.data.listOfColumnOperations.map((operation) => ({
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
      field: 'keyInfo',
      headerName: 'Key info',
      width: 120,
      ...centeredColumn(),
      sortable: false,
      filterable: false,
      renderCell: ({ row }) => {
        const isPrimaryKey = row.column.primaryKey;
        const isForeignKey = row.column.foreignKey;
        return (
          <>
            {isPrimaryKey && (
              <>
                <Key sx={{ fontSize: '200%' }} color="error" /> &nbsp;Primary key
              </>
            )}
            {isForeignKey && (
              <>
                <Key sx={{ fontSize: '200%' }} color="error" /> &nbsp;Foreign key
              </>
            )}
          </>
        );
      },
    },
    {
      field: 'actions',
      headerName: 'Actions',
      width: 200,
      ...centeredColumn(),
      sortable: false,
      filterable: false,
      renderCell: ({ row }) => {
        const usesSuppression = row.listOfColumnOperation
          .map((v: OperationDto) => v.operationName)
          .includes('Suppression');
        const isPrimaryKey = row.column.primaryKey;
        const isForeignKey = row.column.foreignKey;
        const isUnsupportedType = row.column.type !== '12' && row.column.type !== '4';
        return (
          <Button
            disabled={
              isUnsupportedType ||
              isPrimaryKey ||
              isForeignKey ||
              usesSuppression ||
              row.listOfColumnOperation.length >= 2
            }
            size="large"
            color="success"
            variant="contained"
            fullWidth
            onClick={() => {
              setIsAddOperation(true);
              setSelectedRow(row);
            }}
          >
            <Add sx={{ fontSize: '200%', mr: 1 }} />
            Add operation
          </Button>
        );
      },
    },
    {
      field: 'accumulatedOperations',
      headerName: 'Accumulated operations',
      width: 400,
      ...centeredHeader(),
      sortable: false,
      filterable: false,
      renderCell: ({ row }) => {
        return (
          <>
            {row.listOfColumnOperation.map(({ operationName }: OperationDto) => (
              <Button
                key={operationName}
                size="large"
                variant="contained"
                sx={{
                  width: '200px',
                  ml: 0.5,
                  mr: 0.5,
                  backgroundColor: getColorForOperation(operationName as Operation),
                }}
                onClick={() => {}}
              >
                <Edit sx={{ fontSize: '200%', mr: 1 }} />
                {operationName}
              </Button>
            ))}
          </>
        );
      },
    },
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
      {isAddOperation && (
        <AddOperationDialog
          open={isAddOperation}
          columnOperations={selectedRow!}
          handleCancel={() => setIsAddOperation(false)}
          handleAddSuccess={() => {
            setIsAddOperation(false);
            refetch();
          }}
          worksheetId={worksheetId}
          tableName={tableName}
          primaryKeyColumnName={primaryKeyColumnName}
          primaryKeyColumnType={primaryKeyColumnType}
        />
      )}
      <Typography color="primary" variant="h4" sx={{ mb: 2 }}>
        Operations for table{' '}
        <span style={{ color: `${theme.palette.secondary.main}` }}>
          <strong>{tableName}</strong>
        </span>
      </Typography>
      <Divider sx={{ mb: 3 }} />
      <Button
        color="secondary"
        variant="contained"
        onClick={() => navigate(`${ROUTES.WORKSHEET_SUMMARY}?worksheet_id=${worksheetId}`)}
      >
        Return to summary
      </Button>
      <p>
        <strong>Rows count in {tableName}:</strong> {numberOfRows}
        <br />
        <strong>Primary key column:</strong> {primaryKeyColumnName}
      </p>
      <DataGrid autoHeight columns={columns} rows={operations} loading={false} />
    </Container>
  );
};

export default TableOperations;
