import { navigationClosedMixin, navigationWidth, navigationOpenedMixin } from '../navigation.mixin';
import { CSSObject, Theme } from '@mui/material/styles';

export const navigationBaseMixin = (theme: Theme, open: boolean | undefined): CSSObject => ({
  width: navigationWidth,
  flexShrink: 0,
  whiteSpace: 'nowrap',
  boxSizing: 'border-box',
  ...(open && {
    ...navigationOpenedMixin(theme),
    '& .MuiDrawer-paper': navigationOpenedMixin(theme),
  }),
  ...(!open && {
    ...navigationClosedMixin(theme),
    '& .MuiDrawer-paper': navigationClosedMixin(theme),
  }),
});
