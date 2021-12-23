import { FC } from 'react';
import TokenResult from '../../../components/token-result';
import { useNavigate } from 'react-router-dom';

const ExpiredVerifyToken: FC = () => {
  const navigate = useNavigate();

  return (
    <TokenResult
      title="Cannot verify account"
      content="This token has already expired."
      buttonTitle="Send new verification token"
      customOnClick
      handleOnClick={() => {
        console.log('trying to generate new expiry token...');
        navigate('/login');
      }}
    />
  );
};

export default ExpiredVerifyToken;
