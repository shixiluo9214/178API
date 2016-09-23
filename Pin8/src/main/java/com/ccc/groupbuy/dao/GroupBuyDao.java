package com.ccc.groupbuy.dao;

import java.util.List;

import com.ccc.comm.bean.HistorySummaryBean;
import com.ccc.groupbuy.bean.GroupBuyBean;
import com.ccc.groupbuy.bean.GroupBuyItemBean;
import com.ccc.groupbuy.bean.GroupBuyItemPurchaseBean;
import com.ccc.groupbuy.bean.GroupBuyPurchaseBean;
import com.ccc.groupbuy.bean.GroupBuySearchBean;
import com.ccc.groupbuy.bean.PayinfoBean;
import com.ccc.groupbuy.bean.PictureBean;

public interface GroupBuyDao {

	int create(GroupBuyBean groupBuy);

	int setStatus(GroupBuyBean groupBuy);

	int addItem(GroupBuyItemBean groupBuyItemBean);

	int addPic(PictureBean pictureBean);

	int participateItem(GroupBuyItemPurchaseBean groupBuyItemPurchaseBean);

	List<GroupBuyBean> getList(GroupBuySearchBean search);

	GroupBuyBean getGroupBuy(GroupBuySearchBean search);
	GroupBuyBean getGroupBuy(long gbId);

	List<GroupBuyItemBean> getItems(long gbId,long userId);

	List<PictureBean> getPics(long gbiId);

	int addPic4GB(long gbId);

	int calculateAmount4Purchase(long gbpId);
	
	int calculateAmount4GB(long gbId);

	List<GroupBuyItemPurchaseBean> getItemPurchases(long gbiId);

	List<GroupBuyPurchaseBean> getPurchases(long gbId,long userId);

	int adjustItem(GroupBuyItemBean groupBuyItemBean);

	int adjustItemPurchase(GroupBuyItemPurchaseBean groupBuyItemPurchaseBean);

	int pay(GroupBuyPurchaseBean groupBuyPurchase);

	int receive(GroupBuyPurchaseBean groupBuyPurchase);

	int collect(GroupBuyPurchaseBean groupBuyPurchase);

	int terminateParticipation(long gbpId);

	int createPayinfo(PayinfoBean payinfoBean);

	List<PayinfoBean> getPayinfos(long gbId);

	int updateSatus4Owner(GroupBuyBean groupBuy);

	GroupBuyPurchaseBean getPurchaseById(long id);

	List<HistorySummaryBean> getGBSummary(long userId);

	int update(GroupBuyBean groupBuy);

	int removePayinfo(long gbId);

	int insertParticipate(GroupBuyPurchaseBean groupBuyPurchase);

	List<GroupBuyPurchaseBean> getGBPList(GroupBuySearchBean search);

	int disuseParticipation(long gbpId);

	List<GroupBuyBean> getList4Job(String type);

	int notifyDeliver(GroupBuyBean groupBuy);

}
