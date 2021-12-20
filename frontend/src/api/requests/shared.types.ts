export enum Role {
  VERIFIED_USER,
  ADMIN
}

export enum UserStatus {
  ACTIVE,
  BLOCKED,
  REMOVED
}

export interface ApiResponse {
  message: string;
  success: boolean;
}
