import { FC } from 'react';
import TokenResult from '../../../components/token-result';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { postVerifyMailSendAgainGivenToken } from '../../../api/requests/verify-mail/verify-mail.requests';
import { toast } from 'react-toastify';

const ExpiredVerifyToken: FC = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const token: string = searchParams.get('token') ?? '';

  const handleResendVerificationMail = () => {
    // @ts-ignore
    postVerifyMailSendAgainGivenToken(token)
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
        navigate('/verify-mail/token-sent-again');
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
        navigate('/login');
      });
  };

  return (
    <TokenResult
      title="Cannot verify account"
      content="This token has already expired."
      buttonTitle="Send new verification token"
      customOnClick
      handleOnClick={handleResendVerificationMail}
    />
  );
};

export default ExpiredVerifyToken;
