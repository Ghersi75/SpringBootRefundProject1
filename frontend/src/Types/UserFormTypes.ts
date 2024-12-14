export interface SignUpFormPropsType {
  email: string,
  setEmail: React.Dispatch<React.SetStateAction<string>>,
  username: string,
  setUsername: React.Dispatch<React.SetStateAction<string>>,
  password: string,
  setPassword: React.Dispatch<React.SetStateAction<string>>,
  isUsernameAvailable: boolean | null
}