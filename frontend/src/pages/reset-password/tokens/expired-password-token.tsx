import { FC } from 'react';
import TokenResult from '../../../components/token-result';

const ExpiredPasswordToken: FC = () => (
  <TokenResult title="Cannot change password" content="The reset password token has already expired." />
);

export default ExpiredPasswordToken;
