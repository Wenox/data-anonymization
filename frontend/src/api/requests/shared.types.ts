export enum Role {
  VERIFIED_USER = 'VERIFIED_USER',
  ADMIN = 'ADMIN',
}

export enum UserStatus {
  ACTIVE = 'ACTIVE',
  BLOCKED = 'BLOCKED',
  REMOVED = 'REMOVED',
}

export interface ApiResponse {
  message: string;
  success: boolean;
}
