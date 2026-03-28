// TODO: These are temporary stubs. Replace with actual generated models after running:
// cd frontend && openapi-generator-cli generate -i http://localhost:8080/v3/api-docs -g typescript-fetch -o ./src/infrastructure/apis/client --additional-properties=supportsES6=true

export enum UserRoleEnum {
    Admin = "ADMIN",
    User = "USER"
}

export interface ErrorMessage {
    message?: string;
    errorCode?: string;
}

export interface UserRecord {
    id?: string;
    username?: string;
    email?: string;
    city?: string;
    createdAt?: string;
}

export interface UserFileRecord {
    id?: string;
    name?: string;
    path?: string;
    createdAt?: string;
}
