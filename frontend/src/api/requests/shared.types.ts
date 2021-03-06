export enum Role {
  UNVERIFIED_USER = 'UNVERIFIED_USER',
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

export enum FileType {
  PSQL = 'PSQL',
  MYSQL = 'MYSQL',
}
