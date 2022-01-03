import TokenResult from '../../../components/token-result';
import { FC } from 'react';

const ConsumedPasswordToken: FC = () => (
  <TokenResult
    type="PASSWORD_CONSUMED_TOKEN"
    title="Cannot change password"
    content="The password cannot not be changed because this token had already been used before."
  />
);

export default ConsumedPasswordToken;
