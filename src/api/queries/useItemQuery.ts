import {
  useInfiniteQuery,
  useQuery,
  useQueryClient,
} from '@tanstack/react-query';
import { SalesListData } from '../../page/SalesList';
import { CategoryData, ItemData, categoryDataType } from '../../types';
import {
  getFavorites,
  getFavoritesCategories,
  getItems,
  getRecommendCategories,
  getSalesList,
} from '../itemFetcher';

const ITEMS_QUERY_KEY = 'items';
const SALES_LIST_QUERY_KEY = 'salesList';
const FAVORITES_QUERY_KEY = 'favorites';
const RECOMMEND_CATEGORY = 'recommendCategory';
const FAVORITES_CATEGORY = 'favoritesCategory';

export const useGetItemData = (categoryId?: number) => {
  return useInfiniteQuery<ItemData>(
    [ITEMS_QUERY_KEY, categoryId],
    ({ pageParam }) => getItems({ pageParam, categoryId }),
    {
      getNextPageParam: lastPage => lastPage.nextCursor ?? undefined,
    }
  );
};

export const useGetSalesList = ({
  nickname,
  isSold,
}: {
  nickname: string;
  isSold?: boolean;
}) => {
  return useInfiniteQuery<SalesListData>(
    [SALES_LIST_QUERY_KEY, nickname, isSold],
    ({ pageParam }) => getSalesList({ pageParam, nickname, isSold }),
    {
      getNextPageParam: lastPage => lastPage.nextCursor ?? undefined,
    }
  );
};

export const useGetFavorites = (categoryId?: number) => {
  return useInfiniteQuery<ItemData>(
    [FAVORITES_QUERY_KEY, categoryId],
    ({ pageParam }) => getFavorites({ pageParam, categoryId }),
    {
      getNextPageParam: lastPage => lastPage.nextCursor ?? undefined,
    }
  );
};

export const useGetFavoritesCategoryData = () => {
  return useQuery<categoryDataType>(
    [FAVORITES_CATEGORY],
    getFavoritesCategories
  );
};

export const useGetRecommendCategoryData = () => {
  return useQuery<CategoryData>([RECOMMEND_CATEGORY], getRecommendCategories, {
    enabled: false,
  });
};

export const useResetRecommendCategory = () => {
  const queryClient = useQueryClient();
  return () =>
    queryClient.resetQueries({
      queryKey: [RECOMMEND_CATEGORY],
      exact: false,
    });
};
