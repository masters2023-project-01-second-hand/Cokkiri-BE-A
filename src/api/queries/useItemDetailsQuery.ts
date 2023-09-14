import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { ItemDetailsData } from '../../page/ItemDetails';
import {
  getItemDetails,
  patchFavorite,
  patchStatus,
} from '../fetchers/itemDetailsFetcher';

const ITEM_DETAILS_QUERY_KEY = 'itemDetails';

export const useGetItemDetails = (itemId: number) => {
  return useQuery<ItemDetailsData>(
    [ITEM_DETAILS_QUERY_KEY, itemId],
    () => getItemDetails(Number(itemId)),
    {
      enabled: Boolean(itemId),
    }
  );
};

export const usePatchFavorite = () => {
  const queryClient = useQueryClient();

  return useMutation(patchFavorite, {
    onSuccess: (data, variables) => {
      queryClient.setQueryData<ItemDetailsData>(
        [ITEM_DETAILS_QUERY_KEY, variables.itemId],
        prevData => {
          return prevData
            ? {
                ...prevData,
                isFavorite: data.isFavorite,
              }
            : prevData;
        }
      );
    },
  });
};

export const usePatchStatus = () => {
  const queryClient = useQueryClient();

  return useMutation(patchStatus, {
    onSuccess: (data, variables) => {
      queryClient.setQueryData<ItemDetailsData>(
        [ITEM_DETAILS_QUERY_KEY, variables.itemId],
        prevData => {
          return prevData
            ? {
                ...prevData,
                status: prevData.status.map(status => {
                  return {
                    ...status,
                    isSelected: status.name === data.statusName,
                  };
                }),
              }
            : prevData;
        }
      );
    },
  });
};
