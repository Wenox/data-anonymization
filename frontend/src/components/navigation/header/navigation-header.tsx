import { FC } from 'react';
import IconButton from '@mui/material/IconButton';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import NavigationHeaderBase from './navigation-header-base';
import * as React from 'react';
import { useTheme } from '@mui/material/styles';

const NavigationHeader: FC<{ handleDrawerClosed: () => void }> = ({ handleDrawerClosed }) => {
  const theme = useTheme();
  return (
    <NavigationHeaderBase>
      <IconButton onClick={handleDrawerClosed}>
        {theme.direction === 'rtl' ? (
          <ChevronRightIcon fontSize="large" />
        ) : (
          <ChevronLeftIcon
            fontSize="large"
            sx={{
              color: '#212121',
              '&:hover': {
                color: '#dd2c00',
              },
            }}
          />
        )}
      </IconButton>
    </NavigationHeaderBase>
  );
};

export default NavigationHeader;
