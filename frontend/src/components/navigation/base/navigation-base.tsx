import { styled } from '@mui/material/styles';
import MuiDrawer from '@mui/material/Drawer';
import { navigationBaseMixin } from './navigation-base.mixin';

const NavigationBase = styled(MuiDrawer, { shouldForwardProp: (prop) => prop !== 'open' })(({ theme, open }) =>
  navigationBaseMixin(theme, open),
);

export default NavigationBase;
