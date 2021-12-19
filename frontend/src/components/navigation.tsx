import {AppBar, Tab, Tabs} from "@mui/material";
import {FC, useContext, useState} from "react";
import {useNavigate} from "react-router-dom";
import AuthContext, {IAuthContext} from "../context/auth-context";

const Navigation: FC = () => {

  const navigate = useNavigate();
  const [tab, setTab] = useState<number>(0);

  const { auth, setAuth } = useContext<IAuthContext>(AuthContext);

  return (
    <>
      {auth &&
      <AppBar position='static'>
        <Tabs value={tab} onChange={(e, v) => setTab(v)}>
          <Tab label='Start' onClick={() => navigate('/')}/>
          <Tab label='About' onClick={() => navigate('/about')}/>
          <Tab label='Users' onClick={() => navigate('/users')}/>
          <Tab label='Register' onClick={() => navigate('/register')}/>
          {auth.role === 'USER' && <Tab label='User-only' onClick={() => navigate('/register')}/>}
          {auth.role === 'ADMIN' && <Tab label='Admin-only' onClick={() => navigate('/register')}/>}
          <Tab label='Login' onClick={() => navigate('/login')}/>
          <Tab label='Logout' onClick={() => {
            localStorage.clear();
            setAuth(null);
            navigate('/login');
          }}/>
        </Tabs>
      </AppBar>
      }
    </>
  );
}

export default Navigation;