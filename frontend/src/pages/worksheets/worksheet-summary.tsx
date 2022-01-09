import React, { FC, useEffect, useState } from 'react';
import { theme } from '../../styles/theme';
import Typography from '@mui/material/Typography';
import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Button,
  CircularProgress,
  Container,
  Divider,
  Grid,
  IconButton,
} from '@mui/material';
import TextField from '@mui/material/TextField';
import MetadataDownloadButton from '../../components/metadata/metadata-download-button';
import {
  CloudDownload,
  DeveloperBoard,
  ExpandMore,
  ListAlt,
  SnippetFolder,
  Storage as StorageIcon,
} from '@mui/icons-material';
import { DataGrid, GridColDef } from '@mui/x-data-grid';
import { getMyWorksheetSummary } from '../../api/requests/worksheets/worksheet.requests';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { toast } from 'react-toastify';
import { WorksheetSummaryResponse } from '../../api/requests/worksheets/worksheet.types';
import MetadataDialog from '../../components/metadata/metadata-dialog';
import {
  handleDownloadAnonymisationScript,
  handleDownloadOutcomeDump,
  handleDownloadTemplateDump,
  isDownloadDisabled,
} from '../../utils/download-dump';
import { Table } from '../../api/requests/templates/templates.types';
import { centeredColumn, centeredHeader } from '../../styles/data-table';
import { ROUTES } from '../../constants/routes';
import GenerateOutcomeDialog from '../../components/outcome/generate-outcome-dialog';
import { SHOW_WORKSHEET_SUMMARY_TIMEOUT } from '../../constants/timeouts';

const WorksheetSummary: FC = () => {
  const [searchParams] = useSearchParams();
  const id: string = searchParams.get('worksheet_id') ?? '';

  const [isLoading, setIsLoading] = useState(true);
  const [summary, setSummary] = useState<WorksheetSummaryResponse>();
  const [isMetadataDialogOpen, setIsMetadataDialogOpen] = useState(false);
  const [tables, setTables] = useState<Table[]>([]);

  const [openedGenerationDialog, setOpenedGenerationDialog] = useState(false);

  const navigate = useNavigate();

  useEffect(() => {
    setTimeout(
      () =>
        getMyWorksheetSummary(id)
          .then((response) => {
            if (response.status === 200) {
              toast.success('Worksheet summary loaded.', {
                position: 'top-right',
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
              });
              setTables(
                Object.values(response.data.template.metadata.tables).map((value) => ({
                  ...value,
                  id: value.tableName,
                })),
              );
              setSummary(response.data);
            }
          })
          .catch((err) => {
            toast.error('Failed to load the worksheet summary.', {
              position: 'top-right',
              autoClose: 5000,
              hideProgressBar: false,
              closeOnClick: true,
              pauseOnHover: true,
              draggable: true,
              progress: undefined,
            });
          })
          .finally(() => setIsLoading(false)),
      SHOW_WORKSHEET_SUMMARY_TIMEOUT,
    );
  }, [id]);

  const tablesColumns: GridColDef[] = [
    {
      field: 'actions',
      headerName: 'Actions',
      width: 170,
      ...centeredHeader(),
      renderCell: ({ row }) => (
        <Button
          size="large"
          color="secondary"
          variant="contained"
          fullWidth
          onClick={() => navigate(`${ROUTES.OPERATIONS_IN_TABLE}?worksheet_id=${id}&table=${row.tableName}`)}
        >
          <DeveloperBoard sx={{ fontSize: '200%', mr: 1 }} />
          Anonymise
        </Button>
      ),
    },
    { field: 'tableName', headerName: 'Table name', flex: 1, ...centeredHeader() },
    { field: 'numberOfRows', headerName: 'Number of rows', flex: 1, ...centeredHeader() },
    { field: 'numberOfColumns', headerName: 'Number of columns', flex: 1, ...centeredHeader() },
  ];

  const outcomesColumns: GridColDef[] = [
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
    <>
      {isLoading ? (
        <Container
          maxWidth={'xl'}
          component="main"
          sx={{
            height: '600px',
            backgroundColor: '#fff',
            border: `1px solid ${theme.palette.primary.main}`,
            boxShadow: `4px 4px 0px ${theme.palette.primary.dark}`,
            pt: 2,
            pb: 3,
            borderRadius: '2px',
          }}
        >
          <Typography color="secondary" variant="h4" sx={{ mb: 2 }}>
            Worksheet summary
          </Typography>
          <Divider sx={{ mb: 3 }} />
          <CircularProgress
            size="12rem"
            color="secondary"
            sx={{ animationDuration: '0.65s' }}
            style={{
              position: 'absolute',
              left: '48%',
              top: '30%',
            }}
          />
        </Container>
      ) : (
        <Container
          maxWidth={'xl'}
          component="main"
          sx={{
            backgroundColor: '#fff',
            border: `1px solid ${theme.palette.primary.main}`,
            boxShadow: `4px 4px 0px ${theme.palette.primary.dark}`,
            pt: 2,
            pb: 3,
            borderRadius: '2px',
          }}
        >
          <Typography color="secondary" variant="h4" sx={{ mb: 2 }}>
            Worksheet summary
          </Typography>
          <Divider sx={{ mb: 3 }} />

          {isMetadataDialogOpen && (
            <MetadataDialog
              metadataWithFile={{
                metadata: summary?.template.metadata,
                originalFileName: summary?.template.originalFileName,
              }}
              open={isMetadataDialogOpen}
              handleClose={() => setIsMetadataDialogOpen(false)}
            />
          )}

          {/*Template*/}
          {/*Template*/}
          {/*Template*/}
          {/*Template*/}
          {/*Template*/}
          {/*Template*/}

          <Accordion
            expanded
            sx={{
              backgroundColor: '#f9f9f9',
              border: `1px dashed #c4c4c4`,
              boxShadow: 0,
              mt: 3,
              mb: 2,
            }}
          >
            <AccordionSummary expandIcon={<ExpandMore />} aria-controls="panel1a-content" id="panel1a-header">
              <div
                style={{
                  display: 'flex',
                  alignItems: 'center',
                  flexWrap: 'wrap',
                }}
              >
                <SnippetFolder sx={{ fontSize: '180%', mr: 2, color: `${theme.palette.secondary.main}` }} />
                <h1 style={{ lineHeight: '12px', color: `${theme.palette.primary.main}` }}>Template</h1>
              </div>
            </AccordionSummary>

            <AccordionDetails>
              <Grid container spacing={2} alignItems={'center'}>
                <Grid item xs={12}>
                  <Divider />
                </Grid>
                <Grid item xs={12}>
                  <h4 style={{ color: `${theme.palette.primary.light}`, marginTop: '-1px', marginBottom: '-2px' }}>
                    Description
                  </h4>
                </Grid>

                <Grid item xs={6}>
                  <TextField
                    label="Title"
                    value={summary?.template.title}
                    defaultValue={'Title'}
                    variant="outlined"
                    fullWidth
                    InputProps={{
                      readOnly: true,
                    }}
                    sx={{ backgroundColor: '#fff' }}
                  />
                </Grid>
                <Grid item xs={6}>
                  <TextField
                    label="Type"
                    value={summary?.template.type}
                    defaultValue={'Type'}
                    variant="outlined"
                    fullWidth
                    InputProps={{
                      readOnly: true,
                    }}
                    sx={{ backgroundColor: '#fff' }}
                  />
                </Grid>

                <Grid item xs={12}>
                  <TextField
                    label="Description"
                    value={summary?.template.description}
                    defaultValue={'No description'}
                    multiline
                    minRows={2}
                    maxRows={2}
                    variant="outlined"
                    fullWidth
                    InputProps={{
                      readOnly: true,
                    }}
                    sx={{ backgroundColor: '#fff' }}
                  />
                </Grid>

                <Grid item xs={12}>
                  <Divider />
                </Grid>
                <Grid item xs={12}>
                  <h4 style={{ color: `${theme.palette.primary.light}`, marginTop: '-1px', marginBottom: '-2px' }}>
                    Dump
                  </h4>
                </Grid>

                <Grid item xs={6}>
                  <TextField
                    label="Dump file"
                    defaultValue="music.dump"
                    variant="outlined"
                    fullWidth
                    InputProps={{
                      readOnly: true,
                    }}
                    sx={{ backgroundColor: '#fff' }}
                  />
                </Grid>
                <Grid item xs={6}>
                  <IconButton
                    onClick={() => handleDownloadTemplateDump(summary!.template.id, summary!.template.originalFileName)}
                  >
                    <CloudDownload sx={{ fontSize: '300%', color: '#7f00b5', mr: 1 }} />
                    Download
                  </IconButton>
                </Grid>

                <Grid item xs={12}>
                  <Divider />
                </Grid>
                <Grid item xs={12}>
                  <h4 style={{ color: `${theme.palette.primary.light}`, marginTop: '-1px', marginBottom: '-2px' }}>
                    Metadata
                  </h4>
                </Grid>

                <Grid item xs={6}>
                  <Button
                    fullWidth
                    color="secondary"
                    variant="contained"
                    onClick={() => {
                      setIsMetadataDialogOpen(true);
                    }}
                    sx={{ mr: 0.5 }}
                  >
                    View
                  </Button>
                </Grid>
                <Grid item xs={6}>
                  <MetadataDownloadButton
                    metadataWithFile={{
                      metadata: summary?.template.metadata,
                      originalFileName: summary?.template.originalFileName,
                    }}
                  />
                </Grid>
              </Grid>
            </AccordionDetails>
          </Accordion>

          {/*Tables*/}
          {/*Tables*/}
          {/*Tables*/}
          {/*Tables*/}
          {/*Tables*/}
          {/*Tables*/}
          {/*Tables*/}

          <Accordion
            expanded
            sx={{
              backgroundColor: '#f9f9f9',
              border: `1px dashed #c4c4c4`,
              boxShadow: 0,
              mb: 2,
            }}
          >
            <AccordionSummary expandIcon={<ExpandMore />} aria-controls="panel1a-content" id="panel1a-header">
              <div
                style={{
                  display: 'flex',
                  alignItems: 'center',
                  flexWrap: 'wrap',
                }}
              >
                <ListAlt sx={{ fontSize: '180%', mr: 2, color: `${theme.palette.secondary.main}` }} />
                <h1 style={{ lineHeight: '12px', color: `${theme.palette.primary.main}` }}>Tables</h1>
              </div>
            </AccordionSummary>

            <AccordionDetails>
              <Grid container spacing={2}>
                <DataGrid loading={isLoading} autoHeight columns={tablesColumns} rows={tables} />
              </Grid>
            </AccordionDetails>
          </Accordion>

          {/*Operations*/}
          {/*Operations*/}
          {/*Operations*/}
          {/*Operations*/}
          {/*Operations*/}
          {/*Operations*/}
          {/*Operations*/}

          <Accordion
            expanded
            sx={{
              backgroundColor: '#f9f9f9',
              border: `1px dashed #c4c4c4`,
              boxShadow: 0,
              mb: 2,
            }}
          >
            <AccordionSummary expandIcon={<ExpandMore />} aria-controls="panel1a-content" id="panel1a-header">
              <div
                style={{
                  display: 'flex',
                  alignItems: 'center',
                  flexWrap: 'wrap',
                }}
              >
                <DeveloperBoard sx={{ fontSize: '180%', mr: 2, color: `${theme.palette.secondary.main}` }} />
                <h1 style={{ lineHeight: '12px', color: `${theme.palette.primary.main}` }}>Operations</h1>
              </div>
            </AccordionSummary>

            <AccordionDetails>
              <Grid container spacing={2}>
                <DataGrid autoHeight columns={[]} rows={[]} />
              </Grid>
            </AccordionDetails>
          </Accordion>

          {/*Outcomes*/}
          {/*Outcomes*/}
          {/*Outcomes*/}
          {/*Outcomes*/}
          {/*Outcomes*/}
          {/*Outcomes*/}
          {/*Outcomes*/}

          <Accordion
            expanded
            sx={{
              backgroundColor: '#f9f9f9',
              border: `1px dashed #c4c4c4`,
              boxShadow: 0,
            }}
          >
            <AccordionSummary expandIcon={<ExpandMore />} aria-controls="panel1a-content" id="panel1a-header">
              <div
                style={{
                  display: 'flex',
                  alignItems: 'center',
                  flexWrap: 'wrap',
                }}
              >
                <StorageIcon sx={{ fontSize: '180%', mr: 2, color: `${theme.palette.secondary.main}` }} />
                <h1 style={{ lineHeight: '12px', color: `${theme.palette.primary.main}` }}>Outcomes</h1>
              </div>
            </AccordionSummary>

            <AccordionDetails>
              <Button
                disabled={false}
                color="secondary"
                size="large"
                type="submit"
                variant="contained"
                fullWidth
                sx={{ mb: 5 }}
                onClick={() => setOpenedGenerationDialog(true)}
              >
                Generate outcome
              </Button>
              {openedGenerationDialog && (
                <GenerateOutcomeDialog
                  open={openedGenerationDialog}
                  worksheetId={id}
                  handleClose={() => setOpenedGenerationDialog(false)}
                />
              )}
              <Grid container spacing={2}>
                <DataGrid autoHeight columns={outcomesColumns} rows={summary?.outcomes ?? []} />
              </Grid>
            </AccordionDetails>
          </Accordion>
        </Container>
      )}
    </>
  );
};

export default WorksheetSummary;
