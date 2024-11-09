<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Edit Slider</title>
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
            <h1>Edit Slider</h1>

            <!-- Display error messages if any exist -->
            <c:if test="${not empty errors}">
                <ul class="error-message">
                    <c:forEach var="error" items="${errors}">
                        <li>${error}</li>
                        </c:forEach>
                </ul>
            </c:if>
           

            <form action="/SWP391Gr5/marketting/editSliderMarketting" method="post" enctype="multipart/form-data">
                <input type="hidden" name="sliderId" value="${param.sliderId != null ? param.sliderId : slider.sliderId}">

                <div>
                    <label for="sliderTitle">Slider Title:</label>
                    <input type="text" name="sliderTitle" id="sliderTitle" value="${param.title != null ? param.title : slider.title}" required>
                </div>
                <div class="form-group">
                    <label for="product">Product:</label>
                    <select class="form-control" id="product" name="productId" required>
                        <c:if test="${empty param.productId}">
                            <option value="${listProduct[0].productId}" selected>${listProduct[0].productName}</option>
                        </c:if>
                        <c:forEach var="product" items="${listProduct}">
                            <option value="${product.productId}" ${product.productId == (param.productId != null ? param.productId : slider.productId) ? 'selected' : ''}>
                                ${product.productName}
                            </option>
                        </c:forEach>
                    </select>
                </div>


                <div>
                     <label for="backlink">Date Create</label>
                    <input type="text" name="dateCreate" id="dateCreate" value="${slider.createdAt}" readonly>
                </div>
                <div>
                    <label for="imgPath">Image Upload:</label>
                    <input type="file" id="imgPath" name="imgPath" accept="image/*" onchange="previewImage(event)">
                    <!-- Hidden input to hold the existing image path -->
                    <input  name="currentImgPath" value="${param.imgPath != null ? param.imgPath : slider.imgPath}">
                    <img id="imagePreview" src="../${param.imgPath != null ? param.imgPath : slider.imgPath}" alt="Current Slider Image" style="width: 200px; height: auto; margin-top: 10px;">
                </div>

                <div>
                    <label for="status">Status:</label>
                    <select name="status" id="status" required>
                        <option value="1" ${slider.status == '1' ? 'selected' : ''}>Active</option>
                        <option value="0" ${slider.status == '0' ? 'selected' : ''}>Inactive</option>
                    </select>
                </div>

                
                    <input type="hidden" name="updateBy" id="updateBy" value="${param.updateBy != null ? param.updateBy : slider.updateBy}" required>

                <button type="submit">Update Slider</button>
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
                    image.src = '../${param.imgPath != null ? param.imgPath : slider.imgPath}';
                }
            }
        </script>
    </body>
</html>
