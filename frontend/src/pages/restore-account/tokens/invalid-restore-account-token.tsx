import { FC } from 'react';
import TokenResult from '../../../components/token-result';
import { ROUTES } from '../../../constants/routes';
import { useNavigate } from 'react-router-dom';

const InvalidRestoreAccountToken: FC = () => {
  const navigate = useNavigate();

  return (
    <TokenResult
      title="Cannot restore account"
      content="This token is not a valid account restoration token"
      buttonTitle="Create new account"
      customOnClick
      handleOnClick={() => navigate(ROUTES.REGISTER)}
    />
  );
};

export default InvalidRestoreAccountToken;
