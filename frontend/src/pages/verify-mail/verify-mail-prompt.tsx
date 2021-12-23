import { FC } from 'react';
import { useNavigate } from 'react-router-dom';
import Avatar from '@mui/material/Avatar';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import { Box, Container } from '@mui/material';
import Button from '@mui/material/Button';

const VerifyMailPrompt: FC = () => {
  const navigate = useNavigate();

  return (
    <Container
      component="main"
      sx={{
        border: '1px solid #000000',
        boxShadow: '6px 6px 0px #00bfff',
        backgroundColor: 'white',
        mt: 20,
        paddingTop: 8,
        paddingBottom: 2,
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
      }}
      maxWidth="xs"
    >
      <Avatar sx={{ m: 1, bgcolor: 'primary.main' }}>
        <LockOutlinedIcon />
      </Avatar>
      <Typography component="h1" variant="h4">
        Verify your account
      </Typography>
      <Box sx={{ mt: 1 }}>
        <p>Check your email for a link to verify your account.</p>
        <p>If it doesn’t appear within a few minutes, check your spam folder or request a new verification mail.</p>
        <Button fullWidth variant="contained" sx={{ mt: 3 }} onClick={() => navigate('/login')}>
          Return to sign in
        </Button>
        <Button fullWidth variant="outlined" sx={{ mt: 1, mb: 2 }} onClick={() => navigate('/login')}>
          Re-send verification mail
        </Button>
      </Box>
    </Container>
  );
};

export default VerifyMailPrompt;
