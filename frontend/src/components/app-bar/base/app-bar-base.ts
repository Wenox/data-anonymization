import { styled } from '@mui/material/styles';
import MuiAppBar from '@mui/material/AppBar';
import { AppBarProps as MuiAppBarProps } from '@mui/material/AppBar/AppBar';
import { appBarBaseMixin } from './app-bar-base.mixin';

export interface AppBarBaseProps extends MuiAppBarProps {
  open?: boolean;
}

const AppBarBase = styled(MuiAppBar, { shouldForwardProp: (property) => property !== 'open' })<AppBarBaseProps>(
  ({ theme, open }) => appBarBaseMixin(theme, open),
);

export default AppBarBase;
