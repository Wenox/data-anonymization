import TokenResult from './token-result';
import { FC } from 'react';

const ConsumedToken: FC = () => (
  <TokenResult
    title="Cannot change password"
    content="This reset password token cannot be used as it was already consumed before."
  />
);

export default ConsumedToken;
