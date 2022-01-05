import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import Typography from '@mui/material/Typography';
import * as React from 'react';
import { FC } from 'react';
import AppBarBase from './base/app-bar-base';
import { theme } from '../../styles/theme';

interface AppBarProps {
  header?: string;
  handleDrawerOpened: () => void;
  opened: boolean;
}

const AppBar: FC<AppBarProps> = ({ header = 'Data Anonymisation — Web Platform', handleDrawerOpened, opened }) => (
  <AppBarBase position="fixed" sx={{ backgroundColor: `${theme.palette.primary.main}` }} open={opened}>
    <Toolbar>
      <IconButton
        color="inherit"
        aria-label="open drawer"
        onClick={handleDrawerOpened}
        edge="start"
        sx={{
          marginLeft: '-9px',
          marginRight: '36px',
          ...(opened && { display: 'none' }),
          pt: '12px',
          pb: '12px',
          '&:hover': {
            '& .icon': {
              color: theme.palette.secondary.main,
            },
          },
        }}
      >
        <MenuIcon sx={{ fontSize: '160%' }} className="icon" fontSize="large" />
      </IconButton>
      <Typography variant="h6" noWrap component="div">
        {header}
      </Typography>
    </Toolbar>
  </AppBarBase>
);

export default AppBar;
