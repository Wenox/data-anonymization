import React, { FC, useEffect, useState } from 'react';
import { QueryClient, QueryClientProvider } from 'react-query';
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import { Container, ThemeProvider } from '@mui/material';
import PrivateRoute from './components/private-route';
import { theme } from './styles/theme';
import AuthContext from './context/auth-context';
import CssBaseline from '@mui/material/CssBaseline';
import { MeResponse } from './api/requests/me/me.types';
import { getMe } from './api/requests/me/me.requests';
import Navigation from './components/navigation/navigation';
import { MAIN_ROUTES } from './constants/routes';

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      cacheTime: 0,
    },
  },
});

const App: FC = () => {
  const [me, setMe] = useState<MeResponse | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    getMe()
      .then((response) => {
        setMe(response.data);
        setIsLoading(false);
      })
      .catch(() => setIsLoading(false));
  }, []);

  return (
    <BrowserRouter>
      <ThemeProvider theme={theme}>
        <QueryClientProvider client={queryClient}>
          <AuthContext.Provider value={{ me, setMe }}>
            <Container maxWidth={'xl'} id={me ? 'app-container' : 'app-login'}>
              {!isLoading && (
                <>
                  <CssBaseline />

                  <Routes>
                    {MAIN_ROUTES.map((route) => {
                      const Element = route.element;

                      if (route.authenticated) {
                        return (
                          <Route key={route.path} path={route.path} element={<PrivateRoute />}>
                            <Route
                              key={route.path}
                              path={route.path}
                              element={
                                route.menu ? (
                                  <Navigation>
                                    <Element />
                                  </Navigation>
                                ) : (
                                  <Element />
                                )
                              }
                            />
                          </Route>
                        );
                      } else {
                        return (
                          <Route
                            key={route.path}
                            path={route.path}
                            element={
                              route.menu ? (
                                <Navigation>
                                  <Element />
                                </Navigation>
                              ) : (
                                <Element />
                              )
                            }
                          />
                        );
                      }
                    })}

                    <Route path="*" element={<Navigate to="/login" />} />
                  </Routes>
                </>
              )}
            </Container>
          </AuthContext.Provider>
        </QueryClientProvider>
      </ThemeProvider>
    </BrowserRouter>
  );
};

export default App;
