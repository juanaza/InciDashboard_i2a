package com.uniovi.services;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Incident;
import com.uniovi.entities.Operator;
import com.uniovi.entities.types.OperatorKind;
import com.uniovi.repositories.OperatorRepository;

@Service
public class OperatorService {
	
	@Autowired
	private OperatorRepository operatorRepository;
	
	@Autowired
	private IncidentService incidentService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public void addOperator(Operator operator) {
		operator.setPassword(bCryptPasswordEncoder.encode(operator.getPassword()));
		this.operatorRepository.save(operator);
	}
	
	public List<Operator> getOperators(){
		return operatorRepository.findAll();
	}
	
	public Operator getOperatorByEmail(String email) {
		return operatorRepository.findByEmail(email);
	}
	
	public Operator getRandomOperatorOfKind(OperatorKind kind) {
		List<Operator> operators = operatorRepository.findAllByKind(kind);
		Random randNum = new Random();
		return operators.get(randNum.nextInt(operators.size()));
	}
	
	public boolean checkPassword(Operator operator, String password) {
		return bCryptPasswordEncoder.matches(password, operator.getPassword());		
	}

	public boolean modifyPermission(String action) {
		String[] actionSplitted = action.split("-");
		String permission = actionSplitted[0];
		String operatorEmail = actionSplitted[1];
		Operator operator = getOperatorByEmail(operatorEmail);
		
		boolean statePermission = false;
		switch(permission) {
		case "map":
			operator.setMapAccess(!operator.hasMapAccess());
			statePermission = operator.hasMapAccess();
			operatorRepository.save(operator);
			break;
		case "chart":
			operator.setChartAccess(!operator.hasChartAccess());
			statePermission = operator.hasChartAccess();
			operatorRepository.save(operator);
			break;
		case "modify":
			operator.setModifyAccess(!operator.hasModifyAccess());
			statePermission = operator.hasModifyAccess();
			operatorRepository.save(operator);
			break;
		case "admin":
			operator.modifyOperatorRole(!operator.isAdmin());
			statePermission = operator.isAdmin();
			operatorRepository.save(operator);
			break;
		default:
			break;	
		}		
		updateOperatorIncident(operator);
		return statePermission;
	}
	
	public void updateOperatorIncident(Operator operator) {
		for(Incident inci: incidentService.getIncidents()) {
			if(inci.getOperator() != null) {
				if(inci.getOperator().getEmail().equals(operator.getEmail())) {
					inci.setOperator(operator);
					incidentService.updateIncident(inci);
				}
			}			
		}
	}
}
