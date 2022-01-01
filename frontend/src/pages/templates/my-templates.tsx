import { useQuery } from 'react-query';
import { DataGrid, GridColDef } from '@mui/x-data-grid';
import { Button, Container, Divider, IconButton } from '@mui/material';
import Typography from '@mui/material/Typography';
import { getAllMyTemplates } from '../../api/requests/templates/templates.requests';
import { MyTemplate } from '../../api/requests/templates/templates.types';
import { theme } from '../../styles/theme';
import { useState } from 'react';
import MetadataDialog from '../../components/metadata/metadata-dialog';
import MetadataDownloadButton from '../../components/metadata/metadata-download-button';
import { useNavigate } from 'react-router-dom';
import { ROUTES } from '../../constants/routes';
import { Delete, Edit } from '@mui/icons-material';

const MyTemplates = () => {
  const navigate = useNavigate();
  const { data, isLoading, isRefetching } = useQuery('myTemplates', getAllMyTemplates);
  const templates: MyTemplate[] =
    data?.data.map((template) => ({
      ...template,
      metadata: template.metadata ? JSON.stringify(template.metadata, null, 4) : null,
      activeWorksheets: 0,
    })) || [];

  const [isMetadataDialogOpen, setIsMetadataDialogOpen] = useState(false);
  const [metadata, setMetadata] = useState<any>({});

  const columns: GridColDef[] = [
    {
      field: 'worksheet',
      headerName: 'Worksheet actions',
      width: 360,
      sortable: false,
      filterable: false,
      renderCell: ({ row }) => {
        return (
          <>
            <Button
              disabled={row.metadata == null}
              size="medium"
              color="primary"
              variant="contained"
              onClick={() => {}}
              sx={{ mr: 0.5 }}
            >
              Start worksheet
            </Button>
            <Button
              disabled={row.metadata == null}
              size="medium"
              color="primary"
              variant="contained"
              onClick={() => {}}
              sx={{ mr: 0.5 }}
            >
              View worksheets
            </Button>
          </>
        );
      },
    },
    { field: 'activeWorksheets', headerName: 'Active worksheets', width: 145 },
    { field: 'title', headerName: 'Title', flex: 1 },
    { field: 'description', headerName: 'Description', flex: 1 },
    { field: 'originalFileName', headerName: 'Dump name', flex: 1 },
    { field: 'type', headerName: 'Dump type', flex: 1 },
    { field: 'status', headerName: 'Template status', flex: 1 },
    { field: 'createdDate', headerName: 'Created date', width: 160 },
    {
      field: 'metadata',
      headerName: 'Metadata',
      width: 240,
      sortable: false,
      filterable: false,
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
      renderCell: ({ row }) => {
        return (
          <div>
            <IconButton onClick={() => {}}>
              <Edit fontSize="large" sx={{ color: 'blue' }} />
            </IconButton>
            <IconButton onClick={() => {}}>
              <Delete fontSize="large" sx={{ color: 'red' }} />
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
