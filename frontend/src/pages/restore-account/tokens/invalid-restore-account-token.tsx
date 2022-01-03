import { FC } from 'react';
import TokenResult from '../../../components/token-result';
import { ROUTES } from '../../../constants/routes';
import { useNavigate } from 'react-router-dom';

const InvalidRestoreAccountToken: FC = () => {
  const navigate = useNavigate();

  return (
    <TokenResult
      type="RESTORE_INVALID_TOKEN"
      title="Restore error"
      content="The account could not be restored as this restoration token is not a valid token."
      buttonTitle="Create new account"
      customOnClick
      handleOnClick={() => navigate(ROUTES.REGISTER)}
    />
  );
};

export default InvalidRestoreAccountToken;
