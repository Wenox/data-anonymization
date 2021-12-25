import { FC, useContext } from 'react';
import AuthContext from '../context/auth-context';
import { useNavigate } from 'react-router-dom';
import { ROUTES } from '../constants/routes';

const Logout: FC = () => {
  const { setMe } = useContext(AuthContext);
  const navigate = useNavigate();
  localStorage.clear();
  setMe(null);
  navigate(ROUTES.LOGIN);
  return <h1>Logging out...</h1>;
};

export default Logout;
