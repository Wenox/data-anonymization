import { FC } from 'react';
import TokenResult from '../../../components/token-result';

const UserAlreadyVerified: FC = () => (
  <TokenResult title="Account already verified" content="The e-mail address has already been verified." />
);

export default UserAlreadyVerified;
