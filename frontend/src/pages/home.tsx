import { FC, useContext } from 'react';
import AuthContext from '../context/auth-context';

const Home: FC = () => {
  const { me } = useContext(AuthContext);
  return <h1>Hello, {me?.email}!</h1>;
};

export default Home;
