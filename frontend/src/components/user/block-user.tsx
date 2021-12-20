import {IconButton, Tooltip} from "@mui/material";
import {UserStatus} from "../../api/requests/shared.types";
import {LockOutlined} from "@mui/icons-material";
import {FC} from "react";

interface BlockUserProps {
  status: UserStatus
}

const BlockUser: FC<BlockUserProps> = ({status}) => {

  const {style, disabled} = status === UserStatus.ACTIVE ? {
    style: {color: 'red'}, disabled: false,
  } : {
    style: {color: 'gray'}, disabled: true
  };

  return (
    <Tooltip title='Block'>
      <IconButton disabled={disabled} onClick={() => {
      }}>
        <LockOutlined fontSize='large' sx={style}/>
      </IconButton>
    </Tooltip>
  );
}

export default BlockUser;
