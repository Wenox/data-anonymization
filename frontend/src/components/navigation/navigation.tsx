/* eslint-disable no-unused-vars */
import * as React from 'react';
import { useTheme } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import { useState } from 'react';
import { LoginOutlined } from '@mui/icons-material';
import { useNavigate } from 'react-router-dom';
import NavigationHeader from './header/navigation-header';
import NavigationBase from './base/navigation-base';
import AppBar from '../app-bar/app-bar';

const Navigation = ({ children }: React.PropsWithChildren<{}>) => {
  const theme = useTheme();
  const [opened, setOpened] = useState(false);

  const navigate = useNavigate();

  const handleDrawerOpened = () => {
    setOpened(true);
  };

  const handleDrawerClosed = () => {
    setOpened(false);
  };

  return (
    <Box sx={{ display: 'flex' }}>
      <AppBar position="fixed" open={opened}>
        <Toolbar>
          <IconButton
            color="inherit"
            aria-label="open drawer"
            onClick={handleDrawerOpened}
            edge="start"
            sx={{
              marginRight: '36px',
              ...(opened && { display: 'none' }),
            }}
          >
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" noWrap component="div">
            Data Anonymisation — Web Platform
          </Typography>
        </Toolbar>
      </AppBar>
      <NavigationBase variant="permanent" open={opened}>
        <NavigationHeader>
          <IconButton onClick={handleDrawerClosed}>
            {theme.direction === 'rtl' ? <ChevronRightIcon /> : <ChevronLeftIcon />}
          </IconButton>
        </NavigationHeader>

        <Divider />

        <List>
          <ListItem button key={'Login'} onClick={(e) => navigate('/login')}>
            <ListItemIcon>
              <LoginOutlined />
            </ListItemIcon>
            <ListItemText primary={'Login'} />
          </ListItem>
        </List>
      </NavigationBase>
      <Box component="main" sx={{ flexGrow: 1, pt: 6 }}>
        {children}
      </Box>
    </Box>
  );
};

export default Navigation;
