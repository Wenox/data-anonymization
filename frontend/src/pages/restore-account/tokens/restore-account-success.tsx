import { FC } from 'react';
import TokenResult from '../../../components/token-result';

const RestoreAccountSuccess: FC = () => (
  <TokenResult
    title="Restore success"
    content="Your account has been successfully restored. You can now sign-in to the platform."
  />
);

export default RestoreAccountSuccess;
