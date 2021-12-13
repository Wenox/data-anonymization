import {AppBar, Tab, Tabs} from "@mui/material";
import {FC, useState} from "react";
import {useNavigate} from "react-router-dom";

const Navigation: FC = () => {
  const navigate = useNavigate();
  const [tab, setTab] = useState<number>(0);

  // return (
  //   <>
  //     <nav>
  //       <h1>The navigation</h1>
  //       <ul>
  //         <Link to='/about'>
  //           <li>About</li>
  //         </Link>
  //         <Link to='/register'>
  //           <li>Register</li>
  //         </Link>
  //         <Link to='/login'>
  //           <li>Login</li>
  //         </Link>
  //       </ul>
  //     </nav>
  //   </>
  // )

  return (
    <AppBar position='static'>
      <Tabs value={tab} onChange={(e, v) => setTab(v)}>
        <Tab label='Start' onClick={() => navigate('/')}/>
        <Tab label='About' onClick={() => navigate('/about')}/>
        <Tab label='Register' onClick={() => navigate('/register')}/>
        <Tab label='Login' onClick={() => navigate('/login')}/>
        <Tab label='Logout' onClick={() => {}}/>
      </Tabs>
    </AppBar>
  );
}

export default Navigation;