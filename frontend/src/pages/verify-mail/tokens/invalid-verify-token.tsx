import { FC } from 'react';
import TokenResult from '../../../components/token-result';

const InvalidVerifyToken: FC = () => (
  <TokenResult
    type="MAIL_INVALID_TOKEN"
    title="Cannot verify"
    content="This account could not be successfully verified due to invalid verification token."
  />
);

export default InvalidVerifyToken;
