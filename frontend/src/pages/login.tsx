import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import {getMe, login} from "../api/auth";
import {FC, useState} from "react";
import {Alert, CircularProgress, Collapse, IconButton} from "@mui/material";
import * as yup from 'yup';
import {SubmitHandler, useForm, Controller} from "react-hook-form";
import {yupResolver} from "@hookform/resolvers/yup";
import {Copyright} from "../components/copyright";
import {toast} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import {useNavigate} from "react-router-dom";

interface IFormInputs {
  email: string;
  password: string;
}

const schema = yup.object().shape({
  email: yup.string().email().required("E-mail address is required"),
  password: yup.string().min(4).max(20).required(),
});

const theme = createTheme();

const Login: FC = () => {

  localStorage.removeItem('logged_user');

  const {
    handleSubmit,
    control,
    formState: {errors},
  } = useForm<IFormInputs>({resolver: yupResolver(schema)})

  const navigate = useNavigate();

  const [failedLogin, setFailedLogin] = useState(false);
  const [loading, setLoading] = useState(false);

  const formSubmitHandler: SubmitHandler<IFormInputs> = (data: IFormInputs) => {
    console.log('login data: ', data);
    setLoading(true);
    setFailedLogin(false);
    login(data)
      .then(function (response) {
        if (response.status === 200 && response.headers['access_token'] && response.headers['refresh_token']) {
          localStorage.setItem('access_token', response.headers['access_token']);
          localStorage.setItem('refresh_token', response.headers['refresh_token']);
          console.log('local storage was set.');
        }
      })
      .then(response => {
          getMe().then(response => {
            if (response.status === 200) {
              localStorage.setItem('logged_user', JSON.stringify(response.data));
            }
            toast.success('Logged in successfully.', {
              position: "top-right",
              autoClose: 5000,
              hideProgressBar: false,
              closeOnClick: true,
              pauseOnHover: true,
              draggable: true,
              progress: undefined,
            });
            navigate('/')
          })
        }
      )
      .catch(err => {
        toast.error('Logging in failed.', {
          position: "top-right",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
        setFailedLogin(true);
      })
      .then(() => {
        setLoading(false);
      });
  }

  return (
    <ThemeProvider theme={theme}>
      <Container component="main" sx={{
        border: '1px solid #000000',
        boxShadow: '6px 6px 0px #00bfff',
        backgroundColor: 'white',
      }} maxWidth="xs">
        <Box
          sx={{
            mt: 12,
            mb: 12,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          <Avatar sx={{m: 1, bgcolor: '#17bf00'}}>
            <LockOutlinedIcon/>
          </Avatar>
          <Typography component="h1" variant="h2">
            Sign in
          </Typography>
          <Box component="form" onSubmit={handleSubmit(formSubmitHandler)} noValidate
               sx={{mt: 1}}>
            <Controller
              name='email'
              control={control}
              defaultValue='mail@mail.com'
              render={({field}) => (
                <TextField
                  {...field}
                  label="E-mail address"
                  variant="outlined"
                  error={!!errors.email}
                  helperText={errors.email ? errors.email?.message : ''}
                  margin="normal"
                  required
                  fullWidth
                  id="email"
                  name="email"
                  autoComplete="email"
                  autoFocus
                />
              )}
            />

            <Controller
              name='password'
              control={control}
              defaultValue=''
              render={({field}) => (
                <TextField
                  {...field}
                  variant="outlined"
                  error={!!errors.password}
                  helperText={errors.password ? errors.password?.message : ''}
                  margin="normal"
                  required
                  fullWidth
                  name="password"
                  label="Password"
                  type="password"
                  id="password"
                  autoComplete="current-password"
                />
              )}
            />

            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{mt: 3, mb: 2}}
            >
              Login
            </Button>
            <Collapse in={failedLogin}>
              <Alert
                severity="error"
                action={
                  <IconButton
                    aria-label="close"
                    color="inherit"
                    size="small"
                    onClick={() => {
                      setFailedLogin(false);
                    }}
                  >
                    <LockOutlinedIcon fontSize="inherit"/>
                  </IconButton>
                }
              >
                Invalid e-mail address or password
              </Alert>
            </Collapse>
            <Grid container>
              <Grid item xs>
                <Link href="reset-password" variant="body2">
                  Forgot password?
                </Link>
              </Grid>
              <Grid item>
                <Link href="register" variant="body2">
                  {"Don't have an account? Sign Up"}
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
        <Copyright/>
        {loading && <CircularProgress style={{
          position: 'absolute', left: '50%', top: '50%',
          transform: 'translate(-50%, -50%)'
        }}/>}
      </Container>
    </ThemeProvider>
  );
}

export default Login;
