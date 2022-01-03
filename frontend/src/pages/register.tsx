import * as yup from 'yup';
import { FC, useState } from 'react';
import { Controller, SubmitHandler, useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import Container from '@mui/material/Container';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Grid from '@mui/material/Grid';
import Link from '@mui/material/Link';
import { Copyright } from '../components/copyright';
import { postRegisterUser } from '../api/requests/users/users.requests';
import { RegisterUserRequest } from '../api/requests/users/users.types';
import { Alert, Collapse, Divider, IconButton } from '@mui/material';
import { ROUTES } from '../constants/routes';
import { theme } from '../styles/theme';
import { AppRegistration } from '@mui/icons-material';

interface IFormInputs {
  email: string;
  password: string;
  confirmPassword: string;
  firstName: string;
  lastName: string;
  purpose: string;
}

const schema = yup.object().shape({
  email: yup.string().email().required('E-mail address is required'),
  password: yup.string().min(4).max(20).required(),
  confirmPassword: yup.string().oneOf([yup.ref('password'), null], 'Passwords must match'),
  firstName: yup.string().required('First name is required'),
  lastName: yup.string().required('Last name is required'),
  purpose: yup.string().required('Please explain the purpose'),
});

const Register: FC = () => {
  const {
    handleSubmit,
    control,
    formState: { errors },
  } = useForm<IFormInputs>({ resolver: yupResolver(schema) });

  const navigate = useNavigate();

  const [failedLogin, setFailedLogin] = useState(false);

  const formSubmitHandler: SubmitHandler<IFormInputs> = (data: IFormInputs) => {
    console.log('login data: ', data);
    setFailedLogin(false);
    postRegisterUser(data as RegisterUserRequest)
      .then((response) => {
        if (response.status === 200)
          toast.success('Signed up successfully.', {
            position: 'top-right',
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
        navigate('/verify-mail-prompt', { state: { email: data.email } });
      })
      .catch(() => {
        toast.error('Failed to sign up.', {
          position: 'top-right',
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
        setFailedLogin(true);
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
        mt: 4,
        pt: 3,
        pb: 2,
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
      }}
      maxWidth={'sm'}
    >
      <Grid container spacing={2} alignItems="center">
        <Grid sx={{ textAlign: 'right' }} item xs={7}>
          <Typography variant="h2" sx={{ alignItems: 'left', mb: 2 }}>
            Sign up
          </Typography>
        </Grid>
        <Grid sx={{ textAlign: 'left' }} item xs={5}>
          <AppRegistration color="secondary" style={{ fontSize: '600%' }} />
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

        <Controller
          name="confirmPassword"
          control={control}
          defaultValue=""
          render={({ field }) => (
            <TextField
              {...field}
              variant="outlined"
              error={!!errors.confirmPassword}
              helperText={errors.confirmPassword ? errors.confirmPassword?.message : ''}
              margin="normal"
              required
              fullWidth
              name="confirmPassword"
              label="Confirm Password"
              type="password"
              id="confirmPassword"
              autoComplete="current-password"
            />
          )}
        />

        <Controller
          name="firstName"
          control={control}
          defaultValue=""
          render={({ field }) => (
            <TextField
              {...field}
              variant="outlined"
              error={!!errors.firstName}
              helperText={errors.firstName ? errors.firstName?.message : ''}
              margin="normal"
              required
              fullWidth
              name="firstName"
              label="First name"
              type="text"
              id="firstName"
            />
          )}
        />

        <Controller
          name="lastName"
          control={control}
          defaultValue=""
          render={({ field }) => (
            <TextField
              {...field}
              variant="outlined"
              error={!!errors.lastName}
              helperText={errors.lastName ? errors.lastName?.message : ''}
              margin="normal"
              required
              fullWidth
              name="lastName"
              label="Last name"
              type="text"
              id="lastName"
            />
          )}
        />

        <Controller
          name="purpose"
          control={control}
          defaultValue=""
          render={({ field }) => (
            <TextField
              {...field}
              variant="outlined"
              error={!!errors.purpose}
              helperText={errors.purpose ? errors.purpose?.message : ''}
              margin="normal"
              required
              fullWidth
              name="purpose"
              label="Purpose"
              type="text"
              id="purpose"
            />
          )}
        />
        <Divider sx={{ mt: 3 }} />

        <Button type="submit" fullWidth color="secondary" variant="contained" sx={{ mt: 3, mb: 2 }}>
          Sign up
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
            This e-mail address is already taken
          </Alert>
        </Collapse>
        <Grid container>
          <Grid item>
            <Link onClick={() => navigate(ROUTES.LOGIN)} variant="body2">
              {'Already have an account? Sign In'}
            </Link>
          </Grid>
        </Grid>
      </Box>
      <Copyright />
    </Container>
  );
};

export default Register;
