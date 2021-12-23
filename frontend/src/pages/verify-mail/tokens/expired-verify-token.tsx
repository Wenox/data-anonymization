import { FC } from 'react';
import TokenResult from '../../../components/token-result';

const ExpiredVerifyToken: FC = () => (
  <TokenResult title="Cannot verify account" content="This token has already expired." />
);

export default ExpiredVerifyToken;
