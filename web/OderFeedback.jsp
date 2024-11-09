<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>

        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>VNPay Payment</title>

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/MaterialDesign-Webfont/5.3.45/css/materialdesignicons.css" integrity="sha256-NAxhqDvtY0l4xn+YVa6WjAcmd94NNfttjNsDmNatFVc=" crossorigin="anonymous" />
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body{
                margin-top:20px;
                background-color: #f1f3f7;
            }

            .card {
                margin-bottom: 24px;
                -webkit-box-shadow: 0 2px 3px #e4e8f0;
                box-shadow: 0 2px 3px #e4e8f0;
            }
            .card {
                position: relative;
                display: -webkit-box;
                display: -ms-flexbox;
                display: flex;
                -webkit-box-orient: vertical;
                -webkit-box-direction: normal;
                -ms-flex-direction: column;
                flex-direction: column;
                min-width: 0;
                word-wrap: break-word;
                background-color: #fff;
                background-clip: border-box;
                border: 1px solid #eff0f2;
                border-radius: 1rem;
            }
            .activity-checkout {
                list-style: none
            }

            .activity-checkout .checkout-icon {
                position: absolute;
                top: -4px;
                left: -24px
            }

            .activity-checkout .checkout-item {
                position: relative;
                padding-bottom: 24px;
                padding-left: 35px;
                border-left: 2px solid #f5f6f8
            }

            .activity-checkout .checkout-item:first-child {
                border-color: #3b76e1
            }

            .activity-checkout .checkout-item:first-child:after {
                background-color: #3b76e1
            }

            .activity-checkout .checkout-item:last-child {
                border-color: transparent
            }

            .activity-checkout .checkout-item.crypto-activity {
                margin-left: 50px
            }

            .activity-checkout .checkout-item .crypto-date {
                position: absolute;
                top: 3px;
                left: -65px
            }



            .avatar-xs {
                height: 1rem;
                width: 1rem
            }

            .avatar-sm {
                height: 2rem;
                width: 2rem
            }

            .avatar {
                height: 3rem;
                width: 3rem
            }

            .avatar-md {
                height: 4rem;
                width: 4rem
            }

            .avatar-lg {
                height: 5rem;
                width: 5rem
            }

            .avatar-xl {
                height: 6rem;
                width: 6rem
            }

            .avatar-title {
                -webkit-box-align: center;
                -ms-flex-align: center;
                align-items: center;
                background-color: #3b76e1;
                color: #fff;
                display: -webkit-box;
                display: -ms-flexbox;
                display: flex;
                font-weight: 500;
                height: 100%;
                -webkit-box-pack: center;
                -ms-flex-pack: center;
                justify-content: center;
                width: 100%
            }

            .avatar-group {
                display: -webkit-box;
                display: -ms-flexbox;
                display: flex;
                -ms-flex-wrap: wrap;
                flex-wrap: wrap;
                padding-left: 8px
            }

            .avatar-group .avatar-group-item {
                margin-left: -8px;
                border: 2px solid #fff;
                border-radius: 50%;
                -webkit-transition: all .2s;
                transition: all .2s
            }

            .avatar-group .avatar-group-item:hover {
                position: relative;
                -webkit-transform: translateY(-2px);
                transform: translateY(-2px)
            }

            .card-radio {
                background-color: #fff;
                border: 2px solid #eff0f2;
                border-radius: .75rem;
                padding: .5rem;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
                display: block
            }

            .card-radio:hover {
                cursor: pointer
            }

            .card-radio-label {
                display: block
            }

            .edit-btn {
                width: 35px;
                height: 35px;
                line-height: 40px;
                text-align: center;
                position: absolute;
                right: 25px;
                margin-top: -50px
            }

            .card-radio-input {
                display: none
            }

            .card-radio-input:checked+.card-radio {
                border-color: #3b76e1!important
            }


            .font-size-16 {
                font-size: 16px!important;
            }
            .text-truncate {
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
            }

            a {
                text-decoration: none!important;
            }


            .form-control {
                display: block;
                width: 100%;
                padding: 0.47rem 0.75rem;
                font-size: .875rem;
                font-weight: 400;
                line-height: 1.5;
                color: #545965;
                background-color: #fff;
                background-clip: padding-box;
                border: 1px solid #e2e5e8;
                -webkit-appearance: none;
                -moz-appearance: none;
                appearance: none;
                border-radius: 0.75rem;
                -webkit-transition: border-color .15s ease-in-out,-webkit-box-shadow .15s ease-in-out;
                transition: border-color .15s ease-in-out,-webkit-box-shadow .15s ease-in-out;
                transition: border-color .15s ease-in-out,box-shadow .15s ease-in-out;
                transition: border-color .15s ease-in-out,box-shadow .15s ease-in-out,-webkit-box-shadow .15s ease-in-out;
            }

            .edit-btn {
                width: 35px;
                height: 35px;
                line-height: 40px;
                text-align: center;
                position: absolute;
                right: 25px;
                margin-top: -50px;
            }

            .ribbon {
                position: absolute;
                right: -26px;
                top: 20px;
                -webkit-transform: rotate(45deg);
                transform: rotate(45deg);
                color: #fff;
                font-size: 13px;
                font-weight: 500;
                padding: 1px 22px;
                font-size: 13px;
                font-weight: 500
            }

        </style>

    </head>

    <body>
        <jsp:include page="layout/head.jsp"/>
        

        <div class="container">
           <nav aria-label="breadcrumb" class="breadcrumb-nav" style="position: relative; z-index: 10;">
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="#">Shopping Cart</a></li>
                        <li class="breadcrumb-item active" aria-current="page">Order Information</li>
                    </ol>
                </nav>
            <div class="row">
                <div class="col-xl-8">

                    <div class="card">
                        <div class="card-body">
                            <ol class="activity-checkout mb-0 px-4 mt-3">
                                <li class="checkout-item">
                                    <div class="avatar checkout-icon p-1">
                                        <div class="avatar-title rounded-circle bg-primary">
                                            <i class="bx bxs-receipt text-white font-size-20"></i>
                                        </div>
                                    </div>
                                    <div class="feed-item-list">
                                        <div>
                                            

                                            <div class="mb-3">
                                                <form>
                                                    <div>
                                                        <div class="row">
                                                            <div class="col-lg-4">
                                                                <div class="mb-3">
                                                                    <label class="form-label" for="billing-name">Name</label>
                                                                    <input type="text" class="form-control" id="billing-name" placeholder="Enter name">
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-4">
                                                                <div class="mb-3">
                                                                    <label class="form-label" for="billing-email-address">Email Address</label>
                                                                    <input type="email" class="form-control" id="billing-email-address" placeholder="Enter email">
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-4">
                                                                <div class="mb-3">
                                                                    <label class="form-label" for="billing-phone">Phone</label>
                                                                    <input type="text" class="form-control" id="billing-phone" placeholder="Enter Phone no.">
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="mb-3">
                                                            <label class="form-label" for="billing-address">Address</label>
                                                            <textarea class="form-control" id="billing-address" rows="3" placeholder="Enter full address"></textarea>
                                                        </div>

                                                        <div class="row">


                                                            <div class="col-lg-4">
                                                                <div class="mb-4 mb-lg-0">
                                                                    <label class="form-label" for="billing-city">Order notes (optional)</label>
                                                                    <input type="text" class="form-control" id="billing-city" placeholder="Note about your oder">
                                                                </div>
                                                            </div>

                                                            <div class="col-lg-4">

                                                            </div>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                            </ol>
                        </div>
                    </div>

                    <div class="row my-4">
                        <div class="col">
                            <a href="product">
                                <i class="mdi mdi-arrow-left me-1"></i> Continue Shopping </a>
                        </div> <!-- end col -->
                        <div class="col">
                            <div class="text-end mt-2 mt-sm-0">
                                <a href="#" class="btn btn-success">
                                    <i class="mdi mdi-cart-outline me-1"></i> Procced </a>
                            </div>
                        </div> <!-- end col -->
                    </div> <!-- end row-->
                </div>
                <div class="col-xl-4">
                    <div class="card checkout-order-summary">
                        <div class="card-body">
                            <div class="p-3 bg-light mb-3">
                                <h5 class="font-size-16 mb-0">Order Summary <span class="float-end ms-2"></span></h5>
                            </div>
                            <div class="table-responsive">
                                <table class="table table-centered mb-0 table-nowrap">
                                    <thead>
                                        <tr>
                                            <th class="border-top-0" style="width: 110px;" scope="col">Product</th>


                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <th scope="row"><img src="https://www.bootdey.com/image/280x280/FF00FF/000000" alt="product-img" title="product-img" class="avatar-lg rounded"></th>
                                            <td>
                                                <h5 class="font-size-16 text-truncate"><a href="#" class="text-dark">Waterproof Mobile Phone</a></h5>
                                                <p class="text-muted mb-0">
                                                    <i class="bx bxs-star text-warning"></i>
                                                    <i class="bx bxs-star text-warning"></i>
                                                    <i class="bx bxs-star text-warning"></i>
                                                    <i class="bx bxs-star text-warning"></i>
                                                    <i class="bx bxs-star-half text-warning"></i>
                                                </p>
                                                <p class="text-muted mb-0 mt-1">$ 260 x 2</p>
                                            </td>

                                        </tr>
                                        <tr>
                                            <th scope="row"><img src="https://www.bootdey.com/image/280x280/FF00FF/000000" alt="product-img" title="product-img" class="avatar-lg rounded"></th>
                                            <td>
                                                <h5 class="font-size-16 text-truncate"><a href="#" class="text-dark">Smartphone Dual Camera</a></h5>
                                                <p class="text-muted mb-0">
                                                    <i class="bx bxs-star text-warning"></i>
                                                    <i class="bx bxs-star text-warning"></i>
                                                    <i class="bx bxs-star text-warning"></i>
                                                    <i class="bx bxs-star text-warning"></i>
                                                </p>
                                                <p class="text-muted mb-0 mt-1">$ 260 x 1</p>
                                            </td>

                                        </tr>
                                        <tr>
                                            <td colspan="2">
                                                <h5 class="font-size-14 m-0">Sub Total  </h5>
                                                <span>
                                                    $ 780
                                                </span>
                                            </td>


                                        </tr>





                                        <tr class="bg-light">
                                            <td colspan="2">
                                                <h5 class="font-size-14 m-0">Total</h5>
                                                <span>
                                                    $ 780
                                                </span>
                                            </td>

                                        </tr>
                                    </tbody>
                                </table>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- end row -->
 
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>+
       <jsp:include page="layout/footer.jsp"/>
    </body>
    
</html>
