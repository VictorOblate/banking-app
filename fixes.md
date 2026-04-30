Here is the focused fix prompt for your AI coding assistant:

Banking App Fix Instructions — NetBeans 8 / GlassFish 4 / MySQL
Fix the following issues in order. Do not add security features, do not refactor architecture, do not add new features beyond what is described. Only fix what is listed.

PART 1 — JSP COMPILATION CRASHES (fix these first or nothing runs)
Fix 1 — web/jsp/admin/payment/list.jsp
The JSP calls methods that do not exist on the Payment model. Replace the entire <tbody> loop content with the following:

jsp
<td><%= payment.getPaymentId() %></td>
<td><%= payment.getCustomerId() > 0 ? "Customer #" + payment.getCustomerId() : "N/A" %></td>
<td>
    <span class="badge badge-pending">
        <%= payment.getPaymentType() != null ? payment.getPaymentType() : "N/A" %>
    </span>
</td>
<td class="amount">LSL <%= String.format("%.2f", payment.getAmount()) %></td>
<td><%= payment.getPaymentDate() != null ? payment.getPaymentDate() : "N/A" %></td>
<td>
    <span class="badge <%= "PROCESSED".equals(payment.getPaymentStatus()) ? "badge-success" : "badge-pending" %>">
        <%= payment.getPaymentStatus() != null ? payment.getPaymentStatus() : "PENDING" %>
    </span>
</td>
<td>
    <div class="action-buttons">
        <a href="${pageContext.request.contextPath}/payment?action=view&id=<%= payment.getPaymentId() %>" class="btn btn-secondary btn-small">View</a>
        <a href="${pageContext.request.contextPath}/payment?action=edit&id=<%= payment.getPaymentId() %>" class="btn btn-secondary btn-small">Edit</a>
    </div>
</td>
Also remove these two import lines from the top of the file because SimpleDateFormat is no longer used:

jsp
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DecimalFormat" %>
Fix 2 — web/jsp/admin/payment/batch_view.jsp
Two JSTL c:if tags have broken EL syntax — the closing } is missing before the closing quote. Find and fix both:

Change:

jsp
<c:if test="${batch.batchType == 'SALARY'">
To:

jsp
<c:if test="${batch.batchType == 'SALARY'}">
Change:

jsp
<c:if test="${batch.batchType == 'OVERTIME'">
To:

jsp
<c:if test="${batch.batchType == 'OVERTIME'}">
Fix 3 — web/jsp/admin/report/list.jsp
There is an unclosed <div> wrapping the six report-card blocks. The block opens before the Customer Reports section but is never closed before the Recent Reports card. Find the line just before <!-- Recent Reports --> and add:

html
</div><%-- closes report-cards grid --%>
Fix 4 — web/jsp/admin/overtime/list.jsp
overtime.getOvertimeDate() returns a String not a java.util.Date. The fmt:formatDate tag crashes at runtime. Replace:

jsp
<fmt:formatDate value="${overtime.overtimeDate}" pattern="dd-MMM-yyyy"/>
With:

jsp
${overtime.overtimeDate}
Fix 5 — web/jsp/admin/payment/batch_list.jsp and web/jsp/admin/payment/batch_view.jsp
BulkPaymentBatch.getPaymentDate() returns a String. Remove all fmt:formatDate wrappers around batch.paymentDate in both files.

In batch_list.jsp change:

jsp
<fmt:formatDate value="${batch.paymentDate}" pattern="dd-MMM-yyyy"/>
To:

jsp
${batch.paymentDate}
In batch_view.jsp change:

jsp
<fmt:formatDate value="${batch.paymentDate}" pattern="dd-MMM-yyyy"/>
To:

jsp
${batch.paymentDate}
Fix 6 — Replace all <jsp:include> with <%@ include file="..." %> in payment and overtime JSPs
The files listed below use <jsp:include page="..."/> for the header and footer. This causes issues with the shared session context. Replace in every one of these files:

Files to change: overtime/form.jsp, overtime/list.jsp, payment/batch_list.jsp, payment/batch_view.jsp, payment/bulk_salary.jsp, payment/bulk_overtime.jsp, payment/bulk_salary_result.jsp, payment/bulk_overtime_result.jsp

In each file change:

jsp
<jsp:include page="/jsp/components/header.jsp" />
To:

jsp
<%@ include file="/jsp/components/header.jsp" %>
And change:

jsp
<jsp:include page="/jsp/components/footer.jsp" />
To:

jsp
<%@ include file="/jsp/components/footer.jsp" %>
Fix 7 — Delete dead file
Delete this file entirely. It has no session check and will cause a JSP compilation warning: web/jsp/admin/employee/form_old.jsp

PART 2 — DATABASE AND MODEL MISMATCHES (CRUD will silently fail or throw SQL exceptions)
Fix 8 — src/com/bankingapp/dao/AdminDAO.java — updateAdmin() references a column that does not exist
The admin table has no modified_date column but the UPDATE SQL references it. In updateAdmin(), change the SQL string from:

java
String sql = "UPDATE admin SET full_name = ?, email = ?, phone = ?, password = ?, is_active = ?, " +
             "modified_date = NOW() WHERE admin_id = ?";
To:

java
String sql = "UPDATE admin SET full_name = ?, email = ?, phone = ?, password = ?, is_active = ? " +
             "WHERE admin_id = ?";
The parameter bindings do not need to change because modified_date = NOW() was not a ? parameter — it was a literal function call in the SQL.

Fix 9 — src/com/bankingapp/dao/CustomerDAO.java — FK violation when no package is selected
When customer.getPackageId() is 0 (no package selected), the INSERT tries to insert 0 into package_id which is a foreign key — this fails with a SQL constraint error.

In addCustomer(), find:

java
preparedStatement.setInt(12, customer.getPackageId());
Replace with:

java
if (customer.getPackageId() > 0) {
    preparedStatement.setInt(12, customer.getPackageId());
} else {
    preparedStatement.setNull(12, java.sql.Types.INTEGER);
}
In updateCustomer(), find the equivalent setInt call for package_id and apply the same replacement.

Fix 10 — src/com/bankingapp/dao/EmployeeDAO.java — FK violation when no shift is selected
When employee.getShiftId() is 0 (no shift selected), the INSERT tries to insert 0 into shift_id which is a foreign key. This fails with a SQL constraint error.

In addEmployee(), find:

java
preparedStatement.setInt(15, employee.getShiftId());
Replace with:

java
if (employee.getShiftId() > 0) {
    preparedStatement.setInt(15, employee.getShiftId());
} else {
    preparedStatement.setNull(15, java.sql.Types.INTEGER);
}
In updateEmployee(), find the equivalent setInt call for shift_id and apply the same replacement.

Fix 11 — src/com/bankingapp/controller/PaymentServlet.java — savePayment() does not set paymentDate before calling the DAO
payment_date DATE NOT NULL in the schema. When a payment is saved from the add form, payment.getPaymentDate() is null because the servlet never reads it from the request. The DAO then tries to insert null into a NOT NULL column.

In PaymentServlet.savePayment(), after the block that builds the Payment object and before calling paymentService.processPayment(payment), add:

java
String paymentDate = request.getParameter("paymentDate");
if (paymentDate != null && !paymentDate.isEmpty()) {
    payment.setPaymentDate(paymentDate);
} else {
    payment.setPaymentDate(new java.text.SimpleDateFormat("yyyy-MM-dd")
        .format(new java.util.Date()));
}
Fix 12 — src/com/bankingapp/model/Overtime.java — add employeeName field for display in JSP
The overtime list JSP needs to display the employee's name but Overtime.java only has employeeId. Add the field and its getter/setter to Overtime.java:

java
private String employeeName;

public String getEmployeeName() {
    return employeeName;
}

public void setEmployeeName(String employeeName) {
    this.employeeName = employeeName;
}
Fix 13 — src/com/bankingapp/dao/OvertimeDAO.java — join employee name into all queries
Every method that queries the overtime table must be updated to also fetch the employee's name. Change the SQL in getPendingOvertimeRecords(), getApprovedOvertimeRecords(), getOvertimeByEmployeeId(), getOvertimeById(), and getAllOvertimeRecords() (which will be added in Fix 16).

For every SQL string that starts with:

java
"SELECT overtime_id, employee_id, overtime_date, hours_worked, " +
"hourly_rate, overtime_amount, overtime_type, remarks, status, " +
"created_date, approved_date, process_date FROM overtime "
Replace with:

java
"SELECT o.overtime_id, o.employee_id, o.overtime_date, o.hours_worked, " +
"o.hourly_rate, o.overtime_amount, o.overtime_type, o.remarks, o.status, " +
"o.created_date, o.approved_date, o.process_date, " +
"CONCAT(e.first_name, ' ', e.last_name) as employee_name " +
"FROM overtime o LEFT JOIN employees e ON o.employee_id = e.employee_id "
Update mapResultSetToOvertime() to also set the name:

java
overtime.setEmployeeName(resultSet.getString("employee_name"));
Update the column references throughout — every resultSet.getString("overtime_id") etc. stays the same because they are still in scope via the alias o.

Fix 14 — web/jsp/admin/overtime/list.jsp — Employee Name column shows raw ID
Change the Employee Name <td>:

jsp
<td>${overtime.employeeId}</td>
To:

jsp
<td>${overtime.employeeName != null ? overtime.employeeName : overtime.employeeId}</td>
Fix 15 — src/com/bankingapp/service/OvertimeService.java — recalculates amount after DAO call instead of before
In addOvertimeRecord(), the setOvertimeAmount() call happens after overtimeDAO.addOvertime(overtime) which means the DAO inserts 0 as the amount if the setters were called in the wrong order. Move the calculation to before the DAO call.

Find this block in addOvertimeRecord():

java
double overtimeAmount = overtime.getHourlyRate() * overtime.getHoursWorked();
overtime.setOvertimeAmount(overtimeAmount);
Ensure these two lines appear before the return overtimeDAO.addOvertime(overtime); line, not after it.

PART 3 — REMOVE ALL VALIDATIONS THAT BREAK CRUD
Fix 16 — src/com/bankingapp/util/ValidationUtil.java — phone validation rejects all Lesotho numbers
The current pattern requires exactly 10 digits. Lesotho numbers are 8 digits. This causes every customer and employee save to fail at the service layer.

Change:

java
private static final String PHONE_PATTERN = "^[0-9]{10}$";
To:

java
private static final String PHONE_PATTERN = "^[0-9]{7,15}$";
Fix 17 — src/com/bankingapp/service/CustomerService.java — email validation blocks all updates
updateCustomer() calls isValidCustomer() which calls customerDAO.emailExists(). This checks if the email exists for any customer — including the same customer being edited. So every edit of an existing customer fails with "email already exists".

In CustomerService.updateCustomer(), remove the email-exists check from the update path. Change updateCustomer() to skip the emailExists check entirely — only addCustomer() needs it. The simplest fix is to not call customerDAO.emailExists() inside updateCustomer(). The isValidCustomer() method itself is fine; the issue is addCustomer() calling emailExists before isValidCustomer and updateCustomer also calling isValidCustomer. Remove the redundant duplicate path.

Specifically, in updateCustomer(), replace the body with:

java
public boolean updateCustomer(Customer customer) {
    if (customer == null || customer.getCustomerId() <= 0) {
        return false;
    }
    if (customer.getFirstName() == null || customer.getFirstName().trim().isEmpty() ||
        customer.getLastName() == null || customer.getLastName().trim().isEmpty()) {
        return false;
    }
    return customerDAO.updateCustomer(customer);
}
Fix 18 — src/com/bankingapp/service/EmployeeService.java — remove validation that blocks all employee saves
isValidEmployee() calls ValidationUtil.isValidEmail() which rejects empty emails. But email is optional for employees. Any employee without an email address fails to save.

In isValidEmployee(), change the email check from:

java
if (employee.getEmail() != null && !employee.getEmail().trim().isEmpty() &&
    !ValidationUtil.isValidEmail(employee.getEmail())) {
    System.out.println("Invalid email");
    return false;
}
This is already conditionally checking — but ValidationUtil.isValidEmail() uses the regex ^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$ which is fine. The real problem is that isValidEmployee() is called from updateEmployee() but the email field might be null from the form. Ensure the null check is there — the existing code is technically correct but add an extra guard:

java
String email = employee.getEmail();
if (email != null && !email.trim().isEmpty() && !ValidationUtil.isValidEmail(email)) {
    return false;
}
Also in isValidEmployee(), the isValidName() check will fail for names with apostrophes. Change ValidationUtil.isValidName() regex from:

java
return trimmed.matches("^[a-zA-Z\\s-]+$");
To:

java
return trimmed.matches("^[a-zA-Z\\s\\-'.]+$");
Fix 19 — src/com/bankingapp/service/AdminService.java — updateAdmin() rejects blank password blocking all admin edits
When editing an admin, if the password field is left blank (to keep the existing password), updateAdmin() rejects the update because it validates password != null && !password.trim().isEmpty().

Replace the validation block in updateAdmin():

java
public boolean updateAdmin(Admin admin) {
    if (admin == null || admin.getAdminId() <= 0 ||
        admin.getPassword() == null || admin.getPassword().trim().isEmpty() ||
        admin.getFullName() == null || admin.getFullName().trim().isEmpty()) {
        return false;
    }
    return adminDAO.updateAdmin(admin);
}
With:

java
public boolean updateAdmin(Admin admin) {
    if (admin == null || admin.getAdminId() <= 0 ||
        admin.getFullName() == null || admin.getFullName().trim().isEmpty()) {
        return false;
    }
    return adminDAO.updateAdmin(admin);
}
Then in AdminServlet.saveAdmin(), when the action is an edit and password is blank, fetch the existing password and reuse it. After building the Admin object and before calling adminService.updateAdmin(admin), add:

java
if (adminIdStr != null && !adminIdStr.isEmpty()) {
    if (password == null || password.trim().isEmpty()) {
        Admin existing = adminService.getAdminById(Integer.parseInt(adminIdStr));
        if (existing != null) {
            admin.setPassword(existing.getPassword());
        }
    }
}
Fix 20 — src/com/bankingapp/dao/OvertimeDAO.java — add getAllOvertimeRecords() method
This method is needed for the overtime filter to work (Fix 21 requires it). Add to OvertimeDAO.java:

java
public List<Overtime> getAllOvertimeRecords() {
    String sql = "SELECT o.overtime_id, o.employee_id, o.overtime_date, o.hours_worked, " +
                 "o.hourly_rate, o.overtime_amount, o.overtime_type, o.remarks, o.status, " +
                 "o.created_date, o.approved_date, o.process_date, " +
                 "CONCAT(e.first_name, ' ', e.last_name) as employee_name " +
                 "FROM overtime o LEFT JOIN employees e ON o.employee_id = e.employee_id " +
                 "ORDER BY o.created_date DESC";

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    List<Overtime> overtimes = new ArrayList<>();

    try {
        connection = DBConnection.getConnection();
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            overtimes.add(mapResultSetToOvertime(resultSet));
        }
    } catch (SQLException | ClassNotFoundException e) {
        System.err.println("Error retrieving all overtime records: " + e.getMessage());
    } finally {
        closeResources(resultSet, statement, connection);
    }
    return overtimes;
}
Fix 21 — src/com/bankingapp/service/OvertimeService.java — add getAllOvertimeRecords() method
Add to OvertimeService.java:

java
public List<Overtime> getAllOvertimeRecords() {
    return overtimeDAO.getAllOvertimeRecords();
}
Fix 22 — src/com/bankingapp/controller/OvertimeServlet.java — filter always shows only PENDING regardless of selection
In listOvertimes(), both branches call getPendingOvertimeRecords(). Replace the entire listOvertimes() method body with:

java
private void listOvertimes(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, java.io.IOException {
    try {
        String status = request.getParameter("status");
        List<Overtime> overtimes;

        if (status != null && !status.isEmpty()) {
            if ("PENDING".equals(status)) {
                overtimes = overtimeService.getPendingOvertimeRecords();
            } else if ("APPROVED".equals(status)) {
                overtimes = overtimeService.getApprovedOvertimeRecords();
            } else {
                overtimes = overtimeService.getAllOvertimeRecords();
            }
        } else {
            overtimes = overtimeService.getAllOvertimeRecords();
        }

        request.setAttribute("overtimes", overtimes);

        Admin admin = (Admin) request.getSession().getAttribute(Constants.ADMIN_SESSION);
        if (admin != null) {
            request.setAttribute("admin", admin);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/overtime/list.jsp");
        dispatcher.forward(request, response);

    } catch (Exception e) {
        System.err.println("Error listing overtimes: " + e.getMessage());
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
PART 4 — BROKEN FUNCTIONALITY NOT IN THE HEADER
Fix 23 — web/jsp/components/header.jsp — Payments link is missing
The entire payment module (individual payments, bulk salary, bulk overtime, batch management) is completely unreachable from the UI because there is no nav link. Add a Payments link to the <nav> block.

In header.jsp, find the <nav> block and add after the Transactions link:

jsp
<a href="<%= request.getContextPath() %>/payment" 
   class="<%= request.getRequestURI().contains("/payment") ? "active" : "" %>">
    Payments
</a>
Fix 24 — web/jsp/components/header.jsp — Admin Management link is missing
The admin CRUD module exists and is fully developed but is not in the nav. Add it after the Reports link:

jsp
<a href="<%= request.getContextPath() %>/admin" 
   class="<%= request.getServletPath() != null && request.getServletPath().equals("/admin") ? "active" : "" %>">
    Admins
</a>
Fix 25 — web/jsp/admin/dashboard.jsp — "Schedule Shift" links to an action that does not exist
The button sends the user to /shift?action=schedule which has no handler in ShiftServlet and shows a blank page. Change:

jsp
<a href="${pageContext.request.contextPath}/shift?action=schedule" class="btn btn-secondary btn-small">Schedule Shift</a>
To:

jsp
<a href="${pageContext.request.contextPath}/shift?action=add" class="btn btn-secondary btn-small">Add Shift</a>
Fix 26 — web/jsp/admin/dashboard.jsp — "View Reports" links to an action that does not exist
/report?action=view is not a valid action in ReportServlet. Change:

jsp
<a href="${pageContext.request.contextPath}/report?action=view" class="btn btn-secondary btn-small">View Reports</a>
To:

jsp
<a href="${pageContext.request.contextPath}/report?action=list" class="btn btn-secondary btn-small">View Reports</a>
Fix 27 — src/com/bankingapp/controller/ReportServlet.java — "Generate Report" GET request falls through to list
The dashboard "Generate Report" button links to /report?action=generate as a GET request but ReportServlet.doGet() has no generate case so it always falls through to listReports().

In ReportServlet.doGet(), in the switch statement, add:

java
case "generate":
    showReportForm(request, response);
    break;
Fix 28 — src/com/bankingapp/controller/LogoutServlet.java — redirects directly to JSP path bypassing the servlet
Change:

java
response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
To:

java
response.sendRedirect(request.getContextPath() + "/login");
Fix 29 — web/css/style.css — multiple JSP pages use CSS classes that are never defined
The pages payment/list.jsp, transaction/list.jsp, shift/list.jsp, package/list.jsp, and report/list.jsp all use layout and component classes that do not exist in style.css. The pages render as unstyled raw text. Add the following to the end of web/css/style.css:

css
.page-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 1.5rem;
}
.page-title {
    display: flex;
    align-items: center;
    gap: 0.75rem;
}
.page-actions {
    display: flex;
    gap: 0.75rem;
}
.stats-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
    gap: 1rem;
    margin-bottom: 1.5rem;
}
.stat-card {
    background: var(--white);
    border: 1px solid var(--border-gray);
    border-radius: 8px;
    padding: 1.25rem;
    display: flex;
    align-items: center;
    gap: 1rem;
    box-shadow: 0 1px 3px rgba(0,0,0,0.06);
}
.stat-icon { color: var(--primary-green); }
.stat-content { flex: 1; }
.stat-value {
    font-size: 1.75rem;
    font-weight: 700;
    color: var(--primary-green);
    line-height: 1;
}
.stat-label {
    font-size: 0.85rem;
    color: var(--medium-gray);
    margin-top: 0.25rem;
}
.report-card {
    display: flex;
    gap: 1.5rem;
    padding: 1.5rem;
    border-bottom: 1px solid var(--border-gray);
}
.report-icon { color: var(--primary-green); flex-shrink: 0; }
.report-content { flex: 1; }
.report-actions {
    display: flex;
    gap: 0.5rem;
    margin-top: 0.75rem;
}
.action-buttons {
    display: flex;
    gap: 0.5rem;
    flex-wrap: wrap;
}
.btn-sm {
    padding: 0.35rem 0.75rem;
    font-size: 0.82rem;
}
.btn-info {
    background-color: #0288d1;
    color: white;
    border-radius: 4px;
    text-decoration: none;
    display: inline-block;
}
.btn-warning {
    background-color: #f57f17;
    color: white;
    border-radius: 4px;
    text-decoration: none;
    display: inline-block;
}
.btn-danger {
    background-color: var(--error-red);
    color: white;
    border-radius: 4px;
    text-decoration: none;
    display: inline-block;
}
.table-responsive { overflow-x: auto; }
.data-table { width: 100%; border-collapse: collapse; }
.data-table th {
    background: var(--light-green);
    color: var(--primary-green);
    padding: 0.75rem 1rem;
    text-align: left;
    font-weight: 600;
    border-bottom: 2px solid var(--primary-green);
}
.data-table td {
    padding: 0.75rem 1rem;
    border-bottom: 1px solid var(--border-gray);
}
.data-table tr:hover { background-color: var(--light-gray); }
.empty-state {
    text-align: center;
    padding: 3rem;
    color: var(--medium-gray);
}
.form-actions {
    display: flex;
    gap: 0.75rem;
    margin-top: 1.5rem;
}
.amount { font-weight: 600; }
.status {
    display: inline-block;
    padding: 0.3rem 0.65rem;
    border-radius: 4px;
    font-size: 0.82rem;
    font-weight: 600;
}
.status-completed, .status-processed {
    background: #e8f5e9;
    color: var(--success-green);
}
.status-pending {
    background: #fff9c4;
    color: #f57f17;
}
.status-failed, .status-cancelled, .status-rejected {
    background: #ffebee;
    color: var(--error-red);
}
.badge-danger {
    background-color: #ffcdd2;
    color: var(--error-red);
}
.badge-info {
    background-color: #e3f2fd;
    color: #0288d1;
}
PART 5 — EMPLOYEE PAYMENT JSP CLEANUP
Fix 30 — web/jsp/admin/payment/employee_payment.jsp — instantiates service layer directly in JSP scriptlet
Move the employee list loading out of the JSP and into the servlet.

In PaymentServlet.showEmployeePaymentForm(), add:

java
EmployeeService empService = new EmployeeService();
List<com.bankingapp.model.Employee> employees = empService.getAllEmployees();
request.setAttribute("employees", employees);
Add the import at the top of PaymentServlet.java:

java
import com.bankingapp.service.EmployeeService;
import com.bankingapp.model.Employee;
In employee_payment.jsp, remove these lines:

jsp
<%@ page import="com.bankingapp.service.EmployeeService" %>
...
EmployeeService employeeService = new EmployeeService();
List<Employee> employees = employeeService.getAllEmployees();
And replace with:

jsp
<%@ page import="java.util.List" %>
<%@ page import="com.bankingapp.model.Employee" %>
<%
    List<Employee> employees = (List<Employee>) request.getAttribute("employees");
%>
Apply all 30 fixes in the order listed. After applying, clean and rebuild the project in NetBeans, redeploy to GlassFish, and run the schema SQL to recreate the database before testing.


