import { FC } from 'react';
import TokenResult from '../../../components/token-result';
import { ROUTES } from '../../../constants/routes';
import { useNavigate } from 'react-router-dom';

const ExpiredRestoreAccountToken: FC = () => {
  const navigate = useNavigate();

  return (
    <TokenResult
      type="RESTORE_EXPIRED_TOKEN"
      title="Restore error"
      content="The account could not be restored as this restoration token had already expired."
      buttonTitle="Create new account"
      customOnClick
      handleOnClick={() => navigate(ROUTES.REGISTER)}
    />
  );
};

export default ExpiredRestoreAccountToken;
