import * as yup from 'yup';
import { FC } from 'react';
import { Controller, SubmitHandler, useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { toast } from 'react-toastify';
import Container from '@mui/material/Container';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Grid from '@mui/material/Grid';
import { postChangePassword } from '../../api/requests/reset-password/reset-password.requests';
import { theme } from '../../styles/theme';
import { Password } from '@mui/icons-material';
import { Divider } from '@mui/material';
import { ROUTES } from '../../constants/routes';

interface IFormInputs {
  password: string;
  confirmPassword: string;
}

const schema = yup.object().shape({
  password: yup.string().min(4).max(20).required(),
  confirmPassword: yup.string().oneOf([yup.ref('password'), null], 'Passwords must match'),
});

const ChangePasswordForm: FC = () => {
  const [searchParams] = useSearchParams();

  const token: string = searchParams.get('token') ?? '';

  const {
    handleSubmit,
    control,
    formState: { errors },
  } = useForm<IFormInputs>({ resolver: yupResolver(schema) });

  const navigate = useNavigate();

  const formSubmitHandler: SubmitHandler<IFormInputs> = (data: IFormInputs) => {
    postChangePassword({ newPassword: data.password, token: token })
      .then((response) => {
        if (response.status === 200) {
          if (response.data === 'success') {
            toast.success('Changed password successfully.', {
              position: 'top-right',
              autoClose: 5000,
              hideProgressBar: false,
              closeOnClick: true,
              pauseOnHover: true,
              draggable: true,
              progress: undefined,
            });
            navigate(ROUTES.LOGIN);
          } else {
            toast.error('Failed to change password.', {
              position: 'top-right',
              autoClose: 5000,
              hideProgressBar: false,
              closeOnClick: true,
              pauseOnHover: true,
              draggable: true,
              progress: undefined,
            });
            navigate(`/change-password/${response.data}`);
          }
        }
      })
      .catch(() => {
        toast.error('Failed to change password.', {
          position: 'top-right',
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
        navigate(ROUTES.LOGIN);
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
          <Typography color="primary" fontWeight={'300'} variant="h3" sx={{ alignItems: 'left', mb: 2 }}>
            Set new password
          </Typography>
        </Grid>
        <Grid sx={{ textAlign: 'left' }} item xs={5}>
          <Password color="secondary" style={{ fontSize: '600%' }} />
        </Grid>
      </Grid>
      <Box component="form" onSubmit={handleSubmit(formSubmitHandler)} noValidate sx={{ mt: 1 }}>
        <Divider sx={{ mb: 3 }} />
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
              type="type"
              id="confirmPassword"
              autoComplete="current-password"
            />
          )}
        />

        <Divider sx={{ mt: 3 }} />

        <Button color="secondary" type="submit" fullWidth variant="contained" sx={{ mt: 3, mb: 0.5 }}>
          Confirm
        </Button>
        <Button color="primary" fullWidth variant="outlined" sx={{ mt: 1 }} onClick={() => navigate(ROUTES.LOGIN)}>
          Return to login
        </Button>
      </Box>
    </Container>
  );
};

export default ChangePasswordForm;
