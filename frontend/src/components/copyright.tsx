import { FC } from 'react';
import Typography from '@mui/material/Typography';
import Link from '@mui/material/Link';

export const Copyright: FC = () => (
  <Typography variant="body2" color="text.secondary" sx={{ mt: 10 }}>
    {'Copyright © '}
    <Link color="inherit" href="help">
      Data Anonymisation Web Platform
    </Link>{' '}
    {new Date().getFullYear()}
    {'.'}
  </Typography>
);
