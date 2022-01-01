import { IconButton, Tooltip } from '@mui/material';
import { LockOutlined } from '@mui/icons-material';
import { FC } from 'react';

interface UnblockUserProps {
  handleUnblockUser: () => void;
}

const UnblockUser: FC<UnblockUserProps> = ({ handleUnblockUser }) => (
  <Tooltip title="Unblock" placement={'top'}>
    <IconButton onClick={() => handleUnblockUser()}>
      <LockOutlined fontSize="large" sx={{ color: '#00ad17' }} />
    </IconButton>
  </Tooltip>
);

export default UnblockUser;
