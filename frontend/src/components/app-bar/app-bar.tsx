import { styled } from '@mui/material/styles';
import MuiAppBar from '@mui/material/AppBar';
import { AppBarProps as MuiAppBarProps } from '@mui/material/AppBar/AppBar';
import { appBarMixin } from './app-bar.mixin';

export interface AppBarProps extends MuiAppBarProps {
  open?: boolean;
}

const AppBar = styled(MuiAppBar, { shouldForwardProp: (property) => property !== 'open' })<AppBarProps>(
  ({ theme, open }) => appBarMixin(theme, open),
);

export default AppBar;
