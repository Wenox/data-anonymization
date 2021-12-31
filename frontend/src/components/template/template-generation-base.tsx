import React, { FC } from 'react';
import { Container, Divider, Grid } from '@mui/material';
import { theme } from '../../styles/theme';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import { getStepIconFromStepStatus, getStepMessageFromStepStatus } from './template-generation-base.utils';
import { Steps, TemplateGenerationStepStatus } from './template-generation-base.types';

interface TemplateGenerationBaseProps {
  header?: string;
  steps: Steps;
}

const TemplateGenerationBase: FC<TemplateGenerationBaseProps> = ({
  header = 'Generating template',
  steps,
}: TemplateGenerationBaseProps) => {
  const { step1, step2, step3 } = steps;
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
              <h2 style={{ color: theme.palette.primary.main }}>Persist dump file</h2>
            </Grid>
            <Grid item xs={12} textAlign="center">
              {getStepIconFromStepStatus(step1)}
            </Grid>
            <Grid item xs={12} textAlign="center">
              <h2>{getStepMessageFromStepStatus(step1)}</h2>
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
              {getStepIconFromStepStatus(step2)}
            </Grid>
            <Grid item xs={12} textAlign="center">
              <h2>{getStepMessageFromStepStatus(step2)}</h2>
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
              {getStepIconFromStepStatus(step3)}
            </Grid>
            <Grid item xs={12} textAlign="center">
              <h2>{getStepMessageFromStepStatus(step3)}</h2>
            </Grid>
          </Grid>
        </Grid>
      </Grid>
      <Divider sx={{ mt: 2 }} />
      <Grid container spacing={2} marginY={1}>
        <Grid item xs={6} textAlign="center">
          <Button
            disabled={step3 !== TemplateGenerationStepStatus.SUCCESS}
            color="secondary"
            onClick={() => {}}
            variant="contained"
            fullWidth
          >
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

export default TemplateGenerationBase;
