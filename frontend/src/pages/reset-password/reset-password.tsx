import { FC, useState } from 'react';
import * as yup from 'yup';
import { Controller, SubmitHandler, useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';
import Avatar from '@mui/material/Avatar';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import { Box, Container, Divider } from '@mui/material';
import Button from '@mui/material/Button';
import { postRequestResetPassword } from '../../api/requests/reset-password/reset-password.requests';
import { theme } from '../../styles/theme';
import Grid from '@mui/material/Grid';
import { ExitToApp, LockReset, Password } from '@mui/icons-material';
import { ROUTES } from '../../constants/routes';

interface IFormInputs {
  email: string;
}

const schema = yup.object().shape({
  email: yup.string().email().required('E-mail address is required'),
});

const ResetPassword: FC = () => {
  const {
    handleSubmit,
    control,
    formState: { errors },
  } = useForm<IFormInputs>({ resolver: yupResolver(schema) });

  const navigate = useNavigate();

  const [buttonPressed, setButtonPressed] = useState(false);

  const formSubmitHandler: SubmitHandler<IFormInputs> = (data: IFormInputs) => {
    setButtonPressed(true);
    postRequestResetPassword(data.email);
    toast.success('E-mail was sent to you.', {
      position: 'top-right',
      autoClose: 5000,
      hideProgressBar: false,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true,
      progress: undefined,
    });
  };
  return (
    <>
      {!buttonPressed ? (
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
          maxWidth="sm"
        >
          <Grid container spacing={2} alignItems="center">
            <Grid sx={{ textAlign: 'right' }} item xs={7}>
              <Typography variant="h2" sx={{ alignItems: 'left', mb: 2, mr: 2 }}>
                Reset password
              </Typography>
            </Grid>
            <Grid sx={{ textAlign: 'left' }} item xs={5}>
              <LockReset color="secondary" style={{ fontSize: '600%' }} />
            </Grid>
          </Grid>
          <Box component="form" onSubmit={handleSubmit(formSubmitHandler)} noValidate sx={{ mt: 1 }}>
            <Divider sx={{ mb: 3 }} />
            <p>Enter your account&#39;s email address and we will send you a password reset link.</p>
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
            <Divider sx={{ mt: 3 }} />

            <Button color="secondary" type="submit" fullWidth variant="contained" sx={{ mt: 3, mb: 0.5 }}>
              Send password reset email
            </Button>

            <Button color="primary" onClick={() => navigate(ROUTES.LOGIN)} fullWidth variant="outlined" sx={{ mt: 1 }}>
              Return to sign in
            </Button>
          </Box>
        </Container>
      ) : (
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
          maxWidth="sm"
        >
          <Grid container spacing={2} alignItems="center">
            <Grid sx={{ textAlign: 'right' }} item xs={7}>
              <Typography variant="h2" sx={{ alignItems: 'left', mb: 2, mr: 2 }}>
                Reset password
              </Typography>
            </Grid>
            <Grid sx={{ textAlign: 'left' }} item xs={5}>
              <LockReset color="success" style={{ fontSize: '600%' }} />
            </Grid>
          </Grid>
          <Box sx={{ mt: 1 }}>
            <Divider sx={{ mb: 2 }} />
            <p>
              Check your email for a link to reset your password. If it doesn’t appear within a few minutes, check your
              spam folder.
            </p>
            <Divider sx={{ mt: 0.5, mb: 0 }} />
            <Button fullWidth color="secondary" variant="contained" sx={{ mt: 3 }} onClick={() => navigate('/login')}>
              Return to sign in
            </Button>
          </Box>
        </Container>
      )}
    </>
  );
};

export default ResetPassword;
