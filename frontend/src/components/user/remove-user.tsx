import {FC} from "react";
import {IconButton, Tooltip} from "@mui/material";
import {AutoDelete, Delete, DeleteForever} from "@mui/icons-material";
import {UserStatus} from "../../api/requests/shared.types";

interface RemoveUserProps {
  status: UserStatus;
  markedForRemoval: boolean;
  forceRemoval: boolean;
  handleRemoveUser: () => void;
}

const RemoveUser: FC<RemoveUserProps> = ({ status, markedForRemoval, forceRemoval, handleRemoveUser}) => {

  const disabled = status === UserStatus.REMOVED || forceRemoval || markedForRemoval;

  const icon = (() => {
    if (forceRemoval) return { tooltip: 'Pending admin requested removal', style: {color: 'gray'}};
    if (markedForRemoval) return { tooltip: 'Pending user requested removal', style: {color: '#c9c47b'}};
    if (disabled) return { tooltip: 'User was already removed', style: {color: 'gray'}};
    return { tooltip: 'Block', style: {color: 'red'}};
  })();

  return (
    <Tooltip title={icon.tooltip} followCursor={disabled} placement={'top'}>
      <span>
        <IconButton disabled={disabled} onClick={() => handleRemoveUser()}>
          {forceRemoval || markedForRemoval ? <AutoDelete fontSize='large' sx={icon.style} /> : <Delete fontSize='large' sx={icon.style} />}
        </IconButton>
      </span>
    </Tooltip>
  );
}

export default RemoveUser;
