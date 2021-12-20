import {useQuery} from "react-query";
import {getUsers, User} from "../api/users";
import {DataGrid, GridColDef} from "@mui/x-data-grid";
import '../styles/users.scss';
import {IconButton} from "@mui/material";
import {Block, Edit, HowToReg} from "@mui/icons-material";

const Users = () => {

  const { data, isLoading } = useQuery('users', getUsers);
  const users: User[] = data?.data || [];

  const columns: GridColDef[] = [
    { field: 'email', headerName: 'E-mail address', flex: 1},
    { field: 'blocked', headerName: 'Is blocked?', flex: 1,
      renderCell: params => <span>{params.row.blocked ? 'Blocked' : 'Active'}</span>
    },
    { field: 'role', headerName: 'Role', flex: 1 },
    {field: 'actions', headerName: 'Actions', width: 125,
      sortable: false,
      filterable: false,
      renderCell: params => {
        return (
          <div>
            <IconButton onClick={() => {}}>
              <Edit/>
            </IconButton>
            <IconButton onClick={() => {}}>
              {params.row.blocked ? <HowToReg color='primary' /> : <Block color='secondary' />}
            </IconButton>
          </div>
        );
      }
    }
  ];

  return (
    <>
      <div id='user-registration'>
        <h1>Users</h1>
        <DataGrid autoHeight columns={columns} rows={users} loading={isLoading} />
      </div>
    </>
  )
}

export default Users;