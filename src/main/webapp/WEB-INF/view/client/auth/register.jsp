<%@page contentType="text/html" pageEncoding="UTF-8" %> <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>Create User - Vudoan</title>
    <link href="/client/css/register.css" rel="stylesheet" />
    <!-- import bootstrap -->
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB"
      crossorigin="anonymous"
    />
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
      crossorigin="anonymous"
    ></script>
  </head>
  <body>
    <section class="vh-100 gradient-custom">
      <div class="container py-5 h-100">
        <div class="row justify-content-center align-items-center h-100">
          <div class="col-12 col-lg-9 col-xl-7">
            <div class="card shadow-2-strong card-registration" style="border-radius: 15px">
              <div class="card-body p-4 p-md-5">
                <h3 class="mb-4 pb-2 pb-md-0 mb-md-5">Registration Form</h3>

                <form:form method="post" action="/register" modelAttribute="registerUser">
                  <div class="row">
                    <div class="col-md-6 mb-4">
                      <div data-mdb-input-init class="form-outline">
                        <form:input type="text" id="firstName" class="form-control form-control-lg" path="firstName" />
                        <label class="form-label" for="firstName">First Name</label>
                      </div>
                    </div>
                    <div class="col-md-6 mb-4">
                      <div data-mdb-input-init class="form-outline">
                        <form:input type="text" id="lastName" class="form-control form-control-lg" path="lastName" />
                        <label class="form-label" for="lastName">Last Name</label>
                      </div>
                    </div>
                    <div class="col-md-12 mb-4 pb-2">
                      <div data-mdb-input-init class="form-outline">
                        <form:input type="email" id="emailAddress" class="form-control form-control-lg" path="email" />
                        <label class="form-label" for="emailAddress">Email</label>
                      </div>
                    </div>
                    <div class="col-12">
                      <div data-mdb-input-init class="form-outline">
                        <form:input type="password" id="emailAddress" class="form-control form-control-lg" path="password" />
                        <label class="form-label" for="emailAddress">Password</label>
                      </div>
                    </div>
                    <div class="col-12">
                      <div data-mdb-input-init class="form-outline">
                        <form:input type="password" id="emailAddress" class="form-control form-control-lg" path="confirmPassword" />
                        <label class="form-label" for="emailAddress">Confirm Password</label>
                      </div>
                    </div>
                  </div>
                  <div class="mt-4 pt-2">
                    <input data-mdb-ripple-init class="btn btn-primary btn-lg" type="submit" value="Submit" />
                  </div>
                </form:form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </body>
</html>
