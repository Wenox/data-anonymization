import {FC} from "react";
import TokenResult from "./token-result";

const InvalidToken: FC = () => <TokenResult
  title='Cannot change password'
  content='Invalid change password token – no such token exists.'
/>

export default InvalidToken;