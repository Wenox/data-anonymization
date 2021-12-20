import {IconButton, Tooltip} from "@mui/material";
import {LockOutlined} from "@mui/icons-material";
import {FC} from "react";

const UnblockUser: FC = () => (
  <Tooltip title='Unblock'>
    <IconButton onClick={() => {
    }}>
      <LockOutlined fontSize='large' sx={{color: 'green'}}/>
    </IconButton>
  </Tooltip>
);

export default UnblockUser;
