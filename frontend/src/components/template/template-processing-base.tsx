import React, { FC, useEffect, useState } from 'react';
import { CircularProgress, Container, Divider, Grid, IconButton } from '@mui/material';
import { theme } from '../../styles/theme';
import Typography from '@mui/material/Typography';
import { useSearchParams } from 'react-router-dom';
import { getTemplateStatus } from '../../api/requests/templates/templates.requests';
import { toast } from 'react-toastify';
import {
  Block,
  Cancel,
  Check,
  CheckCircle,
  CheckCircleOutline,
  DoneOutline,
  ErrorOutline,
  ErrorOutlineOutlined,
  HourglassTopOutlined,
} from '@mui/icons-material';
import Button from '@mui/material/Button';

interface TemplateProcessingBaseProps {
  header?: string;
  step1: string;
  step2: string;
  step3: string;
}

const getIconFromStatus = (status: string) => {
  if (status === 'inprogress') {
    return <CircularProgress color="secondary" size="6rem" />;
  } else if (status === 'success') {
    return <CheckCircleOutline color="success" sx={{ fontSize: '600%' }} />;
  } else if (status === 'error') {
    return <ErrorOutlineOutlined color="error" sx={{ fontSize: '600%' }} />;
  } else if (status === 'waiting') {
    return <HourglassTopOutlined color="secondary" sx={{ fontSize: '600%' }} />;
  } else if (status === 'cancelled') {
    return <Block color="secondary" sx={{ fontSize: '600%' }} />;
  }
};

const getMessageFromStatus = (status: string) => {
  if (status === 'inprogress') {
    return 'Loading...';
  } else if (status === 'success') {
    return 'Success';
  } else if (status === 'error') {
    return 'Error';
  } else if (status === 'waiting') {
    return 'Waiting';
  } else if (status === 'cancelled') {
    return 'Cancelled';
  }
};

const TemplateProcessingBase: FC<TemplateProcessingBaseProps> = ({
  header = 'Generating new template',
  step1,
  step2,
  step3,
}: TemplateProcessingBaseProps) => {
  return (
    <Container
      maxWidth={'lg'}
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
        {header}
      </Typography>
      <Divider sx={{ mb: 2 }} />
      <Grid container spacing={0}>
        <Grid sx={{ border: `1px solid rgba(0, 0, 0, 0.12)` }} item xs={4}>
          <Grid container spacing={0} alignItems="center">
            <Grid item xs={12} textAlign="center">
              <h1 style={{ color: theme.palette.primary.main }}>Step 1</h1>
              <Divider />
            </Grid>
            <Grid item xs={12} textAlign="center">
              <h2 style={{ color: theme.palette.primary.main }}>Store template</h2>
            </Grid>
            <Grid item xs={12} textAlign="center">
              {getIconFromStatus(step1)}
            </Grid>
            <Grid item xs={12} textAlign="center">
              <h2>{getMessageFromStatus(step1)}</h2>
            </Grid>
          </Grid>
        </Grid>
        <Grid
          sx={{
            borderTop: `1px solid rgba(0, 0, 0, 0.12)`,
            borderBottom: `1px solid rgba(0, 0, 0, 0.12)`,
          }}
          item
          xs={4}
        >
          <Grid container spacing={0} alignItems="center">
            <Grid item xs={12} textAlign="center">
              <h1 style={{ color: theme.palette.primary.main }}>Step 2</h1>
              <Divider />
            </Grid>
            <Grid item xs={12} textAlign="center">
              <h2 style={{ color: theme.palette.primary.main }}>Restore database</h2>
            </Grid>
            <Grid item xs={12} textAlign="center">
              {getIconFromStatus(step2)}
            </Grid>
            <Grid item xs={12} textAlign="center">
              <h2>{getMessageFromStatus(step2)}</h2>
            </Grid>
          </Grid>
        </Grid>
        <Grid sx={{ border: `1px solid rgba(0, 0, 0, 0.12)` }} item xs={4}>
          <Grid container spacing={0} alignItems="center">
            <Grid item xs={12} textAlign="center">
              <h1 style={{ color: theme.palette.primary.main }}>Step 3</h1>
              <Divider />
            </Grid>
            <Grid item xs={12} textAlign="center">
              <h2 style={{ color: theme.palette.primary.main }}>Extract metadata</h2>
            </Grid>
            <Grid item xs={12} textAlign="center">
              {getIconFromStatus(step3)}
            </Grid>
            <Grid item xs={12} textAlign="center">
              <h2>{getMessageFromStatus(step3)}</h2>
            </Grid>
          </Grid>
        </Grid>
      </Grid>
      <Divider sx={{ mt: 2 }} />
      <Grid container spacing={2} marginY={1}>
        <Grid item xs={6} textAlign="center">
          <Button disabled={step3 !== 'success'} color="secondary" onClick={() => {}} variant="contained" fullWidth>
            Produce new worksheet
          </Button>
        </Grid>
        <Grid item xs={6} textAlign="center">
          <Button color="primary" onClick={() => {}} variant="contained" fullWidth>
            Generate another template
          </Button>
        </Grid>
        <Grid item xs={12}>
          <Button color="primary" onClick={() => {}} fullWidth variant="outlined" sx={{ mt: 0 }}>
            View all templates
          </Button>
        </Grid>
      </Grid>
    </Container>
  );
};

export default TemplateProcessingBase;
