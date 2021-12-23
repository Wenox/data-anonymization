import { FC, useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import Avatar from '@mui/material/Avatar';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import { CircularProgress, Container } from '@mui/material';
import { postVerifyMail } from '../../api/requests/verify-mail/verify-mail.requests';

const VerifyMailLoading: FC = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();

  const token: string = searchParams.get('token') ?? '';

  useEffect(() => {
    postVerifyMail(token).then((response) => {
      navigate(`/verify-mail/${response.data}?token=${token}`);
    });
  });

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
      <CircularProgress size="8rem" />
    </Container>
  );
};

export default VerifyMailLoading;