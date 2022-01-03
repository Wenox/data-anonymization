import { Box, Container, Divider } from '@mui/material';
import Avatar from '@mui/material/Avatar';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import { useNavigate } from 'react-router-dom';
import { theme } from '../styles/theme';
import { ROUTES } from '../constants/routes';

interface Props {
  title: string;
  content: string;
  buttonTitle?: string;
  customOnClick?: boolean;
  handleOnClick?: () => void;
}

const TokenResult = ({
  title,
  content,
  buttonTitle = 'Return to sign in',
  customOnClick = false,
  handleOnClick,
}: Props) => {
  const navigate = useNavigate();

  return (
    <Container
      component="main"
      sx={{
        backgroundColor: '#fff',
        border: `1px solid ${theme.palette.primary.main}`,
        boxShadow: `4px 4px 0px ${theme.palette.primary.dark}`,
        borderRadius: '2px',
        mt: 20,
        pt: 3,
        pb: 2,
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
      }}
      maxWidth={'sm'}
    >
      <Avatar sx={{ m: 1, backgroundColor: `${theme.palette.primary.light}` }}>
        <LockOutlinedIcon />
      </Avatar>
      <Typography color="primary" component="h1" variant="h4">
        {title}
      </Typography>
      <p>{content}</p>
      <Button
        fullWidth
        color="secondary"
        variant="contained"
        sx={{ mt: 3, mb: 1 }}
        onClick={customOnClick ? handleOnClick : () => navigate(ROUTES.LOGIN)}
      >
        {buttonTitle}
      </Button>
    </Container>
  );
};

export default TokenResult;
