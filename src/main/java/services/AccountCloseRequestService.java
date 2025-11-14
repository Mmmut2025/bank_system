package services;

import dao.AccountCloseRequestDao;
import models.AccountCloseRequest;

public class AccountCloseRequestService {
	AccountCloseRequestDao closeReqDao;
	public AccountCloseRequestService() {
		closeReqDao = new AccountCloseRequestDao();
	}
	
	public boolean saveReq(AccountCloseRequest req) {
		return closeReqDao.saveRequest(req);
	}
}
