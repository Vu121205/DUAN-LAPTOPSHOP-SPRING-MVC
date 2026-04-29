<%@page contentType="text/html" pageEncoding="UTF-8" %> <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>Login - Vudoan</title>

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
    <section class="vh-100" style="background-color: #508bfc">
      <div class="container h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
          <div class="col-12 col-md-8 col-lg-6 col-xl-5">
            <div class="card border-2">
              <div class="card-body p-5 text-center">
                <h3 class="mb-2">Login</h3>
                <hr />

                <form method="post" action="/login">
                  <c:if test="${param.error != null}">
                      <div class="my-2" style="color: red">
                        Invalid email or password
                      </div>
                  </c:if>
                  <c:if test="${param.logout != null}">
                      <div class="my-2" style="color: rgb(0, 255, 106)">
                        Logout success
                      </div>
                  </c:if>
                  <div class="form-outline mb-4">
                    <input type="email" class="form-control form-control-lg" placeholder="Email" name="username" />
                  </div> 

                  <div class="form-outline mb-4">
                    <input type="password" class="form-control form-control-lg" placeholder="Password" name="password" />
                  </div>

                  <div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                  </div>

                  <hr />
                  <button class="btn btn-primary btn-lg w-100" type="submit">Login</button>
                </form>
                <hr />
              </div>
              <div class="col-12 text-center mb-3"><a href="/register">Have an account? Go to login</a></div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </body>
</html>
