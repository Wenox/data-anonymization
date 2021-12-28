import { NAVIGATION_ITEMS } from '../../../constants/navigation-items';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import List from '@mui/material/List';
import * as React from 'react';
import { FC } from 'react';
import { Role } from '../../../api/requests/shared.types';
import { useNavigate } from 'react-router-dom';
import { useTheme } from '@mui/material/styles';
import { theme } from '../../../styles/theme';
import Divider from '@mui/material/Divider';

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
          <>
            <Divider />
            <ListItem
              sx={{
                pt: '12px',
                pb: '12px',
                '&:hover': {
                  '& .icon': {
                    color: theme.palette.secondary.main,
                  },
                },
              }}
              button
              key={path}
              onClick={(e) => navigate(path)}
            >
              <ListItemIcon>
                <Icon className="icon" sx={{ fontSize: '400%', color: color }} />
              </ListItemIcon>
              <ListItemText
                sx={{ ml: '18px' }}
                primaryTypographyProps={{
                  style: {
                    fontSize: '140%',
                  },
                }}
                primary={name}
              />
            </ListItem>
          </>
        );
      })}
    </List>
  );
};

export default NavigationContent;
