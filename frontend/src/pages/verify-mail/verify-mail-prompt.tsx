import { FC } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import Avatar from '@mui/material/Avatar';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import { Box, Container, Divider } from '@mui/material';
import Button from '@mui/material/Button';
import { postVerifyMailSendAgainGivenEmail } from '../../api/requests/verify-mail/verify-mail.requests';
import { toast } from 'react-toastify';
import { theme } from '../../styles/theme';
import Grid from '@mui/material/Grid';
import { Email, LockReset } from '@mui/icons-material';
import { ROUTES } from '../../constants/routes';

const VerifyMailPrompt: FC = () => {
  const { state } = useLocation();
  const navigate = useNavigate();

  const handleResendVerificationMail = () => {
    // @ts-ignore
    postVerifyMailSendAgainGivenEmail(state.email)
      .then((response) => {
        if (response.status === 200)
          toast.success('Verification mail re-sent successfully.', {
            position: 'top-right',
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
        navigate(ROUTES.VERIFY_MAIL_TOKEN_SENT_AGAIN);
      })
      .catch(() => {
        toast.error('Failed to re-send the verification mail.', {
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
      maxWidth="sm"
    >
      <Grid container spacing={2} alignItems="center">
        <Grid sx={{ textAlign: 'right' }} item xs={7}>
          <Typography variant="h2" sx={{ alignItems: 'left', mb: 2, mr: 2 }}>
            Verify account
          </Typography>
        </Grid>
        <Grid sx={{ textAlign: 'left' }} item xs={5}>
          <Email color="secondary" style={{ fontSize: '600%' }} />
        </Grid>
      </Grid>
      <Box sx={{ mt: 1 }}>
        <Divider sx={{ mb: 3 }} />
        <p>Check your email for a link to verify your account.</p>
        <p>If it doesn’t appear within a few minutes, check your spam folder or request a new verification mail.</p>
        <Divider sx={{ mt: 3 }} />
        <Button
          fullWidth
          color="secondary"
          variant="contained"
          sx={{ mt: 3, mb: 0.5 }}
          onClick={() => navigate(ROUTES.LOGIN)}
        >
          Return to sign in
        </Button>
        <Button color="primary" fullWidth variant="outlined" sx={{ mt: 1 }} onClick={handleResendVerificationMail}>
          Re-send verification mail
        </Button>
      </Box>
    </Container>
  );
};

export default VerifyMailPrompt;
