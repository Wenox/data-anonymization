import {IconButton, Tooltip} from "@mui/material";
import {UserStatus} from "../../api/requests/shared.types";
import {LockOutlined} from "@mui/icons-material";
import {FC} from "react";

interface BlockUserProps {
  status: UserStatus;
  handleBlockUser: () => void;
}

const BlockUser: FC<BlockUserProps> = ({status, handleBlockUser}) => {

  const {style, disabled} = status === UserStatus.ACTIVE ? {
    style: {color: 'red'}, disabled: false,
  } : {
    style: {color: 'gray'}, disabled: true
  };

  return (
    <Tooltip title={disabled ? 'Cannot block removed user' : 'Block'} followCursor={disabled} placement={'top'}>
      <span>
        <IconButton disabled={disabled} onClick={() => handleBlockUser()}>
          <LockOutlined fontSize='large' sx={style}/>
        </IconButton>
      </span>
    </Tooltip>
  );
}

export default BlockUser;
