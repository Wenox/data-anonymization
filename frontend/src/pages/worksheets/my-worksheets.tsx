import { FC } from 'react';
import { Button, Container, Divider, IconButton } from '@mui/material';
import { theme } from '../../styles/theme';
import Typography from '@mui/material/Typography';
import { DataGrid, GridColDef } from '@mui/x-data-grid';
import { useQuery } from 'react-query';
import { getMyWorksheets } from '../../api/requests/worksheets/worksheet.requests';
import { Worksheet } from '../../api/requests/worksheets/worksheet.types';
import { centeredColumn } from '../../styles/data-table';
import { useNavigate } from 'react-router-dom';
import { ROUTES } from '../../constants/routes';
import { Delete, Edit } from '@mui/icons-material';

const MyWorksheets: FC = () => {
  const { data, isLoading, refetch, isRefetching } = useQuery('worksheets', getMyWorksheets);
  const worksheets: Worksheet[] = data?.data || [];

  const navigate = useNavigate();

  const columns: GridColDef[] = [
    {
      field: 'worksheetSummary',
      headerName: 'Summary',
      width: 180,
      sortable: false,
      filterable: false,
      ...centeredColumn(),
      renderCell: ({ row }) => (
        <Button
          size="medium"
          color="primary"
          variant="contained"
          fullWidth
          onClick={() => navigate(`${ROUTES.WORKSHEET_SUMMARY}?worksheet_id=${row.id}`)}
        >
          View summary
        </Button>
      ),
    },
    {
      field: 'title',
      headerName: 'Template',
      flex: 1,
      ...centeredColumn(),
      renderCell: (params) => params.row.template.title,
    },
    {
      field: 'originalFileName',
      headerName: 'Dump file',
      flex: 1,
      ...centeredColumn(),
      renderCell: (params) => params.row.template.originalFileName,
    },
    {
      field: 'type',
      headerName: 'Type',
      flex: 1,
      ...centeredColumn(),
      renderCell: (params) => params.row.template.type,
    },
    {
      field: 'numberOfTables',
      headerName: 'Number of tables',
      flex: 1,
      ...centeredColumn(),
      renderCell: (params) => params.row.template.numberOfTables,
    },
    {
      field: 'actions',
      headerName: 'Actions',
      width: 100,
      sortable: false,
      filterable: false,
      ...centeredColumn(),
      headerAlign: 'center',
      renderCell: ({ row }) => {
        return (
          <div>
            <IconButton onClick={() => {}}>
              <Delete fontSize="large" sx={{ color: '#e00000' }} />
            </IconButton>
          </div>
        );
      },
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
      <Typography color="secondary" variant="h4" sx={{ mb: 2 }}>
        Worksheets
      </Typography>
      <Divider sx={{ mb: 3 }} />
      <DataGrid autoHeight columns={columns} rows={worksheets} loading={isLoading || isRefetching} />
    </Container>
  );
};

export default MyWorksheets;
