package com.ccc.groupbuy.service;

import java.util.List;

import com.ccc.comm.bean.HistoryBean;
import com.ccc.comm.bean.HistorySummaryBean;
import com.ccc.comm.bean.SysLogBean;
import com.ccc.groupbuy.bean.GroupBuyBean;
import com.ccc.groupbuy.bean.GroupBuyItemBean;
import com.ccc.groupbuy.bean.GroupBuyPurchaseBean;
import com.ccc.groupbuy.bean.GroupBuySearchBean;
import com.framework.bean.RequestBean;

public interface GroupBuyService {

	int create(GroupBuyBean groupBuy) throws Exception;

	int additems(GroupBuyItemBean[] items) throws Exception;

	int participateWithValid(GroupBuyPurchaseBean groupBuyPurchase) throws Exception;

	List<GroupBuyBean> getList(GroupBuySearchBean search);

	GroupBuyBean getGroupBuy(GroupBuySearchBean search);

	int purchase(GroupBuyBean groupBuy);

	int deliver(GroupBuyBean groupBuy);

	int complete(GroupBuyBean groupBuy);

	int terminate(RequestBean request);

	int adjustItem(GroupBuyItemBean[] items);

	int pay(GroupBuyPurchaseBean groupBuyPurchase);

	int receive(GroupBuyPurchaseBean groupBuyPurchase);

	int collect(GroupBuyPurchaseBean groupBuyPurchase);

	int terminateParticipation(RequestBean request);

	int remindPay(GroupBuyPurchaseBean groupBuyPurchase);

	int remindReceive(GroupBuyPurchaseBean groupBuyPurchase);

	int saveHistory(HistoryBean history);

	List<HistorySummaryBean> getGBSummary(long userId);

	int removeParticipation(RequestBean request);

	int update(GroupBuyBean groupBuy);

	int disuseParticipation(RequestBean request);

	int notifyDeliver(GroupBuyBean groupBuy);

	int updateDeliverInfo(GroupBuyBean groupBuy);

	void scheduledJob(SysLogBean log);
}
