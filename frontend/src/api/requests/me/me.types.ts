import { Role, UserStatus } from '../shared.types';

export interface MeResponse {
  id: string;
  email: string;
  role: Role;
  firstName: string;
  lastName: string;
  purpose: string;
  status: UserStatus;
  verified: boolean;
  markedForRemoval: boolean;
  removalRequestedDate: string;
  blockedDate: string;
}

export interface EditMyProfileDto {
  email: string;
  firstName: string;
  lastName: string;
  purpose: string;
}
