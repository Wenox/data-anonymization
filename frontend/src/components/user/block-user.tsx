import { IconButton, Tooltip } from '@mui/material';
import { UserStatus } from '../../api/requests/shared.types';
import { LockOutlined } from '@mui/icons-material';
import { FC } from 'react';

interface BlockUserProps {
  status: UserStatus;
  forceRemoval: boolean;
  handleBlockUser: () => void;
}

const BlockUser: FC<BlockUserProps> = ({ status, forceRemoval, handleBlockUser }) => {
  const { style } =
    forceRemoval || status !== UserStatus.ACTIVE ? { style: { color: 'gray' } } : { style: { color: 'red' } };
  const disabled = status !== UserStatus.ACTIVE;

  return (
    <Tooltip title={disabled ? 'Cannot block removed user' : 'Block'} placement={'top'}>
      <span>
        <IconButton disabled={disabled} onClick={() => handleBlockUser()}>
          <LockOutlined fontSize="large" sx={style} />
        </IconButton>
      </span>
    </Tooltip>
  );
};

export default BlockUser;
