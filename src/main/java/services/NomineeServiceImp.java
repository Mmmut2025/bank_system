package services;

import org.zkoss.zhtml.Messagebox;

import dao.NomineeDao;
import models.Nominee;

public class NomineeServiceImp implements NomineeService{
	private final NomineeDao nomineeDao;
	public NomineeServiceImp() {
		nomineeDao = new NomineeDao();
	}

	public boolean saveNominee(Nominee nominee) {
		return nomineeDao.createNominee(nominee);
	}
	
	public boolean isPresentNominee(long nomineeId) {
		if(nomineeDao.isNomineeExist(nomineeId)) {
			return true;
		}
		return false;
	}
}
