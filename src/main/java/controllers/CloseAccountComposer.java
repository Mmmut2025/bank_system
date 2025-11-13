package controllers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;

import models.Account;
import services.AccountServiceImp;

public class CloseAccountComposer extends SelectorComposer<Component>{
	@Wire private Label accountNo;
	private final AccountServiceImp acconntService = new AccountServiceImp();
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Long accountNum = (Long) Executions.getCurrent().getSession().getAttribute("selected_account_no");
		
		Account acc = acconntService.getAccountDetails(accountNum);
	    accountNo.setValue(acc.getAccount_no()+"");
	}
}
