import React, {FC} from 'react';
import Main from './pages/main';
import {QueryClient, QueryClientProvider} from 'react-query';
import {BrowserRouter, Navigate, Outlet, Route, Routes, useLocation} from "react-router-dom";
import About from "./pages/about";
import Login from "./pages/login";
import Register from "./pages/register";
import Navigation from "./components/navigation";
import {Box, ThemeProvider} from "@mui/material";
import PrivateRoute from "./components/private-route";
import {theme} from "./styles/theme";

const queryClient = new QueryClient();

const App: FC = () => {

  const isLoggedIn: boolean = localStorage.getItem('logged_user') !== null;

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