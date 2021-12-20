import {Role, UserStatus} from "../shared.types";

export interface MeResponse {
  id: string;
  email: string;
  role: Role;
  firstName: string;
  lastName: string;
  status: UserStatus;
  verified: boolean;
  markedForRemoval: boolean;
  removalRequestedDate: string;
  blockedDate: string;
}
