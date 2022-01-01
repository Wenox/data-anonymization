import { useQuery } from 'react-query';
import { DataGrid, GridColDef } from '@mui/x-data-grid';
import { Button, Container } from '@mui/material';
import Typography from '@mui/material/Typography';
import { getAllMyTemplates } from '../../api/requests/templates/templates.requests';
import { MyTemplate } from '../../api/requests/templates/templates.types';
import { theme } from '../../styles/theme';
import { useState } from 'react';
import MetadataDialog from '../../components/metadata/metadata-dialog';

const MyTemplates = () => {
  const { data, isLoading, isRefetching } = useQuery('myTemplates', getAllMyTemplates);
  const templates: MyTemplate[] =
    data?.data.map((template) => ({
      ...template,
      metadata: JSON.stringify(template.metadata, null, 4),
    })) || [];

  const [isMetadataDialogOpen, setIsMetadataDialogOpen] = useState(false);
  const [metadata, setMetadata] = useState<any>({});

  const columns: GridColDef[] = [
    { field: 'title', headerName: 'Title', flex: 1 },
    { field: 'originalFileName', headerName: 'Dump name', flex: 1 },
    { field: 'type', headerName: 'Dump type', flex: 1 },
    { field: 'status', headerName: 'Template status', flex: 1 },
    { field: 'description', headerName: 'Description', flex: 1 },
    { field: 'createdDate', headerName: 'Created date', flex: 1 },
    {
      field: 'metadata',
      headerName: 'Metadata',
      flex: 1,
      renderCell: ({ row }) => {
        return (
          <Button
            onClick={() => {
              setIsMetadataDialogOpen(true);
              setMetadata({ content: row.metadata, fileName: row.originalFileName });
            }}
          >
            Show
          </Button>
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
        pb: 3,
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
    </Container>
  );
};

export default MyTemplates;
