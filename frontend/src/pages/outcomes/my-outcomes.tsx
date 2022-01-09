import { useQuery } from 'react-query';
import { DataGrid, GridColDef } from '@mui/x-data-grid';
import { centeredColumn, centeredHeader } from '../../styles/data-table';
import { Container, Divider, IconButton } from '@mui/material';
import { theme } from '../../styles/theme';
import Typography from '@mui/material/Typography';
import { getMyOutcomes } from '../../api/requests/outcomes/outcome.requests';
import { OutcomeResponse } from '../../api/requests/outcomes/outcome.types';
import { handleDownloadAnonymisationScript, handleDownloadOutcomeDump } from '../../utils/download-dump';
import { CloudDownload } from '@mui/icons-material';

const MyOutcomes = () => {
  const { data, isLoading, refetch, isRefetching } = useQuery('outcomes', getMyOutcomes);
  const outcomes: OutcomeResponse[] = data?.data || [];

  const isDownloadDisabled = (outcomeStatus: string) =>
    [
      'DATABASE_DUMP_FAILURE',
      'SCRIPT_EXECUTION_FAILURE',
      'SCRIPT_POPULATION_FAILURE',
      'SCRIPT_POPULATION_FAILURE',
      'MIRROR_FAILURE',
    ].includes(outcomeStatus);

  const columns: GridColDef[] = [
    { field: 'templateName', headerName: 'Template name', flex: 1, ...centeredHeader() },
    {
      field: 'scriptFile',
      headerName: 'Anonymisation script',
      flex: 1,
      ...centeredHeader(),
      renderCell: ({ row }) => {
        const downloadDisabled = isDownloadDisabled(row.outcomeStatus);
        return (
          <>
            <IconButton
              disabled={downloadDisabled}
              onClick={() => handleDownloadAnonymisationScript(row.id, row.anonymisationScriptName)}
            >
              <CloudDownload sx={{ fontSize: '240%', color: downloadDisabled ? '#ccc' : '#7f00b5' }} />
            </IconButton>
            <h2 style={{ color: downloadDisabled ? '#ccc' : `${theme.palette.primary.main}` }}>
              &nbsp;{row.anonymisationScriptName}
            </h2>
          </>
        );
      },
    },
    {
      field: 'dumpFile',
      headerName: 'Dump file',
      flex: 1,
      ...centeredHeader(),
      renderCell: ({ row }) => {
        const downloadDisabled = isDownloadDisabled(row.outcomeStatus);
        return (
          <>
            <IconButton disabled={downloadDisabled} onClick={() => handleDownloadOutcomeDump(row.id, row.dumpName)}>
              <CloudDownload sx={{ fontSize: '240%', color: downloadDisabled ? '#ccc' : '#7f00b5' }} />
            </IconButton>
            <h2 style={{ color: downloadDisabled ? '#ccc' : `${theme.palette.primary.main}` }}>&nbsp;{row.dumpName}</h2>
          </>
        );
      },
    },
    { field: 'outcomeStatus', headerName: 'Status', flex: 1, ...centeredHeader() },
    { field: 'dumpMode', headerName: 'Dump mode', flex: 1, ...centeredHeader() },
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
