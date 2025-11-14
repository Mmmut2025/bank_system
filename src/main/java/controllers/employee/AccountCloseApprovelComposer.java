package controllers.employee;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;

import dao.AccountCloseRequestDao;
import models.AccountCloseRequest;
import models.AccountUpdateRequest;



public class AccountCloseApprovelComposer extends SelectorComposer<Component>{
	
	private final AccountCloseRequestDao accountCloseDao = new AccountCloseRequestDao();
	
	@Wire Listbox requestList;
	@Wire Button approveBtn,rejectBtn;
	    
	private long currentEmployeeId = 201;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		loadPendingRequests();
	}
	
	private void loadPendingRequests() {
		requestList.getItems().clear();
		List<AccountCloseRequest> list = accountCloseDao.getAllPendingRequest();
	
		if(list.size()==0) {
	        	approveBtn.setVisible(false);
	        	rejectBtn.setVisible(false);
	        	
	        	Hbox box = new Hbox();
	        	box.setWidth("100%");
	        	box.setHeight("50px");
	        	box.setPack("center");
	        	box.setAlign("center");
	        	
	        	Label label = new Label("No Account Pending for the Approvel");
	        	label.setStyle("font-size:22px");
	        	box.appendChild(label);
	        	
	        	requestList.getParent().appendChild(box);
	        	return;
	     }
		 requestList.setModel(new ListModelList<>(list));
	}
	
	@Listen("onClick=#approveBtn")
	public void approveRquest() {
		if(requestList.getSelectedItem()==null) {
        	Messagebox.show("Please select one account first!" );
        	return;
        }
		AccountCloseRequest req = requestList.getSelectedItem().getValue();
        accountCloseDao.approveRequest(req.getRequestId(), currentEmployeeId, "");
        loadPendingRequests();
	}
	
	@Listen("onClick=#rejectBtn")
	public void rejectRquest() {
		if(requestList.getSelectedItem()==null) {
        	Messagebox.show("Please select one account first!" );
        	return;
        }
		AccountCloseRequest req = requestList.getSelectedItem().getValue();
        accountCloseDao.rejectRequest(req.getRequestId(), currentEmployeeId, "");
        loadPendingRequests();
	}
}
