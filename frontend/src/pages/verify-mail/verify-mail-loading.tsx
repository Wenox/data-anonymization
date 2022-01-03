import { FC, useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import Avatar from '@mui/material/Avatar';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import { CircularProgress, Container } from '@mui/material';
import { postVerifyMail } from '../../api/requests/verify-mail/verify-mail.requests';
import { theme } from '../../styles/theme';

const VerifyMailLoading: FC = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();

  const token: string = searchParams.get('token') ?? '';

  useEffect(() => {
    setTimeout(
      () =>
        postVerifyMail(token).then((response) => {
          navigate(`/verify-mail/${response.data}?token=${token}`);
        }),
      1000,
    );
  });

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
      <CircularProgress size="8rem" />
    </Container>
  );
};

export default VerifyMailLoading;
