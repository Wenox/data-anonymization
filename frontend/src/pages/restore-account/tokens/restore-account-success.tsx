import { FC } from 'react';
import TokenResult from '../../../components/token-result';

const RestoreAccountSuccess: FC = () => (
  <TokenResult
    title="Account restore success"
    content="Your account has been successfully restored. You can now sign-in."
  />
);

export default RestoreAccountSuccess;
