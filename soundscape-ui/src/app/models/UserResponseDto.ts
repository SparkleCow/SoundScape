import { Role } from "./Role";

export interface UserResponseDto{
  username: string,
  email: string,
  firstName: string,
  lastName: string,
  birthDate: Date,
  country: string,
  profileImageUrl: string,
  bannerImageUrl: string,
  isVerified: boolean,
  isEnabled: boolean,
  isAccountLocked: boolean,
  isCredentialsExpired: boolean,
  role: Role[]
}
