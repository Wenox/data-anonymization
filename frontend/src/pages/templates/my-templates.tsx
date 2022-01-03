import { useQuery } from 'react-query';
import { DataGrid, GridColDef } from '@mui/x-data-grid';
import { Button, CircularProgress, Container, Divider, IconButton } from '@mui/material';
import Typography from '@mui/material/Typography';
import { getAllMyTemplates, getDownloadDump } from '../../api/requests/templates/templates.requests';
import { MyTemplate } from '../../api/requests/templates/templates.types';
import { theme } from '../../styles/theme';
import { useState } from 'react';
import MetadataDialog from '../../components/metadata/metadata-dialog';
import MetadataDownloadButton from '../../components/metadata/metadata-download-button';
import { useNavigate } from 'react-router-dom';
import { ROUTES } from '../../constants/routes';
import { CloudDownload, Delete, Download, Edit } from '@mui/icons-material';
import { centeredColumn } from '../../styles/data-table';
import { toast } from 'react-toastify';
import { postCreateMyWorksheet } from '../../api/requests/worksheets/worksheet.requests';

const MyTemplates = () => {
  const navigate = useNavigate();
  const { data, isLoading, isRefetching } = useQuery('myTemplates', getAllMyTemplates);
  const templates: MyTemplate[] =
    data?.data.map((template) => ({
      ...template,
      metadata: template.metadata ? JSON.stringify(template.metadata, null, 4) : null,
      activeWorksheets: 0,
    })) || [];

  const [isCreatingWorksheet, setIsCreatingWorksheet] = useState(false);

  const handleDownloadDump = (templateId: string, originalFileName: string) => {
    getDownloadDump(templateId)
      .then((response) => {
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', originalFileName);
        document.body.appendChild(link);
        link.click();
        toast.success('Successfully downloaded dump file.', {
          position: 'top-right',
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      })
      .catch((err) => {
        toast.success('Failed to downloaded the dump file.', {
          position: 'top-right',
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      });
  };

  const [isMetadataDialogOpen, setIsMetadataDialogOpen] = useState(false);
  const [metadata, setMetadata] = useState<any>({});

  const columns: GridColDef[] = [
    {
      field: 'worksheet',
      headerName: 'Worksheet actions',
      width: 360,
      sortable: false,
      filterable: false,
      ...centeredColumn(),
      renderCell: ({ row }) => {
        return (
          <>
            <Button
              disabled={row.metadata == null}
              size="medium"
              color="primary"
              variant="contained"
              sx={{ mr: 0.5 }}
              onClick={() => {
                setIsCreatingWorksheet(true);
                postCreateMyWorksheet({ templateId: row.id })
                  .then((response) => {
                    if (response.status === 200)
                      toast.success('Successfully started a new worksheet.', {
                        position: 'top-right',
                        autoClose: 5000,
                        hideProgressBar: false,
                        closeOnClick: true,
                        pauseOnHover: true,
                        draggable: true,
                        progress: undefined,
                      });
                    navigate('/');
                  })
                  .catch(() => {
                    toast.error('Failed to start a new worksheet.', {
                      position: 'top-right',
                      autoClose: 5000,
                      hideProgressBar: false,
                      closeOnClick: true,
                      pauseOnHover: true,
                      draggable: true,
                      progress: undefined,
                    });
                  })
                  .finally(() => setIsCreatingWorksheet(false));
              }}
            >
              Start worksheet
            </Button>
            <Button
              disabled={row.metadata == null}
              size="medium"
              color="primary"
              variant="contained"
              onClick={() => {}}
              sx={{ ml: 0.5 }}
            >
              View worksheets
            </Button>
          </>
        );
      },
    },
    {
      field: 'dump',
      headerName: 'Dump',
      width: 75,
      sortable: false,
      filterable: false,
      ...centeredColumn(),
      renderCell: ({ row }) => {
        return (
          <IconButton onClick={() => handleDownloadDump(row.id, row.originalFileName)}>
            <CloudDownload fontSize="large" sx={{ color: '#7f00b5' }} />
          </IconButton>
        );
      },
    },
    {
      field: 'title',
      headerName: 'Title',
      flex: 1,
      ...centeredColumn(),
    },
    {
      field: 'description',
      headerName: 'Description',
      flex: 1,
      ...centeredColumn(),
    },
    {
      field: 'originalFileName',
      headerName: 'Dump name',
      flex: 1,
      ...centeredColumn(),
    },
    {
      field: 'type',
      headerName: 'Dump type',
      width: 95,
      ...centeredColumn(),
    },
    {
      field: 'status',
      headerName: 'Template status',
      width: 160,
      ...centeredColumn(),
    },
    {
      field: 'activeWorksheets',
      headerName: 'Active worksheets',
      flex: 1,
      ...centeredColumn(),
    },
    {
      field: 'createdDate',
      headerName: 'Created date',
      width: 160,
      ...centeredColumn(),
    },
    {
      field: 'metadata',
      headerName: 'Metadata',
      width: 240,
      sortable: false,
      filterable: false,
      ...centeredColumn(),
      renderCell: ({ row }) => {
        return (
          <>
            <Button
              disabled={row.metadata == null}
              fullWidth
              color="secondary"
              variant="contained"
              onClick={() => {
                setIsMetadataDialogOpen(true);
                setMetadata({ content: row.metadata, fileName: row.originalFileName });
              }}
              sx={{ mr: 0.5 }}
            >
              View
            </Button>
            <MetadataDownloadButton metadata={{ content: row.metadata, fileName: row.originalFileName }} />
          </>
        );
      },
    },
    {
      field: 'actions',
      headerName: 'Actions',
      width: 120,
      sortable: false,
      filterable: false,
      ...centeredColumn(),
      headerAlign: 'left',
      renderCell: ({ row }) => {
        return (
          <div>
            <IconButton onClick={() => {}}>
              <Edit fontSize="large" sx={{ color: '#7f00b5' }} />
            </IconButton>
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
        pt: 2,
        pb: 2,
        borderRadius: '2px',
      }}
    >
      {isCreatingWorksheet && (
        <CircularProgress
          sx={{ position: 'absolute', left: '50%', top: '50%', transform: 'translate(-50%, -50%)' }}
          size="8rem"
        />
      )}
      {isMetadataDialogOpen && (
        <MetadataDialog
          metadata={metadata}
          open={isMetadataDialogOpen}
          handleClose={() => setIsMetadataDialogOpen(false)}
        />
      )}
      <Typography color="secondary" variant="h2" sx={{ mb: 2 }}>
        My templates
      </Typography>
      <DataGrid autoHeight columns={columns} rows={templates} loading={isLoading || isRefetching} />
      <Button
        sx={{ mt: 2 }}
        fullWidth
        color="secondary"
        variant="contained"
        onClick={() => navigate(ROUTES.TEMPLATES_GENERATE)}
      >
        Generate new template
      </Button>
    </Container>
  );
};

export default MyTemplates;
