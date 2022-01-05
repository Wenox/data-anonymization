import { createTheme } from '@mui/material/styles';

export const theme = createTheme({
  palette: {
    // black
    primary: {
      light: '#484848',
      main: '#212121',
      dark: '#000',
      contrastText: '#fff',
    },

    // pink
    // secondary: {
    //   light: '#ff6090',
    //   main: '#e91e63',
    //   dark: '#b0003a',
    //   contrastText: '#fff',
    // },

    // green
    // secondary: {
    //   light: '#6abf69',
    //   main: '#388e3c',
    //   dark: '#00600f',
    //   contrastText: '#fff',
    // }

    // dark red
    secondary: {
      light: '#ff5131',
      main: '#d50000',
      dark: '#9b0000',
      contrastText: '#fff',
    },

    // secondary: {
    //   light: '#ff7961',
    //   main: '#f44336',
    //   dark: '#ba000d',
    //   contrastText: '#fff',
    // },
  },
});
