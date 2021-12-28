import { useQuery } from 'react-query';
import { DataGrid, GridColDef } from '@mui/x-data-grid';
import { Container, IconButton } from '@mui/material';
import { Edit } from '@mui/icons-material';
import { User } from '../api/requests/users/users.types';
import { getUsers, putBlockUser, putForceUserRemoval, putUnblockUser } from '../api/requests/users/users.requests';
import BlockUser from '../components/user/block-user';
import UnblockUser from '../components/user/unblock-user';
import { UserStatus } from '../api/requests/shared.types';
import { toast } from 'react-toastify';
import RemoveUser from '../components/user/remove-user';
import VerifyUser from '../components/user/verify-user';
import { postConfirmVerifyMail } from '../api/requests/verify-mail/verify-mail.requests';
import Typography from '@mui/material/Typography';
import { theme } from '../styles/theme';

const Users = () => {
  const { data, isLoading, refetch, isRefetching } = useQuery('users', getUsers);
  const users: User[] = data?.data || [];

  const handleVerifyUser = (id: string) => {
    postConfirmVerifyMail(id)
      .then((response) => {
        if (response.data.success) {
          toast.success(response.data.message, {
            position: 'top-right',
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
          refetch();
        } else {
          toast.error(response.data.message, {
            position: 'top-right',
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
        }
      })
      .catch(() =>
        toast.error('Failed to confirm users mail verification.', {
          position: 'top-right',
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        }),
      );
  };

  const handleRemoveUser = (id: string) => {
    putForceUserRemoval(id)
      .then((response) => {
        if (response.data.success) {
          toast.success(response.data.message, {
            position: 'top-right',
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
          refetch();
        } else {
          toast.error(response.data.message, {
            position: 'top-right',
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
        }
      })
      .catch(() =>
        toast.error('Failed to block the user.', {
          position: 'top-right',
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        }),
      );
  };

  const handleBlockUser = (id: string) => {
    putBlockUser(id)
      .then((response) => {
        if (response.data.success) {
          toast.success(response.data.message, {
            position: 'top-right',
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
          refetch();
        } else {
          toast.error(response.data.message, {
            position: 'top-right',
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
        }
      })
      .catch(() =>
        toast.error('Failed to block the user.', {
          position: 'top-right',
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        }),
      );
  };

  const handleUnblockUser = (id: string) => {
    putUnblockUser(id)
      .then((response) => {
        if (response.data.success) {
          toast.success(response.data.message, {
            position: 'top-right',
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
          refetch();
        } else {
          toast.error(response.data.message, {
            position: 'top-right',
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
        }
      })
      .catch(() =>
        toast.error('Failed to unblock the user.', {
          position: 'top-right',
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        }),
      );
  };

  const columns: GridColDef[] = [
    { field: 'email', headerName: 'E-mail address', flex: 1 },
    { field: 'status', headerName: 'User status', flex: 1 },
    { field: 'role', headerName: 'Role', flex: 1 },
    {
      field: 'actions',
      headerName: 'Actions',
      width: 225,
      sortable: false,
      filterable: false,
      renderCell: ({ row }) => {
        return (
          <div>
            <IconButton onClick={() => {}}>
              <Edit fontSize="large" sx={{ color: 'blue' }} />
            </IconButton>

            <VerifyUser
              removed={row.status === UserStatus.REMOVED}
              verified={row.verified}
              handleVerifyUser={() => handleVerifyUser(row.id)}
            />

            {row.status === UserStatus.BLOCKED ? (
              <UnblockUser handleUnblockUser={() => handleUnblockUser(row.id)} />
            ) : (
              <BlockUser
                status={row.status}
                forceRemoval={row.forceRemoval}
                handleBlockUser={() => handleBlockUser(row.id)}
              />
            )}

            <RemoveUser
              status={row.status}
              markedForRemoval={row.markedForRemoval}
              forceRemoval={row.forceRemoval}
              handleRemoveUser={() => handleRemoveUser(row.id)}
            />
          </div>
        );
      },
    },
  ];

  return (
    <Container
      maxWidth={false}
      component="main"
      sx={{
        backgroundColor: '#fff',
        border: `1px solid ${theme.palette.primary.main}`,
        boxShadow: `4px 4px 0px ${theme.palette.primary.dark}`,
        borderRadius: '2px',
        pt: 2,
        pb: 3,
      }}
    >
      <Typography color="secondary" variant="h2" sx={{ mb: 2 }}>
        Users
      </Typography>
      <DataGrid autoHeight columns={columns} rows={users} loading={isLoading || isRefetching} />
    </Container>
  );
};

export default Users;
