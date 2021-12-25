import { Role } from '../api/requests/shared.types';
import { Cached, Groups, Logout, SvgIconComponent } from '@mui/icons-material';

export interface NavigationItemDescription {
  path: string;
  name: string;
  roles: Role[];
  icon: SvgIconComponent;
}

export const NAVIGATION_ITEMS: NavigationItemDescription[] = [
  {
    path: '/users',
    name: 'Users',
    roles: [Role.ADMIN],
    icon: Groups,
  },
  {
    path: '/about',
    name: 'About',
    roles: [Role.VERIFIED_USER, Role.ADMIN],
    icon: Cached,
  },
  {
    path: '/logout',
    name: 'Logout',
    roles: [Role.UNVERIFIED_USER, Role.VERIFIED_USER, Role.ADMIN],
    icon: Logout,
  },
];
