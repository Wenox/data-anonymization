import { FC } from 'react';
import TokenResult from '../../../components/token-result';

const InvalidVerifyToken: FC = () => (
  <TokenResult
    title="Cannot verify account"
    content="Invalid verification token – no such token exists."
  />
);

export default InvalidVerifyToken;
