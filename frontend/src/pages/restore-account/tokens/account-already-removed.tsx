import { FC } from 'react';
import TokenResult from '../../../components/token-result';
import { ROUTES } from '../../../constants/routes';
import { useNavigate } from 'react-router-dom';

const AccountAlreadyRemoved: FC = () => {
  const navigate = useNavigate();

  return (
    <TokenResult
      type="RESTORE_ALREADY_REMOVED"
      title="Restore error"
      content="Unfortunately this account had already been removed and there can no longer be restored."
      buttonTitle="Create new account"
      customOnClick
      handleOnClick={() => navigate(ROUTES.REGISTER)}
    />
  );
};

export default AccountAlreadyRemoved;
