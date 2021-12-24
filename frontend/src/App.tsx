import React, { FC, useEffect, useState } from 'react';
import Main from './pages/main';
import { QueryClient, QueryClientProvider } from 'react-query';
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import About from './pages/about';
import Login from './pages/login';
import Register from './pages/register';
import { Container, ThemeProvider } from '@mui/material';
import PrivateRoute from './components/private-route';
import { theme } from './styles/theme';
import ResetPassword from './pages/reset-password/reset-password';
import ChangePasswordLoading from './pages/reset-password/change-password-loading';
import ChangePasswordForm from './pages/reset-password/change-password-form';
import InvalidPasswordToken from './pages/reset-password/tokens/invalid-password-token';
import ExpiredPasswordToken from './pages/reset-password/tokens/expired-password-token';
import ConsumedPasswordToken from './pages/reset-password/tokens/consumed-password-token';
import Users from './pages/users';
import AuthContext from './context/auth-context';
import CssBaseline from '@mui/material/CssBaseline';
import { MeResponse } from './api/requests/me/me.types';
import { getMe } from './api/requests/me/me.requests';
import VerifyMailLoading from './pages/verify-mail/verify-mail-loading';
import ExpiredVerifyToken from './pages/verify-mail/tokens/expired-verify-token';
import InvalidVerifyToken from './pages/verify-mail/tokens/invalid-verify-token';
import UserAlreadyVerified from './pages/verify-mail/tokens/user-already-verified';
import UserVerifySuccess from './pages/verify-mail/tokens/user-verify-success';
import VerifyMailPrompt from './pages/verify-mail/verify-mail-prompt';
import ResentVerifyToken from './pages/verify-mail/tokens/resent-verify-token';
import MiniDrawer from './components/mini-drawer';

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
                    <Route path="/" element={<PrivateRoute />}>
                      <Route path="/" element={<Main />} />
                    </Route>
                    <Route path="/about" element={<PrivateRoute />}>
                      <Route path="/about" element={<About />} />
                    </Route>
                    <Route path="/users" element={<PrivateRoute />}>
                      <Route
                        path="/users"
                        element={
                          <MiniDrawer>
                            <Users />
                          </MiniDrawer>
                        }
                      />
                    </Route>
                    <Route path="/login" element={<Login />} />
                    <Route path="/register" element={<Register />} />
                    <Route path="/reset-password" element={<ResetPassword />} />
                    <Route path="/change-password" element={<ChangePasswordLoading />} />
                    <Route path="/change-password/form" element={<ChangePasswordForm />} />
                    <Route path="/change-password/invalid-token" element={<InvalidPasswordToken />} />
                    <Route path="/change-password/expired-token" element={<ExpiredPasswordToken />} />
                    <Route path="/change-password/consumed-token" element={<ConsumedPasswordToken />} />
                    <Route path="/verify-mail" element={<VerifyMailLoading />} />
                    <Route path="/verify-mail-prompt" element={<VerifyMailPrompt />} />
                    <Route path="/verify-mail/success" element={<UserVerifySuccess />} />
                    <Route path="/verify-mail/invalid-token" element={<InvalidVerifyToken />} />
                    <Route path="/verify-mail/expired-token" element={<ExpiredVerifyToken />} />
                    <Route path="/verify-mail/already-verified" element={<UserAlreadyVerified />} />
                    <Route path="/verify-mail/token-sent-again" element={<ResentVerifyToken />} />
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
