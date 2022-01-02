import { Role } from '../api/requests/shared.types';
import {
  AccountCircle,
  Cached,
  Groups,
  History as HistoryIcon,
  Home,
  Logout,
  SvgIconComponent,
  UploadFile,
  UploadFileOutlined,
} from '@mui/icons-material';
import { ROUTES } from './routes';

export interface NavigationItemDescription {
  path: string;
  name: string;
  roles: Role[];
  icon: SvgIconComponent;
}

export const NAVIGATION_ITEMS: NavigationItemDescription[] = [
  {
    path: ROUTES.HOME,
    name: 'Home',
    roles: [Role.UNVERIFIED_USER, Role.VERIFIED_USER, Role.ADMIN],
    icon: Home,
  },
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
    path: ROUTES.MY_TEMPLATES,
    name: 'My templates',
    roles: [Role.ADMIN, Role.VERIFIED_USER],
    icon: UploadFileOutlined,
  },
  {
    path: ROUTES.TEMPLATES_GENERATE,
    name: 'New template',
    roles: [Role.ADMIN, Role.VERIFIED_USER],
    icon: UploadFile,
  },
  {
    path: ROUTES.TASKS,
    name: 'Tasks',
    roles: [Role.ADMIN],
    icon: HistoryIcon,
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
