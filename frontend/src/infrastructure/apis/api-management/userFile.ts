import { useAppSelector } from "@application/store";
// import {ApiUserFileAddPostRequest, Configuration, UserFileApi} from "../client";
import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";

/**
 * Use constants to identify mutations and queries.
 */
const getUserFilesQueryKey = "getUserFilesQuery";
const downloadUserFileQueryKey = "downloadUserFileQuery";
const addUserFileMutationKey = "addUserFileMutation";

// TODO: Re-enable after regenerating OpenAPI client
// const getFactory = (token: string | null) => new UserFileApi(new Configuration({accessToken: token ?? ""}));

export const useGetUserFiles = (page: number, pageSize: number, search: string = "") => {
    // Stub implementation
    return {
        data: [],
        isLoading: false,
        error: null,
        queryKey: getUserFilesQueryKey
    };
}

export const useAddUserFile = () => {
    // Stub implementation
    return {
        mutate: () => {},
        isLoading: false,
        error: null
    }
}

export const useDownloadUserFile = () => {
    const { token } = useAppSelector(x => x.profileReducer); // You can use the data form the Redux storage.

    return useMutation({
        mutationKey: [downloadUserFileQueryKey, token],
        mutationFn: async (id: string) => {
            return await getFactory(token).apiUserFileDownloadIdGet({ id });
        }
    })
}