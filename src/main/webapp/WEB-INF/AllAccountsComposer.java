package controllers;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.*;
import org.zkoss.zul.*;

import models.Account;
import models.Account.AccountStatus;
import models.Account.AccountType;
import services.AccountServiceImp;
import services.NomineeServiceImp;

import java.util.*;

public class AllAccountsComposer extends SelectorComposer<Window> {
	private final AccountServiceImp acconntService = new AccountServiceImp();
	private final NomineeServiceImp nomineeService = new NomineeServiceImp();

    @Wire private Listbox accountListbox;

    @Override
    public void doAfterCompose(Window comp) throws Exception {
        super.doAfterCompose(comp);
      

        Long customerId = (Long) Executions.getCurrent().getSession().getAttribute("customer_id");
        customerId=1001L;
        
//        if (customerId == null) {
//            Messagebox.show("Session expired. Please log in again.", "Error", Messagebox.OK, Messagebox.ERROR);
//           // Executions.sendRedirect("/customer_dashboard.zul");
//            return;
//        }

        List<Account> accounts = acconntService.listAllAccounts(customerId);

        for (Account acc : accounts) {
            Listitem item = new Listitem();
            item.appendChild(new Listcell(String.valueOf(acc.getAccount_no())));
            item.appendChild(new Listcell(acc.getAccount_type().name()));
            item.appendChild(new Listcell(String.format("%.2f", acc.getBalance())));
            item.appendChild(new Listcell(acc.getAccount_status().name()));

            // store account object as value
            item.setValue(acc);

            // make item clickable
            item.addEventListener("onClick", e -> openAccountDetail(acc));

            accountListbox.appendChild(item);
        }
    }

    private void openAccountDetail(Account acc) {
        // Store selected account number in session
        Executions.getCurrent().getSession().setAttribute("selected_account_no", acc.getAccount_no());
        // Redirect to details page
        Executions.sendRedirect("view_spc_account.zul");
    }

    @Listen("onClick = #backBtn")
    public void onBackClick() {
        Executions.sendRedirect("customer_dashboard.zul");
    }
}