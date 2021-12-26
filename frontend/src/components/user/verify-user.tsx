import { IconButton, Tooltip } from '@mui/material';
import { FC } from 'react';
import { Verified } from '@mui/icons-material';

interface VerifyUserProps {
  removed: boolean;
  verified: boolean;
  handleVerifyUser: () => void;
}

const VerifyUser: FC<VerifyUserProps> = ({ removed, verified, handleVerifyUser }) => {
  const enabled = !verified && !removed;
  const { style } = enabled ? { style: { color: '#00cc00' } } : { style: { color: 'gray' } };

  return (
    <Tooltip title={enabled ? 'Confirm verification' : removed ? 'User removed' : 'Already verified'} placement={'top'}>
      <span>
        <IconButton disabled={!enabled} onClick={() => handleVerifyUser()}>
          <Verified fontSize="large" sx={style} />
        </IconButton>
      </span>
    </Tooltip>
  );
};

export default VerifyUser;
