import { useQuery } from 'react-query';
import { DataGrid, GridColDef } from '@mui/x-data-grid';
import { centeredColumn, centeredHeader } from '../../styles/data-table';
import { Container, Divider, IconButton } from '@mui/material';
import { theme } from '../../styles/theme';
import Typography from '@mui/material/Typography';
import { getMyOutcomes } from '../../api/requests/outcomes/outcome.requests';
import { OutcomeResponse } from '../../api/requests/outcomes/outcome.types';
import { handleDownloadOutcomeDump, handleDownloadTemplateDump } from '../../utils/download-dump';
import { CloudDownload } from '@mui/icons-material';

const MyOutcomes = () => {
  const { data, isLoading, refetch, isRefetching } = useQuery('outcomes', getMyOutcomes);
  const outcomes: OutcomeResponse[] = data?.data || [];

  const columns: GridColDef[] = [
    { field: 'templateName', headerName: 'Template name', flex: 1, ...centeredHeader() },
    {
      field: 'dumpFile',
      headerName: 'Dump file',
      flex: 1,
      renderCell: ({ row }) => (
        <>
          <IconButton onClick={() => handleDownloadOutcomeDump(row.id, row.dumpName)}>
            <CloudDownload sx={{ fontSize: '200%', color: '#7f00b5' }} />
          </IconButton>
          <h3>{row.dumpName}</h3>
        </>
      ),
    },
    { field: 'outcomeStatus', headerName: 'Status', flex: 1, ...centeredHeader() },
    { field: 'dumpMode', headerName: 'Dump mode', flex: 1, ...centeredHeader() },
    { field: 'dumpName', headerName: 'Dump name', flex: 1, ...centeredHeader() },
    { field: 'anonymisationScriptName', headerName: 'Anonymisation script name', flex: 1, ...centeredHeader() },
    { field: 'processingTime', headerName: 'Processing time', flex: 1, ...centeredColumn() },
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
        Outcomes
      </Typography>
      <Divider sx={{ mb: 3 }} />
      <DataGrid autoHeight columns={columns} rows={outcomes} loading={isLoading || isRefetching} />
    </Container>
  );
};

export default MyOutcomes;
