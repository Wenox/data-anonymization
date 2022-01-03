import { FC } from 'react';
import TokenResult from '../../../components/token-result';

const InvalidPasswordToken: FC = () => (
  <TokenResult
    type="PASSWORD_INVALID_TOKEN"
    title="Cannot change password"
    content="The password cannot not be changed due to invalid change password token."
  />
);

export default InvalidPasswordToken;
