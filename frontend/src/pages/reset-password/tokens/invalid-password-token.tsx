import { FC } from 'react';
import TokenResult from '../../../components/token-result';

const InvalidPasswordToken: FC = () => (
  <TokenResult title="Cannot change password" content="Invalid change password token – no such token exists." />
);

export default InvalidPasswordToken;
