import React from 'react';
import Main from './pages/main';
import {QueryClient, QueryClientProvider} from 'react-query';
import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom";
import About from "./pages/about";
import Login from "./pages/login";
import Register from "./pages/register";
import Navigation from "./components/navigation";

const queryClient = new QueryClient();

function App() {

  // const withLoginRedirect = (element: any) => localStorage.getItem("logged_user") ? element : <Redirect to='/login'/>

  return (
    <BrowserRouter>
      <QueryClientProvider client={queryClient}>


        <Routes>
          <Route path="/" element={<Main/>}/>
          <Route path="/about" element={<About/>}/>
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