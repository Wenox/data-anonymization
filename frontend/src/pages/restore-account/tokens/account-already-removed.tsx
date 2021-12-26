import { FC } from 'react';
import TokenResult from '../../../components/token-result';
import { ROUTES } from '../../../constants/routes';
import { useNavigate } from 'react-router-dom';

const AccountAlreadyRemoved: FC = () => {
  const navigate = useNavigate();

  return (
    <TokenResult
      title="Account already removed"
      content="Unfortunately this account has already been removed and can no longer be restored."
      buttonTitle="Create new account"
      customOnClick
      handleOnClick={() => navigate(ROUTES.REGISTER)}
    />
  );
};

export default AccountAlreadyRemoved;
