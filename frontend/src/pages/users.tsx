import {useQuery} from "react-query";
import {getUsers, User} from "../api/users";

const Users = () => {
  const {
    isLoading,
    isError,
    data,
    isFetching
  } = useQuery('users', getUsers);

  const users: User[] = data?.data ?? [];

  return (
    <>
      <div style={{backgroundColor: 'white'}}>
        <h1>Users table</h1>

        {isLoading && <h1>is loading...</h1>}
        {isFetching && <h1>is FETCHING...</h1>}
        {isError && <h1>is error...</h1>}

        {!isLoading && users.map(user => {
          return <p>{user.email} - blocked: {user.blocked} - role: {user.role}</p>
        })}

      </div>
    </>
  )
}

export default Users;