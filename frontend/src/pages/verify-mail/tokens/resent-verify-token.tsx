import { FC } from 'react';
import TokenResult from '../../../components/token-result';

const ResentVerifyToken: FC = () => (
  <TokenResult
    type="MAIL_RESENT"
    title="Mail resent"
    content="Check your email for a link to verify your account. If it doesn’t appear within a few minutes, check your spam folder."
  />
);

export default ResentVerifyToken;
