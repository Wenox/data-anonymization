import { Role } from '../api/requests/shared.types';

export const ADMIN_COLOR: string = 'red';
export const VERIFIED_USER_COLOR: string = 'blue';
export const UNVERIFIED_USER_COLOR: string = 'green';

export const getColorForRole = (role: Role) => {
  if (role === Role.ADMIN) {
    return ADMIN_COLOR;
  } else if (role === Role.VERIFIED_USER) {
    return VERIFIED_USER_COLOR;
  } else {
    return UNVERIFIED_USER_COLOR;
  }
};
