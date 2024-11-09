<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Edit Blog</title>
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
            <h1>Edit Blog</h1>

            <!-- Display error messages if any exist -->
            <c:if test="${not empty errors}">
                <ul class="error-message">
                    <c:forEach var="error" items="${errors}">
                        <li>${error}</li>
                        </c:forEach>
                </ul>
            </c:if>

            <form action="/SWP391Gr5/marketting/editBlogMarketting" method="post"  enctype="multipart/form-data">
                <input type="hidden" name="blogId" value="${param.blogId != null ? param.blogId : blog.blogId}">

                <div>
                    <label for="title">Blog Title:</label>
                    <input type="text" name="title" id="title" value="${param.title != null ? param.title : blog.title}" required>
                </div>

                <div>
                    <label for="description">Description:</label>
                    <textarea name="description" id="description" required>${param.description != null ? param.description : blog.description}</textarea>
                </div>

                <div>
                    <label for="dateCreated">Date Created:</label>
                    <input type="date" name="dateCreated" id="dateCreated" value="${param.dateCreated != null ? param.dateCreated : blog.dateCreated}" required>
                </div>
                
                <div class="form-group">
                    <label for="imgPath">Image Upload</label>
                    <input type="file" class="form-control" id="imgPath" name="imgPath" accept="image/*" onchange="previewImage(event)">
                    <!-- Hidden input to hold the existing image path -->
                    <input type="hidden" name="currentImgPath" value="${param.imgPath != null ? param.imgPath : blog.imgPath}">
                    <img id="imagePreview" src="../${param.imgPath != null ? param.imgPath : blog.imgPath}" alt="Current Product Image" style="width: 200px; height: auto; margin-top: 10px;">
                </div>


                <div>
                    <label for="saleId">Marketer ID:</label>
                    <input type="number" name="marketerId" id="saleId" value="${param.marketerId != null ? param.marketerId : blog.marketerId}" required>
                </div>


                <div>
                    <label for="status">Status:</label>
                    <select name="status" id="status" required>
                        <option value="1" ${blog.status == 'Active' ? 'selected' : ''}>Active</option>
                        <option value="0" ${blog.status == 'Inactive' ? 'selected' : ''}>Inactive</option>
                    </select>
                </div>

                <div>
                    <label for="categoryId">Category:</label>
                    <select name="categoryId" id="categoryId" required>
                        <c:forEach var="category" items="${listCategory}">
                            <option value="${category.categoryId}" 
                                    <c:if test="${(param.categoryId != null && param.categoryId == category.categoryId) || (blog.categoryId == category.categoryId)}">selected</c:if>
                                        >
                                    ${category.categoryName}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div>
                    <label for="tags">Tags:</label>
                    <input type="text" name="tags" id="tags" value="${param.tags != null ? param.tags : blog.tags}" required>
                </div>

                <div>
                    <label for="briefInfo">Brief Info:</label>
                    <input type="text" name="briefInfo" id="briefInfo" value="${param.brief_info != null ? param.brief_info : blog.brief_info}" required>
                </div>

                <button type="submit">Update Blog</button>
            </form>
        </div>


        <script>
            function previewImage(event) {
                const image = document.getElementById('imagePreview');
                const file = event.target.files[0];
                if (file) {
                    const reader = new FileReader();
                    reader.onload = function (e) {
                        image.src = e.target.result;
                    };
                    reader.readAsDataURL(file);
                } else {

                    image.src = '../${param.imgPath != null ? param.imgPath : blog.imgPath}';
                }
            }

        </script>
    </body>
</html>
