import { FC } from 'react';
import TokenResult from '../../../components/token-result';

const UserVerifySuccess: FC = () => (
  <TokenResult
    smaller
    type="MAIL_SUCCESS"
    title="Success"
    content="The account e-mail address has been successfully verified."
  />
);

export default UserVerifySuccess;
