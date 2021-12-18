import {AppBar, Tab, Tabs} from "@mui/material";
import {FC, useState} from "react";
import {useNavigate} from "react-router-dom";

const Navigation: FC = () => {
  const navigate = useNavigate();
  const [tab, setTab] = useState<number>(0);

  const logged = JSON.parse(localStorage.getItem("logged_user")!);

  return (
    <>
      {logged &&
      <AppBar position='static'>
        <Tabs value={tab} onChange={(e, v) => setTab(v)}>
          <Tab label='Start' onClick={() => navigate('/')}/>
          <Tab label='About' onClick={() => navigate('/about')}/>
          <Tab label='Users' onClick={() => navigate('/users')}/>
          <Tab label='Register' onClick={() => navigate('/register')}/>
          {logged.role === 'USER' && <Tab label='User-only' onClick={() => navigate('/register')}/>}
          {logged.role === 'ADMIN' && <Tab label='Admin-only' onClick={() => navigate('/register')}/>}
          <Tab label='Login' onClick={() => navigate('/login')}/>
          <Tab label='Logout' onClick={() => {
            localStorage.clear();
            navigate('/login');
          }}/>
        </Tabs>
      </AppBar>
      }
    </>
  );
}

export default Navigation;