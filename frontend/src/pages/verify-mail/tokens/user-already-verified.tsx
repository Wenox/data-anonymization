import { FC } from 'react';
import TokenResult from '../../../components/token-result';

const UserAlreadyVerified: FC = () => (
  <TokenResult
    smaller
    type="MAIL_ALREADY_VERIFIED"
    title="Verified"
    content="This e-mail address has already been verified."
  />
);

export default UserAlreadyVerified;
