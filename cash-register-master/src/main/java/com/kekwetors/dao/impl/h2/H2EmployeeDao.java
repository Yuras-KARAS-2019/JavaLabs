package com.kekwetors.dao.impl.h2;

import com.kekwetors.dao.EmployeeDao;
import com.kekwetors.dao.H2DaoFactory;
import com.kekwetors.dao.model.Employee;
import com.kekwetors.dao.model.EmployeeRole;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Slf4j
public class H2EmployeeDao implements EmployeeDao {

    private static final String GET_ALL_EMPLOYEES = "SELECT * FROM employee;";
    private static final String GET_EMPLOYEE_BY_ID = "SELECT * FROM employee WHERE id = ?;";
    private static final String GET_EMPLOYEE_BY_LOGIN = "SELECT * FROM employee WHERE login = ?;";
    private static final String INSERT_EMPLOYEE = "INSERT INTO employee (name, role, login, password) VALUES (?, ?, ?, ?);";
    private static final String UPDATE_EMPLOYEE = "UPDATE employee SET name = ?, role = ?, login = ?, password = ? WHERE id = ?;";
    private static final String DELETE_EMPLOYEE = "DELETE FROM employee WHERE id = ?;";

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();

        try (Connection connection = H2DaoFactory.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL_EMPLOYEES);
            while (resultSet.next()) {
                Employee employee = new Employee(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        EmployeeRole.fromString(resultSet.getString("role")),
                        resultSet.getString("login"),
                        resultSet.getString("password"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            log.error("SQLException while getting all employees", e);
            throw new RuntimeException();
        }
        return employees;
    }

    @Override
    public Employee getEmployeeById(int id) {
        Employee employee = null;

        try (Connection connection = H2DaoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_EMPLOYEE_BY_ID)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                employee = new Employee(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        EmployeeRole.fromString(resultSet.getString("role")),
                        resultSet.getString("login"),
                        resultSet.getString("password"));
            }
        } catch (SQLException e) {
            log.error(format("SQLException while getting employee with id {%s}", id), e);
            throw new RuntimeException();
        }
        return employee;
    }

    @Override
    @SneakyThrows
    public Employee getEmployeeByLogin(String login) {
        Employee employee = null;

        try (Connection connection = H2DaoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_EMPLOYEE_BY_LOGIN)) {
            statement.setString(1, login);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                employee = new Employee(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        EmployeeRole.fromString(resultSet.getString("role")),
                        resultSet.getString("login"),
                        resultSet.getString("password"));
            }
        } catch (SQLException e) {
            log.error(format("SQLException while getting employee with login {%s}", login), e);
            throw new RuntimeException();
        }
        return employee;
    }

    @Override
    @SneakyThrows
    public Employee insertEmployee(Employee employee) {
        try (Connection connection = H2DaoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_EMPLOYEE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getRole().toString());
            statement.setString(3, employee.getLogin());
            statement.setString(4, employee.getPassword());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                log.error("Failed to insert " + employee);
                throw new RuntimeException();
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                employee.setId(generatedKeys.getInt(1));
            } else {
                log.error("Failed to obtain id of inserted " + employee);
                throw new RuntimeException();
            }

            log.info("Inserted " + employee);
        } catch (SQLException e) {
            log.error("SQLException while inserting " + employee, e);
            throw new RuntimeException();
        }

        return employee;
    }

    @Override
    @SneakyThrows
    public void updateEmployee(Employee employee) {
        try (Connection connection = H2DaoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_EMPLOYEE)) {
            statement.setInt(5, employee.getId());
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getRole().toString());
            statement.setString(3, employee.getLogin());
            statement.setString(4, employee.getPassword());
            statement.execute();

            log.info(String.format("Updated employee with id %s to %s", employee.getId(), employee));
        } catch (SQLException e) {
            log.error("SQLException while updating employee " + employee, e);
            throw new RuntimeException();
        }
    }

    @Override
    @SneakyThrows
    public void deleteEmployee(int id) {
        try (Connection connection = H2DaoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_EMPLOYEE)) {
            statement.setInt(1, id);
            statement.execute();

            log.info("Deleted employee with id " + id);
        } catch (SQLException e) {
            log.error("SQLException while deleting employee with id" + id, e);
            throw new RuntimeException();
        }
    }
}
