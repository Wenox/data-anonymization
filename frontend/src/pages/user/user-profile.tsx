import { FC, useEffect, useState } from 'react';
import { User } from '../../api/requests/users/users.types';
import { getMyProfile, putEditMyProfile } from '../../api/requests/me/me.requests';
import { toast } from 'react-toastify';
import { Box, Container, Divider, Grid, Skeleton } from '@mui/material';
import { makeStyles } from '@mui/styles';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import { Controller, SubmitHandler, useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import Button from '@mui/material/Button';
import RemoveMyAccountDialog from '../../components/user/remove-my-account-dialog';
import PasswordConfirmationDialog from '../../components/user/password-confirmation-dialog';
import { SKELETON_TIMEOUT } from '../../constants/timeouts';

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
  buttonDark: {
    backgroundColor: '#212121',
    '&:hover': {
      backgroundColor: '#000000',
    },
  },
});

const UserProfile: FC = () => {
  const [user, setUser] = useState<User>();
  const [isLoading, setIsLoading] = useState(true);
  const [isRemoveAccountInitialDialogOpen, setIsRemoveAccountInitialDialogOpen] = useState(false);
  const [isRemoveAccountFinalDialogOpen, setIsRemoveAccountFinalDialogOpen] = useState(false);

  const handleOpenRemoveInitialAccountDialog = () => {
    setIsRemoveAccountInitialDialogOpen(true);
  };

  const handleCloseRemoveInitialAccountDialog = () => {
    setIsRemoveAccountInitialDialogOpen(false);
  };

  const handleOpenRemoveFinalAccountDialog = () => {
    setIsRemoveAccountFinalDialogOpen(true);
  };

  const handleCloseRemoveFinalAccountDialog = () => {
    setIsRemoveAccountFinalDialogOpen(false);
  };

  const classes = useStyles();

  const {
    handleSubmit,
    control,
    formState: { errors },
  } = useForm<IFormInputs>({ resolver: yupResolver(schema) });

  const fetchData = () => {
    getMyProfile()
      .then((response) => {
        setTimeout(() => {
          setUser(response.data);
          setIsLoading(false);
          toast.success('Profile loaded successfully.', {
            position: 'top-right',
            autoClose: 3000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
        }, SKELETON_TIMEOUT);
      })
      .catch((err) => {
        toast.error('Failed to load the profile.', {
          position: 'top-right',
          autoClose: 3000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
        setIsLoading(false);
      });
  };

  useEffect(() => {
    fetchData();
  }, []);

  const formSubmitHandler: SubmitHandler<IFormInputs> = (data: IFormInputs) => {
    putEditMyProfile(data)
      .then((response) => {
        if (response.status === 200)
          toast.success(response.data.message, {
            position: 'top-right',
            autoClose: 3000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
      })
      .catch(() => {
        toast.error('Failed to edit the profile.', {
          position: 'top-right',
          autoClose: 3000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      });
  };

  return (
    <Container
      component="main"
      sx={{
        backgroundColor: '#fff',
        border: '1px solid #212121',
        pt: 2,
        pb: 3,
        borderRadius: '2px',
        boxShadow: '4px 4px 0px #000000',
      }}
    >
      <Typography color="secondary" variant="h4" sx={{ mb: 2 }}>
        User Profile
      </Typography>
      {isRemoveAccountInitialDialogOpen && (
        <RemoveMyAccountDialog
          open={isRemoveAccountInitialDialogOpen}
          handleConfirm={() => {
            handleOpenRemoveFinalAccountDialog();
            handleCloseRemoveInitialAccountDialog();
          }}
          handleClose={handleCloseRemoveInitialAccountDialog}
        />
      )}

      {isRemoveAccountFinalDialogOpen && (
        <PasswordConfirmationDialog
          open={isRemoveAccountFinalDialogOpen}
          handleClose={handleCloseRemoveFinalAccountDialog}
        />
      )}

      <Divider sx={{ mb: 3 }} />

      <Box component="form" onSubmit={handleSubmit(formSubmitHandler)} noValidate sx={{ mt: 1 }}>
        <Grid container spacing={3}>
          {isLoading ? (
            <>
              <Grid item xs={6}>
                <Skeleton variant="rectangular" height={60} sx={{ animationDuration: '0.6s', animationDelay: '0s' }} />
              </Grid>
              <Grid item xs={6}>
                <Skeleton variant="rectangular" height={60} sx={{ animationDuration: '0.6s', animationDelay: '0s' }} />
              </Grid>
              <Grid item xs={12}>
                <Skeleton variant="rectangular" height={60} sx={{ animationDuration: '0.6s', animationDelay: '0s' }} />
              </Grid>
              <Grid item xs={12}>
                <Skeleton variant="rectangular" height={68} sx={{ animationDuration: '0.6s', animationDelay: '0s' }} />
              </Grid>
            </>
          ) : (
            <>
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
                      minRows={2}
                      maxRows={2}
                      fullWidth
                      id="purpose"
                      label="Purpose"
                      error={!!errors.purpose}
                      helperText={errors.purpose ? errors.purpose?.message : ''}
                    />
                  )}
                />
              </Grid>
            </>
          )}
          <Grid item xs={12}>
            <Divider />
            <Button type="submit" className={classes.buttonDark} fullWidth variant="contained" sx={{ mt: 3, mb: -1 }}>
              Save changes
            </Button>
          </Grid>
          <Grid item xs={12}>
            <Button
              onClick={handleOpenRemoveInitialAccountDialog}
              fullWidth
              variant="contained"
              className={classes.button}
            >
              Delete account
            </Button>
          </Grid>
        </Grid>
      </Box>
    </Container>
  );
};

export default UserProfile;
