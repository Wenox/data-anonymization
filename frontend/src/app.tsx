import React, { FC, useState } from 'react';
import { QueryClientProvider } from 'react-query';
import { BrowserRouter } from 'react-router-dom';
import { ThemeProvider } from '@mui/material';
import { theme } from './styles/theme';
import AuthContext from './context/auth-context';
import { MeResponse } from './api/requests/me/me.types';
import Main from './pages/main';
import { queryClient } from './api/query.config';
import './styles/app.scss';

const App: FC = () => {
  const [me, setMe] = useState<MeResponse | null>(null);

  return (
    <BrowserRouter>
      <ThemeProvider theme={theme}>
        <QueryClientProvider client={queryClient}>
          <AuthContext.Provider value={{ me, setMe }}>
            <Main />
          </AuthContext.Provider>
        </QueryClientProvider>
      </ThemeProvider>
    </BrowserRouter>
  );
};

export default App;
