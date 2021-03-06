import { Role } from '../api/requests/shared.types';
import {
  AccountCircle,
  Groups,
  HelpOutline,
  History as HistoryIcon,
  Home,
  Logout,
  SnippetFolder,
  Storage as StorageIcon,
  SvgIconComponent,
  TableView,
  UploadFile,
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
    path: ROUTES.TEMPLATES_GENERATE,
    name: 'New template',
    roles: [Role.ADMIN, Role.VERIFIED_USER],
    icon: UploadFile,
  },
  {
    path: ROUTES.MY_TEMPLATES,
    name: 'Templates',
    roles: [Role.ADMIN, Role.VERIFIED_USER],
    icon: SnippetFolder,
  },
  {
    path: ROUTES.MY_WORKSHEETS,
    name: 'Worksheets',
    roles: [Role.ADMIN, Role.VERIFIED_USER],
    icon: TableView,
  },
  {
    path: ROUTES.MY_OUTCOMES,
    name: 'Outcomes',
    roles: [Role.ADMIN, Role.VERIFIED_USER],
    icon: StorageIcon,
  },
  {
    path: ROUTES.TASKS,
    name: 'Scheduling',
    roles: [Role.ADMIN],
    icon: HistoryIcon,
  },
  {
    path: ROUTES.HELP,
    name: 'Help',
    roles: [Role.VERIFIED_USER, Role.ADMIN],
    icon: HelpOutline,
  },
  {
    path: ROUTES.LOGOUT,
    name: 'Logout',
    roles: [Role.UNVERIFIED_USER, Role.VERIFIED_USER, Role.ADMIN],
    icon: Logout,
  },
];
