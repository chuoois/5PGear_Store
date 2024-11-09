<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Edit Product</title>
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
            <h1>Edit Product</h1>

            <!-- Display error messages if any exist -->
            <c:if test="${not empty errors}">
                <ul class="error-message">
                    <c:forEach var="error" items="${errors}">
                        <li>${error}</li>
                        </c:forEach>
                </ul>
            </c:if>

            <form action="/SWP391Gr5/marketting/editProductMarketting" method="post" enctype="multipart/form-data">
                <input type="hidden" name="productId" value="${param.productId != null ? param.productId : product.productId}">

                <div>
                    <label for="productName">Product Name:</label>
                    <input type="text" name="productName" id="productName" value="${param.productName != null ? param.productName : product.productName}" required>
                </div>

                <div>
                    <label for="price">Price:</label>
                    <input type="number" step="0.01" name="price" id="price" value="${param.price != null ? param.price : product.price}" required>
                </div>
                
                <div>
                    <label for="price">Import Price</label>
                    <input type="number" step="0.01" name="ImportPrice" id="ImportPrice"  value="${importPrice != null ? importPrice : product.importPrice}" readonly="">
                </div>

                <div style="display: none;">
                    <label for="quantity">Quantity:</label>
                    <input type="number" name="quantity" id="quantity" value="${param.quantity != null ? param.quantity : product.quantity}" required>
                </div>
                <div style="display: none;">
                    <label for="quantity">Hold:</label>
                    <input type="number" name="hold" id="quantity" value="${param.hold != null ? param.hold : product.hold}" required>
                </div>



                <div>
                    <label for="description">Description:</label>
                    <textarea name="description" id="description" required>${param.description != null ? param.description : product.description}</textarea>
                </div>

                <div class="form-group">
                    <label for="imgPath">Image Upload</label>
                    <input type="file" class="form-control" id="imgPath" name="imgPath" accept="image/*" onchange="previewImage(event)">
                    <input type="hidden" name="currentImgPath" value="${product.imgPath}">
                    <img id="imagePreview" src="../${param.imgPath != null ? param.imgPath : product.imgPath}" alt="Current Product Image" style="width: 200px; height: auto; margin-top: 10px">
                </div>



                <div>
                    <label for="brandId">Brand:</label>
                    <select name="brandId" id="brandId" required>
                        <c:forEach var="brand" items="${listBrand}">
                            <option value="${brand.brandId}" 
                                    <c:if test="${(param.brandId != null && param.brandId == brand.brandId) || (product.brand.brandId == brand.brandId)}">selected</c:if>
                                        >
                                    ${brand.brandName}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div>
                    <label for="categoryId">Category:</label>
                    <select name="categoryId" id="categoryId" required>
                        <c:forEach var="category" items="${listCategory}">
                            <option value="${category.categoryId}" 
                                    <c:if test="${(param.categoryId != null && param.categoryId == category.categoryId) || (product.category.categoryId == category.categoryId)}">selected</c:if>
                                        >
                                    ${category.categoryName}
                            </option>
                        </c:forEach>
                    </select>
                </div>



                <div>
                    <label for="releaseYear">Release Year:</label>
                    <input type="number" name="releaseYear" id="releaseYear" value="${param.releaseYear != null ? param.releaseYear : product.releaseYear}" required>
                </div>

                <div>
                    <label for="sale">Sale:</label>
                    <input type="number" step="0.01" name="sale" id="sale" value="${param.sale != null ? param.sale : product.sale}" required>
                </div>

                <div style="display: none;">
                    <label for="rate">Rate:</label>
                    <input type="number" name="rate" id="rate" value="${param.rate != null ? param.rate : product.rate}" required>
                </div>

                <div>
                    <label for="status">Status:</label>
                    <select name="status" id="status" required>
                        <option value="1" ${product.status == 1 ? 'selected' : ''}>Active</option>
                        <option value="0" ${product.status == 0 ? 'selected' : ''}>Inactive</option>
                    </select>
                </div>

                <button type="submit">Update Product</button>
            </form>
        </div>

        <script>
            function previewImage(event) {
                const image = document.getElementById('imagePreview');
                const file = event.target.files[0]; // Get the selected file
                console.log("file: ",file)
                if (file) {
                    const reader = new FileReader();
                    reader.onload = function (e) {
                        image.src = e.target.result; // Set the image source to the uploaded file
                    };
                    reader.readAsDataURL(file); // Convert the file to a data URL
                } else {
                    image.src = '../${param.imgPath != null ? param.imgPath : product.imgPath}'; // Reset to original if no file is selected
                }
            }
        </script>

    </body>
</html>
