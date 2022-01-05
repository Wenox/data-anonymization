import * as React from 'react';
import { useContext, useState } from 'react';
import Box from '@mui/material/Box';
import Divider from '@mui/material/Divider';
import NavigationBase from './base/navigation-base';
import AppBar from '../app-bar/app-bar';
import AuthContext from '../../context/auth-context';
import NavigationContent from './content/navigation-content';
import NavigationHeader from './header/navigation-header';
import { getColorForRole } from '../../constants/colors';

const Navigation = ({ children }: React.PropsWithChildren<{}>) => {
  const [opened, setOpened] = useState(false);
  const { me } = useContext(AuthContext);

  const handleDrawerOpened = () => {
    setOpened(true);
  };

  const handleDrawerClosed = () => {
    setOpened(false);
  };

  return (
    <Box sx={{ display: 'flex' }}>
      <AppBar handleDrawerOpened={handleDrawerOpened} opened={opened} />
      <NavigationBase variant="permanent" open={opened}>
        <NavigationHeader handleDrawerClosed={handleDrawerClosed} />
        <NavigationContent role={me!.role} />
        <Divider sx={{ mt: '-8px' }} />
      </NavigationBase>
      <Box
        component="main"
        sx={{
          flexGrow: 1,
          mt: 13,
          ml: '32px',
          mr: '32px',
        }}
      >
        {children}
      </Box>
    </Box>
  );
};

export default Navigation;
