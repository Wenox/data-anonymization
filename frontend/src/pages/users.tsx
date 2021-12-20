import {useQuery} from "react-query";
import {DataGrid, GridColDef} from "@mui/x-data-grid";
import '../styles/users.scss';
import {IconButton} from "@mui/material";
import {
  Edit,
} from "@mui/icons-material";
import {User} from "../api/requests/users/users.types";
import {getUsers} from "../api/requests/users/users.requests";
import BlockUser from "../components/user/block-user";
import UnblockUser from "../components/user/unblock-user";
import {UserStatus} from "../api/requests/shared.types";

const Users = () => {

  const {data, isLoading} = useQuery('users', getUsers);
  const users: User[] = data?.data || [];

  const columns: GridColDef[] = [
    {field: 'email', headerName: 'E-mail address', flex: 1},
    {field: 'status', headerName: 'User status', flex: 1},
    {field: 'role', headerName: 'Role', flex: 1},
    {
      field: 'actions', headerName: 'Actions', width: 125,
      sortable: false,
      filterable: false,
      renderCell: ({ row }) => {
        return (
          <div>
            <IconButton onClick={() => {
            }}>
              <Edit/>
            </IconButton>

            {row.status === UserStatus.BLOCKED ? <UnblockUser /> : <BlockUser status={row.status} />}

          </div>
        );
      }
    }
  ];

  return (
    <>
      <div id='user-registration'>
        <h1>Users</h1>
        <DataGrid autoHeight columns={columns} rows={users} loading={isLoading}/>
      </div>
    </>
  )
}

export default Users;