package com.internousdev.hibiscus.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.hibiscus.dao.CartInfoDAO;
import com.internousdev.hibiscus.dao.PurchaseHistoryInfoDAO;
import com.internousdev.hibiscus.dto.CartInfoDTO;
import com.internousdev.hibiscus.dto.PurchaseHistoryInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 決済完了
 * @author Kouichi
 */
public class SettlementCompleteAction extends ActionSupport implements SessionAware{


	private Map<String,Object> session;
	private int destinationId;

	public String execute(){
		String result = ERROR;

		if(!session.containsKey("mCategoryDtoList")){
			return "session";
		}

		@SuppressWarnings("unchecked")
		ArrayList<PurchaseHistoryInfoDTO> purchaseHistoryInfoDtoList = (ArrayList<PurchaseHistoryInfoDTO>)session.get("splitCartList");

		//リストに入れたカート情報をDBへ登録
		PurchaseHistoryInfoDAO purchaseHistoryDAO = new PurchaseHistoryInfoDAO();
		int count = 0;
		for(int i=0;i<purchaseHistoryInfoDtoList.size();i++){
			count += purchaseHistoryDAO.regist(String.valueOf(session.get("loginId")),
					purchaseHistoryInfoDtoList.get(i).getProductId(),
					purchaseHistoryInfoDtoList.get(i).getProductCount(),
					destinationId,
					purchaseHistoryInfoDtoList.get(i).getPrice());
		}

		//ログインIDに紐付いたカート情報の削除
		if(count > 0){
			CartInfoDAO cartInfoDAO = new CartInfoDAO();
			count = cartInfoDAO.deleteAll(String.valueOf(session.get("loginId")));
			if(count > 0){
				List<CartInfoDTO> cartInfoDtoList = new ArrayList<CartInfoDTO>();
				cartInfoDtoList = cartInfoDAO.getCartInfoDTOList(String.valueOf(session.get("loginId")));
				Iterator<CartInfoDTO> iterator = cartInfoDtoList.iterator();
				if(!(iterator.hasNext())){
					cartInfoDtoList = null;
				}
				session.put("cartInfoDtoList", cartInfoDtoList);

				int totalPrice = Integer.parseInt(String.valueOf(cartInfoDAO.getTotalPrice(String.valueOf(session.get("loginId")))));
				session.put("totalPrice",totalPrice);

				session.remove("splitCartList");

				result = SUCCESS;
			}
		}
		return result;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public int getDestinationId() {
		return destinationId;
	}

	public void setDestinationId(int destinationId) {
		this.destinationId = destinationId;
	}

}
