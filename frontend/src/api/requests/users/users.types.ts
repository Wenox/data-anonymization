import {Role, UserStatus} from "../shared.types";

export interface RegisterUserRequest {
  email: string;
  password: string;
  firstName: string;
  lastName: string;
  purpose: string | null;
}

export interface FullUserResponse {
  id: string;
  email: string;
  role: Role;
  firstName: string;
  lastName: string;
  purpose: string;
  status: UserStatus;
  verified: boolean;
  markedForRemoval: boolean;
  forceRemoval: boolean;
  removalRequestedDate: string;
  removedDate: string;
  blockedDate: string;
  registeredDate: string;
  lastLoginDate: string;
}

export type User = FullUserResponse;
