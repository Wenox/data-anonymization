import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { FC, useContext, useState } from 'react';
import { Alert, CircularProgress, Collapse, Divider, IconButton } from '@mui/material';
import * as yup from 'yup';
import { Controller, SubmitHandler, useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { Copyright } from '../components/copyright';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { useNavigate } from 'react-router-dom';
import AuthContext from '../context/auth-context';
import { ACCESS_TOKEN, REFRESH_TOKEN } from '../constants/auth';
import { getMe } from '../api/requests/me/me.requests';
import { postLogin } from '../api/requests/auth/auth.requests';
import { Role } from '../api/requests/shared.types';
import { ROUTES } from '../constants/routes';
import { ExitToApp } from '@mui/icons-material';
import { useTheme } from '@mui/styles';
import { theme } from '../styles/theme';

interface IFormInputs {
  email: string;
  password: string;
}

const schema = yup.object().shape({
  email: yup.string().email().required('E-mail address is required'),
  password: yup.string().min(4).max(20).required(),
});

const Login: FC = () => {
  const theme = useTheme;

  const { setMe } = useContext(AuthContext);

  const {
    handleSubmit,
    control,
    formState: { errors },
  } = useForm<IFormInputs>({ resolver: yupResolver(schema) });

  const navigate = useNavigate();

  const [failedLogin, setFailedLogin] = useState(false);
  const [loading, setLoading] = useState(false);

  const formSubmitHandler: SubmitHandler<IFormInputs> = (data: IFormInputs) => {
    setLoading(true);
    setFailedLogin(false);
    postLogin(data)
      .then(function (response) {
        if (response.status === 200 && response.headers[ACCESS_TOKEN] && response.headers[REFRESH_TOKEN]) {
          localStorage.setItem(ACCESS_TOKEN, response.headers[ACCESS_TOKEN]);
          localStorage.setItem(REFRESH_TOKEN, response.headers[REFRESH_TOKEN]);
        }
      })
      .then(() => {
        getMe().then((response) => {
          if (response.status === 200) {
            setMe(response.data);
          }
          toast.success('Logged in successfully.', {
            position: 'top-right',
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
          if (response.data?.role === Role.UNVERIFIED_USER) {
            toast.success('Verify your account please.', {
              position: 'top-right',
              autoClose: 5000,
              hideProgressBar: false,
              closeOnClick: true,
              pauseOnHover: true,
              draggable: true,
              progress: undefined,
            });
            navigate(ROUTES.VERIFY_MAIL_PROMPT, { state: { email: response.data?.email } });
          } else {
            navigate(ROUTES.USERS);
          }
        });
      })
      .catch(() => {
        toast.error('Logging in failed.', {
          position: 'top-right',
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
  };

  return (
    <Container
      component="main"
      sx={{
        backgroundColor: '#fff',
        border: `1px solid ${theme.palette.primary.main}`,
        boxShadow: `4px 4px 0px ${theme.palette.primary.dark}`,
        borderRadius: '2px',
        pt: 2,
        pb: 3,
        mt: 20,
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
      }}
      maxWidth="xs"
    >
      <Grid container spacing={2} alignItems="center">
        <Grid sx={{ textAlign: 'right' }} item xs={7}>
          <Typography variant="h2" sx={{ alignItems: 'left', mb: 2 }}>
            Sign in
          </Typography>
        </Grid>
        <Grid sx={{ textAlign: 'left' }} item xs={5}>
          <ExitToApp color="secondary" style={{ fontSize: '600%' }} />
        </Grid>
      </Grid>
      <Box component="form" onSubmit={handleSubmit(formSubmitHandler)} noValidate sx={{ mt: 1 }}>
        <Divider sx={{ mb: 3 }} />
        <Controller
          name="email"
          control={control}
          defaultValue=""
          render={({ field }) => (
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
          name="password"
          control={control}
          defaultValue=""
          render={({ field }) => (
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

        <Divider sx={{ mt: 3 }} />

        <Button color="secondary" type="submit" fullWidth variant="contained" sx={{ mt: 3, mb: 2 }}>
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
                <LockOutlinedIcon fontSize="inherit" />
              </IconButton>
            }
          >
            Invalid e-mail address or password
          </Alert>
        </Collapse>
        <Grid container>
          <Grid item xs>
            <Link onClick={() => navigate('/reset-password')} variant="body2">
              Forgot password?
            </Link>
          </Grid>
          <Grid item>
            <Link onClick={() => navigate('/register')} variant="body2">
              {"Don't have an account? Sign Up"}
            </Link>
          </Grid>
        </Grid>
      </Box>
      <Copyright />
      {loading && (
        <CircularProgress
          style={{
            position: 'absolute',
            left: '50%',
            top: '50%',
            transform: 'translate(-50%, -50%)',
          }}
        />
      )}
    </Container>
  );
};

export default Login;
