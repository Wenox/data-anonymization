import React, { FC, useContext, useEffect, useState } from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import { Navigate, Route, Routes } from 'react-router-dom';
import { APP_ROUTES } from '../constants/routes';
import { Container } from '@mui/material';
import { getMe } from '../api/requests/me/me.requests';
import AuthContext from '../context/auth-context';
import { getAppRoute } from '../components/route/app-route';

const Main: FC = () => {
  const { setMe } = useContext(AuthContext);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    getMe()
      .then((response) => {
        setMe(response.data);
        setIsLoading(false);
      })
      .catch(() => setIsLoading(false));
  }, [setMe]);

  return (
    <>
      {!isLoading && (
        <>
          <CssBaseline />
          <Routes>
            {APP_ROUTES.map((description) => getAppRoute(description))}
            <Route path="*" element={<Navigate to="/login" />} />
          </Routes>
        </>
      )}
    </>
  );
};

export default Main;
