import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import Typography from '@mui/material/Typography';
import * as React from 'react';
import { FC } from 'react';
import AppBarBase from './base/app-bar-base';

interface AppBarProps {
  header?: string;
  handleDrawerOpened: () => void;
  opened: boolean;
  color: string;
}

const AppBar: FC<AppBarProps> = ({
  header = 'Data Anonymisation — Web Platform',
  handleDrawerOpened,
  opened,
  color,
}) => (
  <AppBarBase position="fixed" sx={{ backgroundColor: color }} open={opened}>
    <Toolbar>
      <IconButton
        color="inherit"
        aria-label="open drawer"
        onClick={handleDrawerOpened}
        edge="start"
        sx={{ marginRight: '36px', ...(opened && { display: 'none' }) }}
      >
        <MenuIcon fontSize="large" />
      </IconButton>
      <Typography variant="h6" noWrap component="div">
        {header}
      </Typography>
    </Toolbar>
  </AppBarBase>
);

export default AppBar;