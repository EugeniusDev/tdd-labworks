package edu.froliak.tddlabworks;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/*
  @author eugen
  @project tdd-labworks
  @class CompanyServiceImplTest
  @version 1.0.0
  @since 3/27/2026 - 09.10
*/

class CompanyServiceImplTest {
    private final Company main = new Company(null,2);
    private final Company book = new Company(main,3);
    private final Company manager = new Company(main,4);
    private final Company developer = new Company(manager,8);
    private final Company design = new Company(manager,6);
    private final Company lawer = new Company(null,1);

    private final List<Company> list = List.of(main, book, manager,developer, design);

    private final ICompanyService companyService = new CompanyServiceImpl();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenCompanyIsNullThenNull() {
        Company result = companyService.getTopLevelParent(null);
        Assertions.assertNull(result);
    }

    @Test
    void whenCompanyHasNoParentItIsOnTop() {
        Company result = companyService.getTopLevelParent(main);
        Assertions.assertEquals(main, result);
    }

    @Test
    void whenCompanyIsSingleItIsOnTop() {
        Company result = companyService.getTopLevelParent(lawer);
        Assertions.assertEquals(lawer, result);
    }

    @Test
    void whenCompanyHasOneStepToTheTopThenFindTop() {
        Company result = companyService.getTopLevelParent(book);
        Assertions.assertEquals(main, result);
    }

    @Test
    void whenCompanyHasTwoStepsToTheTopThenFindTop() {
        Company result = companyService.getTopLevelParent(developer);
        Assertions.assertEquals(main, result);
    }

    @Test
    void whenCompanyIsDeeplyNestedThenFindTop() {
        // Creating a deeper hierarchy: main -> manager -> developer -> junior
        Company junior = new Company(developer, 10);
        Company result = companyService.getTopLevelParent(junior);
        Assertions.assertEquals(main, result);
    }

    @Test
    void whenCompanyIsDesignThenMainIsTop() {
        // Tests another branch of the hierarchy
        Company result = companyService.getTopLevelParent(design);
        Assertions.assertEquals(main, result);
    }

    @Test
    void whenCalculatingEmployeeCountForTopLevelThenSumAll() {
        // Main(2) + Book(3) + Manager(4) + Developer(8) + Design(6) = 23
        long count = companyService.getEmployeeCountForCompanyAndChildren(main, list);
        Assertions.assertEquals(23, count);
    }

    @Test
    void whenCalculatingEmployeeCountForMidLevelThenSumSubTree() {
        // Manager(4) + Developer(8) + Design(6) = 18
        long count = companyService.getEmployeeCountForCompanyAndChildren(manager, list);
        Assertions.assertEquals(18, count);
    }

    @Test
    void whenCompanyHasNoChildrenThenEmployeeCountIsJustItsOwn() {
        // Lawyer is not in the list and has no children
        long count = companyService.getEmployeeCountForCompanyAndChildren(lawer, list);
        Assertions.assertEquals(1, count);
    }
}