export interface User {
    id?: number;               // Optional because it may not be needed for registration
    username: string;
    password: string;
    latitude: number;
    longitude: number;
    role: 'ADMIN' | 'MECHANIC' | 'CUSTOMER';  // Enum values
  }