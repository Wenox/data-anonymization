import Login from '../pages/login';
import Register from '../pages/register';
import About from '../pages/about';
import Users from '../pages/users';
import ResetPassword from '../pages/reset-password/reset-password';
import ChangePasswordLoading from '../pages/reset-password/change-password-loading';
import ChangePasswordForm from '../pages/reset-password/change-password-form';
import InvalidPasswordToken from '../pages/reset-password/tokens/invalid-password-token';
import ExpiredPasswordToken from '../pages/reset-password/tokens/expired-password-token';
import ConsumedPasswordToken from '../pages/reset-password/tokens/consumed-password-token';
import VerifyMailLoading from '../pages/verify-mail/verify-mail-loading';
import VerifyMailPrompt from '../pages/verify-mail/verify-mail-prompt';
import UserVerifySuccess from '../pages/verify-mail/tokens/user-verify-success';
import InvalidVerifyToken from '../pages/verify-mail/tokens/invalid-verify-token';
import ExpiredVerifyToken from '../pages/verify-mail/tokens/expired-verify-token';
import UserAlreadyVerified from '../pages/verify-mail/tokens/user-already-verified';
import ResentVerifyToken from '../pages/verify-mail/tokens/resent-verify-token';
import React from 'react';
import Logout from '../pages/logout';
import UserProfile from '../pages/user/user-profile';
import RestoreAccountLoading from '../pages/restore-account/restore-account-loading';
import RestoreAccountSuccess from '../pages/restore-account/tokens/restore-account-success';
import InvalidRestoreAccountToken from '../pages/restore-account/tokens/invalid-restore-account-token';
import ExpiredRestoreAccountToken from '../pages/restore-account/tokens/expired-restore-account-token';
import AccountAlreadyRemoved from '../pages/restore-account/tokens/account-already-removed';
import Tasks from '../pages/tasks';
import Templates from '../pages/templates';
import Home from '../pages/home';
import TemplateGenerationNew from '../pages/templates/generation/template-generation-new';
import TemplateGenerationUploadSuccess from '../pages/templates/generation/template-generation-upload-success';
import TemplateGenerationRestoreSuccess from '../pages/templates/generation/template-generation-restore-success';
import TemplateGenerationSuccess from '../pages/templates/generation/template-generation-success';
import TemplateGenerationError from '../pages/templates/generation/template-generation-error';
import MyTemplates from '../pages/templates/my-templates';

export const ROUTES = {
  HOME: '/home',
  LOGIN: '/login',
  REGISTER: '/register',
  LOGOUT: '/logout',
  USER_PROFILE: '/user-profile',
  TASKS: '/tasks',
  TEMPLATES: '/templates',
  MY_TEMPLATES: '/my-templates',
  TEMPLATES_GENERATE: '/templates/generate',
  TEMPLATES_GENERATING_NEW: '/templates/generating/new',
  TEMPLATES_GENERATING_UPLOAD_SUCCESS: '/templates/generating/upload-success',
  TEMPLATES_GENERATING_RESTORE_SUCCESS: '/templates/generating/restore-success',
  TEMPLATES_GENERATING_SUCCESS: '/templates/generating/success',
  TEMPLATES_GENERATING_ERROR: '/templates/generating/error',
  USERS: '/users',
  ABOUT: '/about',
  RESET_PASSWORD: '/reset-password',
  CHANGE_PASSWORD: '/change-password',
  CHANGE_PASSWORD_FORM: '/change-password/form',
  CHANGE_PASSWORD_INVALID_TOKEN: '/change-password/invalid-token',
  CHANGE_PASSWORD_EXPIRED_TOKEN: '/change-password/expired-token',
  CHANGE_PASSWORD_CONSUMED_TOKEN: '/change-password/consumed-token',
  CHANGE_PASSWORD_INVALID: '/change-password/invalid-token',
  VERIFY_MAIL: '/verify-mail',
  VERIFY_MAIL_PROMPT: '/verify-mail-prompt',
  VERIFY_MAIL_SUCCESS: '/verify-mail/success',
  VERIFY_MAIL_INVALID_TOKEN: '/verify-mail/invalid-token',
  VERIFY_MAIL_EXPIRED_TOKEN: '/verify-mail/expired-token',
  VERIFY_MAIL_ALREADY_VERIFIED: '/verify-mail/already-verified',
  VERIFY_MAIL_TOKEN_SENT_AGAIN: '/verify-mail/token-sent-again',
  RESTORE_ACCOUNT: '/restore-account',
  RESTORE_ACCOUNT_SUCCESS: '/restore-account/success',
  RESTORE_ACCOUNT_ALREADY_REMOVED: '/restore-account/already-removed',
  RESTORE_ACCOUNT_INVALID_TOKEN: '/restore-account/invalid-token',
  RESTORE_ACCOUNT_EXPIRED_TOKEN: '/restore-account/expired-token',
};

export interface RouteDescription {
  path: string;
  element: React.FunctionComponent | any;
  authenticated: boolean;
  menu: boolean;
}

export const APP_ROUTES: RouteDescription[] = [
  {
    path: ROUTES.HOME,
    element: Home,
    authenticated: true,
    menu: true,
  },
  {
    path: ROUTES.LOGIN,
    element: Login,
    authenticated: false,
    menu: false,
  },
  {
    path: ROUTES.REGISTER,
    element: Register,
    authenticated: false,
    menu: false,
  },
  {
    path: ROUTES.LOGOUT,
    element: Logout,
    authenticated: true,
    menu: false,
  },
  {
    path: ROUTES.USER_PROFILE,
    element: UserProfile,
    authenticated: true,
    menu: true,
  },
  {
    path: ROUTES.TASKS,
    element: Tasks,
    authenticated: true,
    menu: true,
  },
  {
    path: ROUTES.MY_TEMPLATES,
    element: MyTemplates,
    authenticated: true,
    menu: true,
  },
  {
    path: ROUTES.TEMPLATES_GENERATE,
    element: Templates,
    authenticated: true,
    menu: true,
  },
  {
    path: ROUTES.TEMPLATES_GENERATING_NEW,
    element: TemplateGenerationNew,
    authenticated: true,
    menu: true,
  },
  {
    path: ROUTES.TEMPLATES_GENERATING_UPLOAD_SUCCESS,
    element: TemplateGenerationUploadSuccess,
    authenticated: true,
    menu: true,
  },
  {
    path: ROUTES.TEMPLATES_GENERATING_RESTORE_SUCCESS,
    element: TemplateGenerationRestoreSuccess,
    authenticated: true,
    menu: true,
  },
  {
    path: ROUTES.TEMPLATES_GENERATING_SUCCESS,
    element: TemplateGenerationSuccess,
    authenticated: true,
    menu: true,
  },
  {
    path: ROUTES.TEMPLATES_GENERATING_ERROR,
    element: TemplateGenerationError,
    authenticated: true,
    menu: true,
  },
  {
    path: ROUTES.ABOUT,
    element: About,
    authenticated: false,
    menu: true,
  },
  {
    path: ROUTES.USERS,
    element: Users,
    authenticated: true,
    menu: true,
  },
  {
    path: ROUTES.RESET_PASSWORD,
    element: ResetPassword,
    authenticated: false,
    menu: false,
  },
  {
    path: ROUTES.CHANGE_PASSWORD,
    element: ChangePasswordLoading,
    authenticated: false,
    menu: false,
  },
  {
    path: ROUTES.CHANGE_PASSWORD_FORM,
    element: ChangePasswordForm,
    authenticated: false,
    menu: false,
  },
  {
    path: ROUTES.CHANGE_PASSWORD_INVALID_TOKEN,
    element: InvalidPasswordToken,
    authenticated: false,
    menu: false,
  },
  {
    path: ROUTES.CHANGE_PASSWORD_EXPIRED_TOKEN,
    element: ExpiredPasswordToken,
    authenticated: false,
    menu: false,
  },
  {
    path: ROUTES.CHANGE_PASSWORD_CONSUMED_TOKEN,
    element: ConsumedPasswordToken,
    authenticated: false,
    menu: false,
  },
  {
    path: ROUTES.VERIFY_MAIL,
    element: VerifyMailLoading,
    authenticated: false,
    menu: false,
  },
  {
    path: ROUTES.VERIFY_MAIL_PROMPT,
    element: VerifyMailPrompt,
    authenticated: false,
    menu: false,
  },
  {
    path: ROUTES.VERIFY_MAIL_SUCCESS,
    element: UserVerifySuccess,
    authenticated: false,
    menu: false,
  },
  {
    path: ROUTES.VERIFY_MAIL_INVALID_TOKEN,
    element: InvalidVerifyToken,
    authenticated: false,
    menu: false,
  },
  {
    path: ROUTES.VERIFY_MAIL_EXPIRED_TOKEN,
    element: ExpiredVerifyToken,
    authenticated: false,
    menu: false,
  },
  {
    path: ROUTES.VERIFY_MAIL_ALREADY_VERIFIED,
    element: UserAlreadyVerified,
    authenticated: false,
    menu: false,
  },
  {
    path: ROUTES.VERIFY_MAIL_TOKEN_SENT_AGAIN,
    element: ResentVerifyToken,
    authenticated: false,
    menu: false,
  },
  {
    path: ROUTES.RESTORE_ACCOUNT,
    element: RestoreAccountLoading,
    authenticated: false,
    menu: false,
  },
  {
    path: ROUTES.RESTORE_ACCOUNT_SUCCESS,
    element: RestoreAccountSuccess,
    authenticated: false,
    menu: false,
  },
  {
    path: ROUTES.RESTORE_ACCOUNT_INVALID_TOKEN,
    element: InvalidRestoreAccountToken,
    authenticated: false,
    menu: false,
  },
  {
    path: ROUTES.RESTORE_ACCOUNT_EXPIRED_TOKEN,
    element: ExpiredRestoreAccountToken,
    authenticated: false,
    menu: false,
  },
  {
    path: ROUTES.RESTORE_ACCOUNT_ALREADY_REMOVED,
    element: AccountAlreadyRemoved,
    authenticated: false,
    menu: false,
  },
];
