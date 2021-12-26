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

  const color = getColorForRole(me!.role);

  const handleDrawerOpened = () => {
    setOpened(true);
  };

  const handleDrawerClosed = () => {
    setOpened(false);
  };

  return (
    <Box sx={{ display: 'flex' }}>
      <AppBar handleDrawerOpened={handleDrawerOpened} opened={opened} color={color} />
      <NavigationBase variant="permanent" open={opened}>
        <NavigationHeader handleDrawerClosed={handleDrawerClosed} />
        <NavigationContent role={me!.role} color={color} />
        <Divider />
      </NavigationBase>
      <Box component="main" sx={{ flexGrow: 1, pt: 6 }}>
        {children}
      </Box>
    </Box>
  );
};

export default Navigation;
