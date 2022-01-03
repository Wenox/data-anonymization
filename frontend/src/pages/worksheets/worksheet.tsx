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
import { CloudDownload, ExpandMore } from '@mui/icons-material';
import { DataGrid } from '@mui/x-data-grid';
import { getMyWorksheetSummary } from '../../api/requests/worksheets/worksheet.requests';
import { useSearchParams } from 'react-router-dom';
import { toast } from 'react-toastify';
import { WorksheetSummary } from '../../api/requests/worksheets/worksheet.types';
import { getDownloadDump } from '../../api/requests/templates/templates.requests';

const Worksheet: FC = () => {
  const [searchParams] = useSearchParams();
  const id: string = searchParams.get('worksheet_id') ?? '';

  const [isLoading, setIsLoading] = useState(true);
  const [summary, setSummary] = useState<WorksheetSummary>();
  const [isMetadataDialogOpen, setIsMetadataDialogOpen] = useState(false);

  useEffect(() => {
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
          setSummary(response.data);
          console.log('summary: ', summary);
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
      .finally(() => setIsLoading(false));
  }, []);

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

  return (
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
      <Typography fontWeight={'300'} color="primary" variant="h3" sx={{ mb: 2 }}>
        Worksheet summary
      </Typography>
      <Divider sx={{ mb: 3 }} />

      {isLoading && (
        <CircularProgress
          size="10rem"
          color="secondary"
          style={{
            position: 'absolute',
            left: '50%',
            top: '50%',
            transform: 'translate(-50%, -50%)',
          }}
        />
      )}

      <Accordion
        sx={{
          backgroundColor: '#f9f9f9',
          border: `1px dashed #c4c4c4`,
          boxShadow: 0,
          mt: 3,
          mb: 2,
        }}
      >
        <AccordionSummary expandIcon={<ExpandMore />} aria-controls="panel1a-content" id="panel1a-header">
          <Typography fontWeight={'300'} color="secondary" variant="h4">
            Template
          </Typography>
        </AccordionSummary>

        <AccordionDetails>
          <Grid container spacing={2}>
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
              <h4 style={{ color: `${theme.palette.primary.light}`, marginTop: '-1px', marginBottom: '-2px' }}>Dump</h4>
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
              <IconButton onClick={() => handleDownloadDump(summary!.template.id, summary!.template.originalFileName)}>
                <CloudDownload fontSize="large" sx={{ color: '#7f00b5', mr: 1 }} />
                Download{' '}
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
                metadata={{
                  content: JSON.stringify(summary?.template.metadata, null, 4),
                  fileName: summary?.template.originalFileName,
                }}
              />
            </Grid>
          </Grid>
        </AccordionDetails>
      </Accordion>

      <Accordion
        sx={{
          backgroundColor: '#f9f9f9',
          border: `1px dashed #c4c4c4`,
          boxShadow: 0,
          mb: 2,
        }}
      >
        <AccordionSummary expandIcon={<ExpandMore />} aria-controls="panel1a-content" id="panel1a-header">
          <Typography fontWeight={'300'} color="secondary" variant="h4">
            Tables
          </Typography>
        </AccordionSummary>

        <AccordionDetails>
          <Grid container spacing={2}>
            <DataGrid autoHeight columns={[]} rows={[]} />
          </Grid>
        </AccordionDetails>
      </Accordion>

      <Accordion
        sx={{
          backgroundColor: '#f9f9f9',
          border: `1px dashed #c4c4c4`,
          boxShadow: 0,
          mb: 2,
        }}
      >
        <AccordionSummary expandIcon={<ExpandMore />} aria-controls="panel1a-content" id="panel1a-header">
          <Typography fontWeight={'300'} color="secondary" variant="h4">
            Operations
          </Typography>
        </AccordionSummary>

        <AccordionDetails>
          <Grid container spacing={2}>
            <DataGrid autoHeight columns={[]} rows={[]} />
          </Grid>
        </AccordionDetails>
      </Accordion>

      <Accordion
        sx={{
          backgroundColor: '#f9f9f9',
          border: `1px dashed #c4c4c4`,
          boxShadow: 0,
        }}
      >
        <AccordionSummary expandIcon={<ExpandMore />} aria-controls="panel1a-content" id="panel1a-header">
          <Typography fontWeight={'300'} color="secondary" variant="h4">
            Outcomes
          </Typography>
        </AccordionSummary>

        <AccordionDetails>
          <Grid container spacing={2}>
            <DataGrid autoHeight columns={[]} rows={[]} />
          </Grid>
        </AccordionDetails>
      </Accordion>
    </Container>
  );
};

export default Worksheet;
