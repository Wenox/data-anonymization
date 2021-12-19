import {useQuery} from "react-query";
import {getUsers, User} from "../api/users";
import {DataGrid, GridColDef} from "@mui/x-data-grid";
import '../styles/users.scss';

const Users = () => {

  const { data } = useQuery('users', getUsers);
  const users: User[] = data?.data || [];

  const columns: GridColDef[] = [
    { field: 'email', headerName: 'E-mail address' },
    { field: 'blocked', headerName: 'Is blocked?' },
    { field: 'role', headerName: 'Role' },
  ];

  return (
    <>
      <div id='user-registration' style={{ height: 500 }}>
        <h1>Users</h1>
        <DataGrid columns={columns} rows={users} />
      </div>

    </>
  )
}

export default Users;