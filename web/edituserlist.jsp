<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Edit User</title>
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
        <style>
            /* Basic styling for the form */
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }
            body {
                font-family: 'Poppins', sans-serif;
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                min-height: 100vh;
                display: flex;
                justify-content: center;
                align-items: center;
                padding: 20px;
            }
            .container {
                background: rgba(255, 255, 255, 0.9);
                border-radius: 20px;
                padding: 40px;
                box-shadow: 0 8px 32px rgba(31, 38, 135, 0.37);
                max-width: 600px;
                width: 100%;
            }
            h1 {
                color: #4a4a4a;
                text-align: center;
                margin-bottom: 30px;
                font-size: 2.5em;
            }
            .error-message {
                color: red;
                margin-bottom: 20px;
                list-style-type: none;
                padding: 0;
            }
            form {
                display: grid;
                gap: 20px;
            }
            label {
                font-weight: 600;
                color: #4a4a4a;
                margin-bottom: 5px;
            }
            input, select, textarea {
                padding: 10px;
                border: none;
                border-radius: 5px;
                background-color: rgba(255, 255, 255, 0.8);
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                font-size: 16px;
                width: 100%;
            }
            button {
                background: linear-gradient(45deg, #667eea, #764ba2);
                color: white;
                padding: 12px 20px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 18px;
                box-shadow: 0 4px 6px rgba(50, 50, 93, 0.11), 0 1px 3px rgba(0, 0, 0, 0.08);
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Edit User</h1>

            <form action="editusers" method="post">
                <!-- Hidden field for marketingId -->
                <input type="hidden" id="id" name="id" value="${user.marketingId}">

                <label for="fullName">Full Name</label>
                <input type="text" id="fullName" name="fullName" value="${user.fullName}" required>

                <label for="email">Email</label>
                <input type="email" id="email" name="email" value="${user.email}" required>

                <label for="dob">Date of Birth</label>
                <input type="date" id="dob" name="dob" value="${user.dob}" required>

                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>

                <label for="phone">Phone</label>
                <input type="text" id="phone" name="phone" value="${user.phone}" required>

                <label for="gender">Gender</label>
                <select id="gender" name="gender" required>
                    <option value="" disabled>Select your gender</option>
                    <option value="Male" <c:if test="${user.gender == 'Male'}">selected</c:if>>Male</option>
                    <option value="Female" <c:if test="${user.gender == 'Female'}">selected</c:if>>Female</option>
                    <option value="Other" <c:if test="${user.gender == 'Other'}">selected</c:if>>Other</option>
                    </select>

                    <label for="role">Role</label>
                    <select id="role" name="role" required>
                        <option value="" disabled>Select your role</option>
                        <option value="Admin" <c:if test="${user.role == 'Admin'}">selected</c:if>>Admin</option>
                        <option value="Customer" <c:if test="${user.role == 'Customer'}">selected</c:if>>Customer</option>
                        <option value="Marketing" <c:if test="${user.role == 'Marketing'}">selected</c:if>>Marketing</option>
                        <option value="Sale" <c:if test="${user.role == 'Sale'}">selected</c:if>>Sale</option>
                        <option value="SaleManager" <c:if test="${user.role == 'SaleManager'}">selected</c:if>>Sale Manager</option>
                    </select>

                    <label for="status">Status</label>
                    <select id="status" name="status" required>
                        <option value="" disabled>Select status</option>
                        <option value="Active" <c:if test="${user.status == 'Active'}">selected</c:if>>Active</option>
                    <option value="Inactive" <c:if test="${user.status == 'Inactive'}">selected</c:if>>Inactive</option>
                    <option value="Blocked" <c:if test="${user.status == 'Blocked'}">selected</c:if>>Blocked</option>
                </select>

                <button type="submit">Save Changes</button>
            </form>
        </div>
    </body>
</html>
