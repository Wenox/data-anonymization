import React, { FC, useState } from 'react';
import { theme } from '../../styles/theme';
import Typography from '@mui/material/Typography';
import { Accordion, AccordionDetails, AccordionSummary, Button, Container, Divider, Grid } from '@mui/material';
import TextField from '@mui/material/TextField';
import MetadataDownloadButton from '../../components/metadata/metadata-download-button';
import { CloudDownload, ExpandMore } from '@mui/icons-material';

const Worksheet: FC = () => {
  const [isMetadataDialogOpen, setIsMetadataDialogOpen] = useState(false);

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
                defaultValue="Music"
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
                defaultValue="PostgreSQL"
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
                defaultValue="Longer description of this template goes here."
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
              <Button
                fullWidth
                color="primary"
                variant="outlined"
                onClick={() => {
                  setIsMetadataDialogOpen(true);
                }}
                sx={{ height: '100%', fontSize: '120%', backgroundColor: '#fff' }}
              >
                <CloudDownload fontSize="large" sx={{ color: '#7f00b5', mr: 1 }} />
                Download
              </Button>
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
              <MetadataDownloadButton buttonText="Download" metadata={{ content: '', fileName: '' }} />
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
          <Grid container spacing={2}></Grid>
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
          <Grid container spacing={2}></Grid>
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
          <Grid container spacing={2}></Grid>
        </AccordionDetails>
      </Accordion>
    </Container>
  );
};

export default Worksheet;
