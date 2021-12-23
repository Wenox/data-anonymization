import TokenResult from '../../../components/token-result';
import { FC } from 'react';

const ConsumedPasswordToken: FC = () => (
  <TokenResult
    title="Cannot change password"
    content="This reset password token cannot be used as it was already consumed before."
  />
);

export default ConsumedPasswordToken;
