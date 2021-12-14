import React from 'react';
import Main from './pages/main';
import {QueryClient, QueryClientProvider} from 'react-query';
import {BrowserRouter, Navigate, Outlet, Route, Routes} from "react-router-dom";
import About from "./pages/about";
import Login from "./pages/login";
import Register from "./pages/register";
import Navigation from "./components/navigation";
import {IndexRouteProps, LayoutRouteProps, PathRouteProps} from "react-router";

const queryClient = new QueryClient();

const PrivateRoute = () => {
  const auth = localStorage.getItem('logged_user') !== null;
  return auth ? <Outlet /> : <Navigate to="/login" />;
}

function App() {

  const isLoggedIn: boolean = localStorage.getItem('logged_user') !== null;

  return (
    <BrowserRouter>
      <QueryClientProvider client={queryClient}>


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
            element={<Navigate to="/login" />}
          />
        </Routes>
        <Navigation />


      </QueryClientProvider>
    </BrowserRouter>
  );
}

export default App;