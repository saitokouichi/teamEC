package com.internousdev.hibiscus.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.hibiscus.dao.DestinationInfoDAO;
import com.internousdev.hibiscus.dao.ProductInfoDAO;
import com.internousdev.hibiscus.dto.DestinationInfoDTO;
import com.internousdev.hibiscus.dto.ProductInfoDTO;
import com.internousdev.hibiscus.dto.PurchaseHistoryInfoDTO;
import com.internousdev.hibiscus.util.CommonUtility;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 宛先情報選択
 *
 * @author Kouichi
 */
public class SettlementConfirmAction extends ActionSupport implements SessionAware {

	private String productId;
	private String productCount;
	private int notDestinationFlg;
	private Map<String, Object> session;

	public String execute() {

		String result = ERROR;

		// sessionタイムアウト
		if (!session.containsKey("mCategoryDtoList")) {
			return "session";
		}

		// 宛先情報の取得
		if (session.containsKey("loginId")) {
			DestinationInfoDAO destinationInfoDAO = new DestinationInfoDAO();
			List<DestinationInfoDTO> destinationInfoDtoList = new ArrayList<>();

			destinationInfoDtoList = destinationInfoDAO.getDestinationInfo(String.valueOf(session.get("loginId")));
			Iterator<DestinationInfoDTO> iterator = destinationInfoDtoList.iterator();

			if (!(iterator.hasNext())) {
				destinationInfoDtoList = null;
			}
			session.put("destinationInfoDtoList", destinationInfoDtoList);

		}

		// 分割して配列に格納
		// 新規宛先情報入力完了画面からの遷移では実行されない
		if (notDestinationFlg == 1) {

			ProductInfoDAO productInfoDAO = new ProductInfoDAO();
			ProductInfoDTO productInfoDTO = new ProductInfoDTO();
			List<PurchaseHistoryInfoDTO> purchaseHistoryInfoDtoList = new ArrayList<PurchaseHistoryInfoDTO>();

			CommonUtility commonUtility = new CommonUtility();
			String[] productIdList = commonUtility.parseArrayList(productId);
			String[] productCountList = commonUtility.parseArrayList(productCount);

			for (int i = 0; i < productCountList.length; i++) {
				// 商品個数が自然数以外に書き換えられた時、再びcart画面を表示させる
				if (Integer.parseInt(String.valueOf(productCountList[i])) <= 0
						|| Integer.parseInt(String.valueOf(productCountList[i])) >= 1000) {
					return "session";
				}

				// リストに格納
				PurchaseHistoryInfoDTO purchaseHistoryInfoDTO = new PurchaseHistoryInfoDTO();
				purchaseHistoryInfoDTO.setLoginId(String.valueOf(session.get("loginId")));
				purchaseHistoryInfoDTO.setProductId(Integer.parseInt(String.valueOf(productIdList[i])));
				productInfoDTO = productInfoDAO.getProductInfo(Integer.parseInt(String.valueOf(productIdList[i])));
				int price = productInfoDTO.getPrice();
				purchaseHistoryInfoDTO.setPrice(price);
				purchaseHistoryInfoDTO.setProductCount(Integer.parseInt(String.valueOf(productCountList[i])));
				purchaseHistoryInfoDtoList.add(purchaseHistoryInfoDTO);
			}

			session.put("splitCartList", purchaseHistoryInfoDtoList);
		}

		// loginIdが無いときカートフラグに値を入れる
		if (!session.containsKey("loginId")) {
			session.put("cartFlg", 1);
			result = "login";
		} else {
			result = SUCCESS;
		}
		return result;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductCount() {
		return productCount;
	}

	public void setProductCount(String productCount) {
		this.productCount = productCount;
	}

	public int getNotDestinationFlg() {
		return notDestinationFlg;
	}

	public void setNotDestinationFlg(int notDestinationFlg) {
		this.notDestinationFlg = notDestinationFlg;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
