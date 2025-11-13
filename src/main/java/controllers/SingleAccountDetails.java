package controllers;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.*;
import org.zkoss.zul.*;

import models.Account;
import services.AccountServiceImp;

public class SingleAccountDetails extends SelectorComposer<Window> {
	private final AccountServiceImp acconntService = new AccountServiceImp();

    @Wire private Label accountNo, accountType, accountBalance, accountStatus, accountBranch, modeOfOperation, nomineeId;

    @Override
    public void doAfterCompose(Window comp) throws Exception {
        super.doAfterCompose(comp);

        Long selectedAccountNo = (Long) Executions.getCurrent().getSession().getAttribute("selected_account_no");
        //Long customerId = (Long) Executions.getCurrent().getSession().getAttribute("customer_id");
   

       // if (selectedAccountNo == null || customerId == null) {
         if (selectedAccountNo == null) {
            Messagebox.show("Invalid access or session expired!", "Error", Messagebox.OK, Messagebox.ERROR);
            Executions.sendRedirect("close_account.zul");
            return;
        }

        Account acc = acconntService.getAccountDetails(selectedAccountNo);
        if (acc == null) {
            Messagebox.show("Account not found!", "Error", Messagebox.OK, Messagebox.ERROR);
            Executions.sendRedirect("close_account.zul");
            return;
        }

        accountNo.setValue(String.valueOf(acc.getAccount_no()));
        accountType.setValue(acc.getAccount_type().name());
        accountBalance.setValue(String.format("%.2f", acc.getBalance()));
        accountStatus.setValue(acc.getAccount_status().name());
        accountBranch.setValue(acc.getBranch_Name());
        modeOfOperation.setValue(acc.getMode_of_operation().name());
        nomineeId.setValue(String.valueOf(acc.getNominee_id()));
    }

    @Listen("onClick = #backBtn")
    public void onBackClick() {
        Executions.sendRedirect("view_all_account.zul");
    }
}