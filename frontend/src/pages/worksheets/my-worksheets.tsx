import { FC } from 'react';
import { Container, Divider } from '@mui/material';
import { theme } from '../../styles/theme';
import Typography from '@mui/material/Typography';
import { DataGrid, GridColDef } from '@mui/x-data-grid';
import { useQuery } from 'react-query';
import { getMyWorksheets } from '../../api/requests/worksheets/worksheet.requests';
import { Worksheet } from '../../api/requests/worksheets/worksheet.types';
import { centeredColumn } from '../../styles/data-table';

const MyWorksheets: FC = () => {
  const { data, isLoading, refetch, isRefetching } = useQuery('worksheets', getMyWorksheets);
  const worksheets: Worksheet[] = data?.data || [];

  const columns: GridColDef[] = [
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
