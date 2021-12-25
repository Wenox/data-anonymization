import { Role } from '../api/requests/shared.types';
import { Cached, Groups, SvgIconComponent } from '@mui/icons-material';

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
];
