import { CSSObject, Theme } from '@mui/material/styles';

export const navigationWidth = 250;

export const navigationOpenedMixin = (theme: Theme): CSSObject => ({
  width: navigationWidth,
  overflowX: 'hidden',
});

export const navigationClosedMixin = (theme: Theme): CSSObject => ({
  width: `calc(${theme.spacing(7)} + 28px)`,
  [theme.breakpoints.up('sm')]: {
    width: `calc(${theme.spacing(9)} + 28px)`,
  },
  overflowX: 'hidden',
});

export const navigationOpenedMixinSmooth = (theme: Theme): CSSObject => ({
  width: navigationWidth,
  overflowX: 'hidden',
  transition: theme.transitions.create('width', {
    duration: theme.transitions.duration.enteringScreen,
    easing: theme.transitions.easing.sharp,
  }),
});

export const navigationClosedMixinSmooth = (theme: Theme): CSSObject => ({
  width: `calc(${theme.spacing(7)} + 1px)`,
  [theme.breakpoints.up('sm')]: {
    width: `calc(${theme.spacing(9)} + 1px)`,
  },
  overflowX: 'hidden',
  transition: theme.transitions.create('width', {
    duration: theme.transitions.duration.leavingScreen,
    easing: theme.transitions.easing.sharp,
  }),
});
