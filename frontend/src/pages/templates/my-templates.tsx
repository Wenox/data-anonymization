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
      headerAlign: 'center',
      align: 'center',
      headerClassName: 'data-grid-header',
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
              sx={{ ml: 0.5 }}
            >
              View worksheets
            </Button>
          </>
        );
      },
    },
    {
      field: 'title',
      headerName: 'Title',
      headerAlign: 'center',
      align: 'center',
      headerClassName: 'data-grid-header',
      flex: 1,
    },
    {
      field: 'description',
      headerName: 'Description',
      headerAlign: 'center',
      align: 'center',

      headerClassName: 'data-grid-header',
      flex: 1,
    },
    {
      headerClassName: 'data-grid-header',
      field: 'originalFileName',
      headerName: 'Dump name',
      headerAlign: 'center',
      align: 'center',

      flex: 1,
    },
    {
      headerClassName: 'data-grid-header',
      field: 'type',
      headerName: 'Dump type',
      headerAlign: 'center',
      align: 'center',
      width: 95,
    },
    {
      headerClassName: 'data-grid-header',
      field: 'status',
      headerName: 'Template status',
      headerAlign: 'center',
      align: 'center',

      width: 160,
    },
    {
      headerClassName: 'data-grid-header',
      field: 'activeWorksheets',
      headerName: 'Active worksheets',
      headerAlign: 'center',
      align: 'center',

      flex: 1,
    },
    {
      headerClassName: 'data-grid-header',
      field: 'createdDate',
      headerName: 'Created date',
      headerAlign: 'center',
      align: 'center',

      width: 160,
    },
    {
      headerClassName: 'data-grid-header',
      field: 'metadata',
      headerName: 'Metadata',
      headerAlign: 'center',
      align: 'center',

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
      headerClassName: 'data-grid-header',
      field: 'actions',
      headerName: 'Actions',
      align: 'center',

      width: 120,
      sortable: false,
      filterable: false,
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
