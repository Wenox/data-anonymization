import { Role } from '../api/requests/shared.types';
import { AccountCircle, Cached, Groups, History, Logout, SvgIconComponent, UploadFile } from '@mui/icons-material';
import { ROUTES } from './routes';

export interface NavigationItemDescription {
  path: string;
  name: string;
  roles: Role[];
  icon: SvgIconComponent;
}

export const NAVIGATION_ITEMS: NavigationItemDescription[] = [
  {
    path: ROUTES.USER_PROFILE,
    name: 'Profile',
    roles: [Role.UNVERIFIED_USER, Role.VERIFIED_USER, Role.ADMIN],
    icon: AccountCircle,
  },
  {
    path: ROUTES.USERS,
    name: 'Users',
    roles: [Role.ADMIN],
    icon: Groups,
  },
  {
    path: ROUTES.TEMPLATES,
    name: 'Templates',
    roles: [Role.ADMIN, Role.VERIFIED_USER],
    icon: UploadFile,
  },
  {
    path: ROUTES.TASKS,
    name: 'Tasks',
    roles: [Role.ADMIN],
    icon: History,
  },
  {
    path: ROUTES.ABOUT,
    name: 'About',
    roles: [Role.VERIFIED_USER, Role.ADMIN],
    icon: Cached,
  },
  {
    path: ROUTES.LOGOUT,
    name: 'Logout',
    roles: [Role.UNVERIFIED_USER, Role.VERIFIED_USER, Role.ADMIN],
    icon: Logout,
  },
];
