import { Box, Container, Divider } from '@mui/material';
import Avatar from '@mui/material/Avatar';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import { useNavigate } from 'react-router-dom';
import { theme } from '../styles/theme';
import { ROUTES } from '../constants/routes';
import Grid from '@mui/material/Grid';
import { GppBad, GppMaybe, LockReset, Mail, MarkEmailRead, VerifiedUser } from '@mui/icons-material';

interface Props {
  title: string;
  content: string;
  buttonTitle?: string;
  customOnClick?: boolean;
  handleOnClick?: () => void;
  type?: string;
  smaller?: boolean;
}

const TokenResult = ({
  title,
  content,
  buttonTitle = 'Return to sign in',
  customOnClick = false,
  handleOnClick,
  type = 'LOCK',
  smaller = false,
}: Props) => {
  const navigate = useNavigate();

  const handleGetIconForType = () => {
    switch (type) {
      case 'LOCK':
        return <LockOutlinedIcon color="secondary" style={{ fontSize: '600%' }} />;
      case 'MAIL_RESENT':
        return <Mail color="success" style={{ fontSize: '600%' }} />;
      case 'MAIL_SUCCESS':
        return <MarkEmailRead color="success" style={{ fontSize: '600%' }} />;
      case 'MAIL_ALREADY_VERIFIED':
        return <VerifiedUser color="success" style={{ fontSize: '600%' }} />;
      case 'MAIL_INVALID_TOKEN':
        return <GppBad color="error" style={{ fontSize: '600%' }} />;
      case 'PASSWORD_INVALID_TOKEN':
      case 'PASSWORD_EXPIRED_TOKEN':
        return <GppBad color="error" style={{ fontSize: '800%' }} />;
      case 'PASSWORD_CONSUMED_TOKEN':
        return <GppMaybe color="error" style={{ fontSize: '800%' }} />;
    }
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
      maxWidth={smaller ? 'xs' : 'sm'}
    >
      <Grid container spacing={2} alignItems="center">
        <Grid sx={{ textAlign: 'right' }} item xs={8}>
          <Typography variant="h2" sx={{ alignItems: 'right', mb: 2, mr: 2 }}>
            {title}
          </Typography>
        </Grid>
        <Grid sx={{ textAlign: 'left' }} item xs={4}>
          {handleGetIconForType()}
        </Grid>
      </Grid>
      <Box sx={{ mt: 1 }}>
        <Divider sx={{ mb: 2 }} />
        <p>{content}</p>
        <Divider sx={{ mt: 0.5, mb: 0 }} />
        <Button
          fullWidth
          color="secondary"
          variant="contained"
          sx={{ mt: 3, mb: 1 }}
          onClick={customOnClick ? handleOnClick : () => navigate(ROUTES.LOGIN)}
        >
          {buttonTitle}
        </Button>
      </Box>
    </Container>
  );
};

export default TokenResult;
