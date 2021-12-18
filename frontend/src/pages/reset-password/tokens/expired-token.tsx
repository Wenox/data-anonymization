import {FC} from "react";
import TokenResult from "./token-result";

const ExpiredToken: FC = () => <TokenResult
  title='Cannot change password'
  content='The reset password token has already expired.'
/>

export default ExpiredToken;