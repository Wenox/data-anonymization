import { FC } from 'react';
import IconButton from '@mui/material/IconButton';
import NavigationHeaderBase from './navigation-header-base';
import * as React from 'react';
import { KeyboardDoubleArrowLeft } from '@mui/icons-material';
import { theme } from '../../../styles/theme';

const NavigationHeader: FC<{ handleDrawerClosed: () => void }> = ({ handleDrawerClosed }) => {
  return (
    <NavigationHeaderBase>
      <IconButton onClick={handleDrawerClosed}>
        <KeyboardDoubleArrowLeft
          sx={{
            fontSize: '200%',
            color: theme.palette.primary.main,
            '&:hover': {
              color: theme.palette.secondary.main,
            },
          }}
        />
      </IconButton>
    </NavigationHeaderBase>
  );
};

export default NavigationHeader;
