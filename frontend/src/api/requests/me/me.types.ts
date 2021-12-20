import {Role} from "../shared.types";

export interface MeResponse {
  id: string;
  email: string;
  role: Role;
  firstName: string;
  lastName: string;
  blocked: boolean;
  verified: boolean;
  markedForRemoval: boolean;
  removalRequestedDate: string;
  blockedDate: string;
}
