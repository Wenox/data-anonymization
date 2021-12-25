/* eslint-disable no-unused-vars */
import { CSSObject, Theme } from '@mui/material/styles';
import { navigationWidth } from '../../navigation/navigation.mixin';

export const appBarBaseMixin = (theme: Theme, open: boolean | undefined): CSSObject => ({
  zIndex: theme.zIndex.drawer + 1,
  ...(open && {
    marginLeft: navigationWidth,
    width: `calc(100% - ${navigationWidth}px)`,
  }),
});

export const appBarBaseMixinSmooth = (theme: Theme, open: boolean | undefined): CSSObject => ({
  zIndex: theme.zIndex.drawer + 1,
  transition: theme.transitions.create(['width', 'margin'], {
    duration: theme.transitions.duration.leavingScreen,
    easing: theme.transitions.easing.sharp,
  }),
  ...(open && {
    marginLeft: navigationWidth,
    width: `calc(100% - ${navigationWidth}px)`,
    transition: theme.transitions.create(['width', 'margin'], {
      duration: theme.transitions.duration.enteringScreen,
      easing: theme.transitions.easing.sharp,
    }),
  }),
});
