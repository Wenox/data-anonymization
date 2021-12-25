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

export const ROUTES = {
  LOGIN: '/login',
  REGISTER: '/register',
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
};

export interface RouteDescription {
  path: string;
  element: React.FunctionComponent;
  authenticated: boolean;
  menu: boolean;
}

export const APP_ROUTES: RouteDescription[] = [
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
];
