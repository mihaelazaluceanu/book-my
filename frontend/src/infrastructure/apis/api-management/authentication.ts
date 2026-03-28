import {LoginDto} from "../client/models";
import {AuthControllerApi, Configuration} from "../client";
import {useMutation} from "@tanstack/react-query";

/**
 * Use constants to identify mutations and queries.
 */
const loginMutationKey = "loginMutation";

/**
 * Returns the object with the callbacks that can be used for the React Query API, in this case just to log in the user.
 */
export const useLogin = () => {
    return useMutation({ // Return the mutation object.
        mutationKey: [loginMutationKey], // Add the key to identify the mutation.
        mutationFn: (loginDto: LoginDto) => new AuthControllerApi(new Configuration({credentials: "include"})).login({loginDto}) // Add the mutation callback by using the generated client code and adapt it.
    })
}