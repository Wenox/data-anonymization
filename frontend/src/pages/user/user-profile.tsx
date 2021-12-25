import { FC, useEffect, useState } from 'react';
import { User } from '../../api/requests/users/users.types';
import { getMyProfile } from '../../api/requests/me/me.requests';
import { toast } from 'react-toastify';
import { Box, Container, Divider, Grid } from '@mui/material';
import { makeStyles } from '@mui/styles';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import { Controller, SubmitHandler, useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import Button from '@mui/material/Button';

interface IFormInputs {
  email: string;
  firstName: string;
  lastName: string;
  purpose: string;
}

const schema = yup.object().shape({
  email: yup.string().email().required('E-mail address is required'),
  firstName: yup.string().required('First name is required'),
  lastName: yup.string().required('Last name is required'),
  purpose: yup.string().required('Please explain the purpose'),
});

const useStyles = makeStyles({
  button: {
    backgroundColor: 'red',
    '&:hover': {
      backgroundColor: '#dd0000',
    },
  },
});

const UserProfile: FC = () => {
  const [user, setUser] = useState<User>();
  const [isLoading, setIsLoading] = useState(true);

  const classes = useStyles();

  const {
    handleSubmit,
    control,
    formState: { errors },
  } = useForm<IFormInputs>({ resolver: yupResolver(schema) });

  const fetchData = () => {
    getMyProfile()
      .then((response) => {
        setUser(response.data);
        toast.success('Profile loaded successfully.', {
          position: 'top-right',
          autoClose: 3000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      })
      .catch((err) => {
        toast.success('Failed to load the profile.', {
          position: 'top-right',
          autoClose: 3000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      })
      .finally(() => setIsLoading(false));
  };

  useEffect(() => {
    fetchData();
  }, []);

  const formSubmitHandler: SubmitHandler<IFormInputs> = (data: IFormInputs) => {
    console.log('edit my profile data: ', data);
    // postRegisterUser(data as RegisterUserRequest)
    //   .then((response) => {
    //     if (response.status === 200)
    //       toast.success('Signed up successfully.', {
    //         position: 'top-right',
    //         autoClose: 5000,
    //         hideProgressBar: false,
    //         closeOnClick: true,
    //         pauseOnHover: true,
    //         draggable: true,
    //         progress: undefined,
    //       });
    //     navigate('/verify-mail-prompt', { state: { email: data.email } });
    //   })
    //   .catch(() => {
    //     toast.error('Failed to sign up.', {
    //       position: 'top-right',
    //       autoClose: 5000,
    //       hideProgressBar: false,
    //       closeOnClick: true,
    //       pauseOnHover: true,
    //       draggable: true,
    //       progress: undefined,
    //     });
    //   });
  };

  return (
    <Container
      component="main"
      sx={{
        border: '1px solid #000000',
        boxShadow: '6px 6px 0px #00bfff',
        backgroundColor: 'white',
        mt: 4,
        display: 'flex',
        flexDirection: 'column',
      }}
      maxWidth="md"
    >
      <Typography sx={{ pt: 3, pb: 3 }} component="h3" variant="h3">
        User profile
      </Typography>

      <Divider sx={{ mb: 3 }} />

      {isLoading ? (
        <h1>Loading...</h1>
      ) : (
        <Box component="form" onSubmit={handleSubmit(formSubmitHandler)} noValidate sx={{ mt: 1 }}>
          <Grid container spacing={3}>
            <Grid item xs={6}>
              <Controller
                name="firstName"
                control={control}
                defaultValue={user?.firstName}
                render={({ field }) => (
                  <TextField
                    {...field}
                    fullWidth
                    id="firstName"
                    label="First name"
                    defaultValue={user?.firstName}
                    error={!!errors.firstName}
                    helperText={errors.firstName ? errors.firstName?.message : ''}
                  />
                )}
              />
            </Grid>
            <Grid item xs={6}>
              <Controller
                name="lastName"
                control={control}
                defaultValue={user?.lastName}
                render={({ field }) => (
                  <TextField
                    {...field}
                    fullWidth
                    id="lastName"
                    label="Last name"
                    defaultValue={user?.lastName}
                    error={!!errors.lastName}
                    helperText={errors.lastName ? errors.lastName?.message : ''}
                  />
                )}
              />
            </Grid>
            <Grid item xs={12}>
              <Controller
                name="email"
                control={control}
                defaultValue={user?.email}
                render={({ field }) => (
                  <TextField
                    {...field}
                    fullWidth
                    id="email"
                    label="E-mail address"
                    defaultValue={user?.email}
                    error={!!errors.email}
                    helperText={errors.email ? errors.email?.message : ''}
                  />
                )}
              />
            </Grid>
            <Grid item xs={12}>
              <Controller
                name="purpose"
                control={control}
                defaultValue={user?.purpose}
                render={({ field }) => (
                  <TextField
                    {...field}
                    multiline
                    rows={2}
                    maxRows={2}
                    fullWidth
                    id="purpose"
                    label="Purpose"
                    defaultValue={user?.purpose}
                    error={!!errors.purpose}
                    helperText={errors.purpose ? errors.purpose?.message : ''}
                  />
                )}
              />
            </Grid>
            <Grid item xs={12}>
              <Divider />
              <Button type="submit" fullWidth variant="contained" sx={{ mt: 3, mb: -1 }}>
                Save changes
              </Button>
            </Grid>
            <Grid item xs={12}>
              <Button type="submit" fullWidth variant="contained" className={classes.button} sx={{ mb: 4 }}>
                Delete account
              </Button>
            </Grid>
          </Grid>
        </Box>
      )}
    </Container>
  );
};

export default UserProfile;
