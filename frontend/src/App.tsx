import React, {FC} from 'react';
import Main from './pages/main';
import {QueryClient, QueryClientProvider} from 'react-query';
import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom";
import About from "./pages/about";
import Login from "./pages/login";
import Register from "./pages/register";
import Navigation from "./components/navigation";
import {Box, ThemeProvider} from "@mui/material";
import PrivateRoute from "./components/private-route";
import {theme} from "./styles/theme";
import ResetPassword from "./pages/reset-password/reset-password";
import ChangePasswordLoading from "./pages/reset-password/change-password-loading";
import ChangePasswordForm from "./pages/reset-password/change-password-form";
import InvalidToken from "./pages/reset-password/tokens/invalid-token";
import ExpiredToken from "./pages/reset-password/tokens/expired-token";
import ConsumedToken from "./pages/reset-password/tokens/consumed-token";

const queryClient = new QueryClient();

const App: FC = () => {

  return (
    <BrowserRouter>
      <ThemeProvider theme={theme}>
        <QueryClientProvider client={queryClient}>
          <Navigation/>
          <Box mt={'64px'}>
            <Routes>
              <Route path='/' element={<PrivateRoute/>}>
                <Route path='/' element={<Main/>}/>
              </Route>
              <Route path='/about' element={<PrivateRoute/>}>
                <Route path='/about' element={<About/>}/>
              </Route>
              <Route path="/login" element={<Login/>}/>
              <Route path="/register" element={<Register/>}/>
              <Route path="/reset-password" element={<ResetPassword/>}/>
              <Route path="/change-password" element={<ChangePasswordLoading/>}/>
              <Route path="/change-password/form" element={<ChangePasswordForm/>}/>
              <Route path="/change-password/invalid-token" element={<InvalidToken/>}/>
              <Route path="/change-password/expired-token" element={<ExpiredToken/>}/>
              <Route path="/change-password/consumed-token" element={<ConsumedToken/>}/>
              <Route
                path="*"
                element={<Navigate to="/login"/>}
              />
            </Routes>
          </Box>
        </QueryClientProvider>
      </ThemeProvider>
    </BrowserRouter>
  );
}

export default App;