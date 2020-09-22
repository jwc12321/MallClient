package com.mall.sls.sort;

import com.mall.sls.BasePresenter;
import com.mall.sls.BaseView;
import com.mall.sls.data.entity.FirstCategory;
import com.mall.sls.data.entity.GoodsItem;
import com.mall.sls.data.entity.SearchHistory;
import com.mall.sls.data.entity.SecondCategory;

import java.util.List;

/**
 * @author jwc on 2020/9/21.
 * 描述：
 */
public interface SortContract {
    interface SortPresenter extends BasePresenter{
        void getFirstCategory();
        void getSecondCategory(String categoryId);
    }

    interface SortView extends BaseView<SortPresenter>{
        void renderFirstCategory(FirstCategory firstCategory);
        void renderSecondCategory(SecondCategory secondCategory);
    }

    interface CategoryGoodsPresenter extends BasePresenter{
        void getCategoryGoods(String refreshType,String categoryId,String sortType,String orderType);
        void getMoreCategoryGoods(String categoryId,String sortType,String orderType);
    }

    interface CategoryGoodsView extends BaseView<CategoryGoodsPresenter>{
        void renderCategoryGoods(GoodsItem goodsItem);
        void renderMoreCategoryGoods(GoodsItem goodsItem);
    }

    interface SearchGoodsPresenter extends BasePresenter{
        void getSearchHistory(String refreshType);
        void deleteHistory();
        void getKeywordGoods(String refreshType,String keyword,String sortType,String orderType);
        void getMoreKeywordGoods(String keyword,String sortType,String orderType);
    }

    interface SearchGoodsView extends BaseView<SearchGoodsPresenter>{
        void renderSearchHistory(List<String> history);
        void renderDeleteHistory();
        void renderKeywordGoods(GoodsItem goodsItem);
        void renderMoreKeywordGoods(GoodsItem goodsItem);
    }
}
