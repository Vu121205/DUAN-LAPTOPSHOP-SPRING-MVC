<%@page contentType="text/html" pageEncoding="UTF-8" %> <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %> <%@
taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap" rel="stylesheet" />

    <!-- Icon Font Stylesheet -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet" />

    <!-- Libraries Stylesheet -->
    <link href="/client/lib/lightbox/css/lightbox.min.css" rel="stylesheet" />
    <link href="/client/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet" />

    <!-- Customized Bootstrap Stylesheet -->
    <link href="/resources/client/css/bootstrap.min.css" rel="stylesheet" />

    <!-- Template Stylesheet -->
    <link href="/resources/client/css/style.css" rel="stylesheet" />

    <!-- <link href="/css/demo.css " rel="stylesheet"> -->
  </head>
  <body>
    <!-- Spinner Start -->
    <div id="spinner" class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50 d-flex align-items-center justify-content-center">
      <div class="spinner-grow text-primary" role="status"></div>
    </div>
    <!-- Spinner End -->

    <jsp:include page="../layout/header.jsp" />

    <!-- filter and product -->
    <div class="container-fluid fruite py-5">
      <div class="container py-5">
        <div class="row g-2">
              <!-- filter -->
              <div class="col-lg-4">
                <div class="row g-4">
                  <div class="col-12">
                    <div class="row g-4">
                      <div class="col-12">
                        <div class="mb-2"><b>Hãng sản xuất</b></div>
                        <div class="form-check form-check-inline">
                          <input class="form-check-input" type="checkbox" value="Apple" id="factory-1" />
                          <label class="form-check-label" for="factory-1">Apple</label>
                        </div>
                        <div class="form-check form-check-inline">
                          <input class="form-check-input" type="checkbox" value="Asus" id="factory-2" />
                          <label class="form-check-label" for="factory-2">Asus</label>
                        </div>
                        <div class="form-check form-check-inline">
                          <input class="form-check-input" type="checkbox" value="Lenovo" id="factory-3" />
                          <label class="form-check-label" for="factory-3">Lenovo</label>
                        </div>
                        <div class="form-check form-check-inline">
                          <input class="form-check-input" type="checkbox" value="Dell" id="factory-4" />
                          <label class="form-check-label" for="factory-4">Dell</label>
                        </div>
                        <div class="form-check form-check-inline">
                          <input class="form-check-input" type="checkbox" value="Acer" id="factory-5" />
                          <label class="form-check-label" for="factory-5">Acer</label>
                        </div>
                      </div>
                      <div class="col-12">
                        <div class="mb-2"><b>Mục đích sử dụng</b></div>
                        <div class="form-check form-check-inline">
                          <input class="form-check-input" type="checkbox" value="Gaming" id="target-1" />
                          <label class="form-check-label" for="target-1">Gaming</label>
                        </div>
                        <div class="form-check form-check-inline">
                          <input class="form-check-input" type="checkbox" value="Sinh viên - văn phòng" id="target-2" />
                          <label class="form-check-label" for="target-2">Sinh viên - văn phòng</label>
                        </div>
                        <div class="form-check form-check-inline">
                          <input class="form-check-input" type="checkbox" value="Thiết kế đồ họa" id="target-3" />
                          <label class="form-check-label" for="target-3">Thiết kế đồ họa</label>
                        </div>
                        <div class="form-check form-check-inline">
                          <input class="form-check-input" type="checkbox" value="Mỏng nhẹ" id="target-4" />
                          <label class="form-check-label" for="target-4">Mỏng nhẹ</label>
                        </div>
                        <div class="form-check form-check-inline">
                          <input class="form-check-input" type="checkbox" value="Doanh nhân" id="target-5" />
                          <label class="form-check-label" for="target-5">Doanh nhân</label>
                        </div>
                      </div>
                      <div class="col-12">
                        <div class="mb-2"><b>Mức giá</b></div>
                        <div class="form-check form-check-inline">
                          <input class="form-check-input" type="checkbox" value="Dưới 10 triệu" id="price-1" />
                          <label class="form-check-label" for="price-1">Dưới 10 triệu</label>
                        </div>
                        <div class="form-check form-check-inline">
                          <input class="form-check-input" type="checkbox" value="10 - 15 triệu" id="price-2" />
                          <label class="form-check-label" for="price-2">10 - 15 triệu</label>
                        </div>
                        <div class="form-check form-check-inline">
                          <input class="form-check-input" type="checkbox" value="15 - 20 triệu" id="price-3" />
                          <label class="form-check-label" for="price-3">15 - 20 triệu</label>
                        </div>
                        <div class="form-check form-check-inline">
                          <input class="form-check-input" type="checkbox" value="20 - 25 triệu" id="price-4" />
                          <label class="form-check-label" for="price-4">20 - 25 triệu</label>
                        </div>
                      </div>
                      <div class="col-12">
                        <div class="mb-2"><b>Sắp xếp</b></div>
                        <div class="form-check form-check-inline">
                          <input class="form-check-input" type="radio" value="Giá tăng dần" id="sort-1" />
                          <label class="form-check-label" for="sort-1">Giá tăng dần</label>
                        </div>
                        <div class="form-check form-check-inline">
                          <input class="form-check-input" type="radio" value="Giá giảm dần" id="sort-2" />
                          <label class="form-check-label" for="sort-2">Giá giảm dần</label>
                        </div>
                        <div class="form-check form-check-inline">
                          <input class="form-check-input" type="radio" value="Không sắp xếp" id="sort-3" />
                          <label class="form-check-label" for="sort-3">Không sắp xếp</label>
                        </div>
                      </div>
                    </div>
                    <div class="d-flex justify-content-center my-4">
                            <a href="#" class="btn border border-secondary px-4 py-3 rounded-pill text-primary w-100">Lọc sản phẩm</a>
                     </div>
                  </div>
                </div>
              </div>
              <!-- list products -->
              <div class="col-lg-8">
                <div class="row g-4 justify-content-center">
                  <div class="col-md-6 col-lg-6 col-xl-4">
                    <div class="rounded position-relative fruite-item">
                      <div class="fruite-img">
                        <img src="img/fruite-item-5.jpg" class="img-fluid w-100 rounded-top" alt="" />
                      </div>
                      <div class="text-white bg-secondary px-3 py-1 rounded position-absolute" style="top: 10px; left: 10px">Fruits</div>
                      <div class="p-4 border border-secondary border-top-0 rounded-bottom">
                        <h4>Grapes</h4>
                        <p>Lorem ipsum dolor sit amet consectetur adipisicing elit sed do eiusmod te incididunt</p>
                        <div class="d-flex justify-content-between flex-lg-wrap">
                          <p class="text-dark fs-5 fw-bold mb-0">$4.99 / kg</p>
                          <a href="#" class="btn border border-secondary rounded-pill px-3 text-primary">
                            <i class="fa fa-shopping-bag me-2 text-primary"></i>
                            Add to cart
                          </a>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-12">
                    <div class="pagination d-flex justify-content-center mt-5">
                      <a href="#" class="rounded">&laquo;</a>
                      <a href="#" class="active rounded">1</a>
                      <a href="#" class="rounded">2</a>
                      <a href="#" class="rounded">3</a>
                      <a href="#" class="rounded">4</a>
                      <a href="#" class="rounded">5</a>
                      <a href="#" class="rounded">6</a>
                      <a href="#" class="rounded">&raquo;</a>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <jsp:include page="../layout/footer.jsp" />

    <!-- Back to Top -->
    <a href="#" class="btn btn-primary border-3 border-primary rounded-circle back-to-top"><i class="fa fa-arrow-up"></i></a>

    <!-- JavaScript Libraries -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="client/easing/easing.min.js"></script>
    <script src="client/lib/waypoints/waypoints.min.js"></script>
    <script src="client/lib/lightbox/js/lightbox.min.js"></script>
    <script src="client/lib/owlcarousel/owl.carousel.min.js"></script>

    <!-- Template Javascript -->
    <script src="/client/js/main.js"></script>
  </body>
</html>
