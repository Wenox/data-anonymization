import { Role } from '../api/requests/shared.types';

export const ADMIN_COLOR: string = '#dd2c00';
export const ADMIN_COLOR_DARK: string = '#dd2c00';
export const VERIFIED_USER_COLOR: string = '#651fff';
export const VERIFIED_USER_COLOR_DARK: string = '#0100ca';
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
