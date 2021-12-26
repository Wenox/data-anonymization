import { FC } from 'react';
import TokenResult from '../../../components/token-result';
import { ROUTES } from '../../../constants/routes';
import { useNavigate } from 'react-router-dom';

const ExpiredRestoreAccountToken: FC = () => {
  const navigate = useNavigate();

  return (
    <TokenResult
      title="Cannot restore account"
      content="Unfortunately this account restoration token has already expired."
      buttonTitle="Create new account"
      customOnClick
      handleOnClick={() => navigate(ROUTES.REGISTER)}
    />
  );
};

export default ExpiredRestoreAccountToken;
