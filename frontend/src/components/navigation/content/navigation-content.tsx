import { NAVIGATION_ITEMS } from '../../../constants/navigation-items';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import List from '@mui/material/List';
import * as React from 'react';
import { FC } from 'react';
import { Role } from '../../../api/requests/shared.types';
import { useNavigate } from 'react-router-dom';

interface NavigationContentProps {
  role: Role;
  color: string;
}

const NavigationContent: FC<NavigationContentProps> = ({ role, color }) => {
  const navigate = useNavigate();
  return (
    <List>
      {NAVIGATION_ITEMS.filter(({ roles }) => roles.includes(role)).map(({ path, name, icon }) => {
        const Icon = icon;
        return (
          <ListItem button key={path} onClick={(e) => navigate(path)}>
            <ListItemIcon>
              <Icon fontSize="large" sx={{ color: color }} />
            </ListItemIcon>
            <ListItemText primary={name} />
          </ListItem>
        );
      })}
    </List>
  );
};

export default NavigationContent;
