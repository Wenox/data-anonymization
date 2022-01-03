import { FC } from 'react';
import TokenResult from '../../../components/token-result';

const ExpiredPasswordToken: FC = () => (
  <TokenResult
    type="PASSWORD_EXPIRED_TOKEN"
    title="Cannot change password"
    content="The password cannot not be changed as the reset token had already expired."
  />
);

export default ExpiredPasswordToken;
