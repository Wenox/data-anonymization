import {AppBar, Tab, Tabs} from "@mui/material";
import {FC, useContext, useState} from "react";
import {useNavigate} from "react-router-dom";
import AuthContext, {IAuthContext} from "../context/auth-context";
import {Role} from "../api/requests/shared.types";

const Navigation: FC = () => {

  const navigate = useNavigate();
  const [tab, setTab] = useState<number>(0);

  const { me, setMe } = useContext<IAuthContext>(AuthContext);

  return (
    <>
      {me &&
      <AppBar position='static'>
        <Tabs value={tab} onChange={(e, v) => setTab(v)}>
          <Tab label='Start' onClick={() => navigate('/')}/>
          <Tab label='About' onClick={() => navigate('/about')}/>
          <Tab label='Users' onClick={() => navigate('/users')}/>
          <Tab label='Register' onClick={() => navigate('/register')}/>
          {me.role === Role.VERIFIED_USER && <Tab label='User-only' onClick={() => navigate('/register')}/>}
          {me.role === Role.ADMIN && <Tab label='Admin-only' onClick={() => navigate('/register')}/>}
          <Tab label='Login' onClick={() => navigate('/login')}/>
          <Tab label='Logout' onClick={() => {
            localStorage.clear();
            setMe(null);
            navigate('/login');
          }}/>
        </Tabs>
      </AppBar>
      }
    </>
  );
}

export default Navigation;