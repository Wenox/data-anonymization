import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import {login} from "../api/auth";
import {FC, useState} from "react";
import {Alert, Collapse, IconButton} from "@mui/material";
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
  const {
    handleSubmit,
    control,
    formState: {errors},
  } = useForm<IFormInputs>({ resolver: yupResolver(schema) })

  const navigate = useNavigate();

  const [failedLogin, setFailedLogin] = useState(false);

  const formSubmitHandler: SubmitHandler<IFormInputs> = (data: IFormInputs) => {
    console.log('login data: ', data);
    setFailedLogin(false);
    login(data).then(response => {
      toast.success('Logged in successfully.', {
        position: "top-right",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });
      navigate('/register')
    }).catch(err => {
      toast.error('Logging in failed.', {
        position: "top-right",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });
      setFailedLogin(true)
    });
  }

  return (
    <ThemeProvider theme={theme}>
      <Container component="main" sx={{
        border: '1px solid #d6daff',
        boxShadow: '5px 5px 12px #d6daff'
      }} maxWidth="xs">
        <CssBaseline/>
        <Box
          sx={{
            marginTop: 8,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          <Avatar sx={{m: 1, bgcolor: 'secondary.main'}}>
            <LockOutlinedIcon/>
          </Avatar>
          <Typography component="h1" variant="h2">
            Sign in
          </Typography>
          <Box component="form" onSubmit={handleSubmit(formSubmitHandler)} noValidate sx={{mt: 1}}>
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
                <Link href="forgot-password" variant="body2">
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
      </Container>
    </ThemeProvider>
  );
}

export default Login;
