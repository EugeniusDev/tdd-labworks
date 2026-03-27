package edu.froliak.tddlabworks;

/*
  @author eugen
  @project tdd-labworks
  @class CompanyServiceImpl
  @version 1.0.0
  @since 3/27/2026 - 09.09
*/

import java.util.List;

public class CompanyServiceImpl implements ICompanyService {
    @Override
    public Company getTopLevelParent(Company child) {
        if (child == null) {
            return null;
        }
        if (child.getParent() == null) {
            return child;
        }

        return getTopLevelParent(child.getParent());
    }

    @Override
    public long getEmployeeCountForCompanyAndChildren(Company company, List<Company> companies) {
        if (company == null) {
            return 0;
        }

        long employeesCount = company.getEmployeesCount();

        for (Company childCompany : companies) {
            if (company.equals(childCompany.getParent())) {
                employeesCount += getEmployeeCountForCompanyAndChildren(childCompany, companies);
            }
        }

        return employeesCount;
    }
}