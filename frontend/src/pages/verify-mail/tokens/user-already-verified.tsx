import { FC } from 'react';
import TokenResult from '../../../components/token-result';

const UserAlreadyVerified: FC = () => (
  <TokenResult
    smaller
    type="MAIL_ALREADY_VERIFIED"
    title="Verified"
    content="This account's e-mail address has already been verified before."
  />
);

export default UserAlreadyVerified;
